package com.carato.carato_backend.image;

import com.carato.carato_backend.exceptions.GeneralException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageUtil {
    @Value("${carato.upload.dir}")
    public String uploadDirectory;
    @Value("${carato.upload.uri}")
    public String uploadUri;

    // TODO: This method is temporary, we will use Amazon S3 soon. Right now, we use a images on the server because it's necessary for the frontend development and first sprint
    // NOTE: deleteImage is not implemented because this is a temporal solution
    public List<String> saveImage(List<MultipartFile> files) throws IOException {
        List<String> url = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = saveImage(file);
            url.add(fileName);
        }

        return url;
    }

    public String saveImage(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();

        String extension = getExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID() + "." + extension;

        Path fileToSave = load(uniqueFilename);
        String mimeType = file.getContentType();
        if (mimeType == null || !mimeType.startsWith("image/")) {
            throw new GeneralException(406, "File received '" + originalFilename + "' is not a valid image");
        }

        Files.write(fileToSave, file.getBytes());

        return uploadUri + uniqueFilename;
    }

    private String getExtension(String filename) {
        int dotPosition = filename.lastIndexOf(".");
        int slashPosition = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

        if (dotPosition > slashPosition) {
            return filename.substring(dotPosition + 1);
        } else {
            return "";
        }
    }

    public Resource loadAsResource(String filename) throws FileNotFoundException, MalformedURLException {
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else {
            throw new FileNotFoundException("Could not read file: " + filename);
        }
    }

    public Path load(String filename) {
        return Path.of(uploadDirectory + filename);
    }
}
