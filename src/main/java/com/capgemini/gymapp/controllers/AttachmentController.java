package com.capgemini.gymapp.controllers;


import com.capgemini.gymapp.services.impl.AttachmentService;
import com.capgemini.gymapp.services.interfaces.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    @Autowired
    private IAttachmentService attachmentService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image")MultipartFile file, @PathVariable("id") int id) throws IOException {
        String uploadImage = attachmentService.uploadImageToFileSystem(file, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
/*
    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=attachmentService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }
*/
}