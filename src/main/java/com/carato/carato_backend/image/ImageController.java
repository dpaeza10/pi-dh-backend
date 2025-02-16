package com.carato.carato_backend.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ImageController {
    @Autowired
    private ImageUtil imageUtil;

    @GetMapping("/storage/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        try {
            Resource file = imageUtil.loadAsResource(filename);

            String headerValue = "inline; filename=\"" + file.getFilename() + "\"";
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(file);
        } catch (IOException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
