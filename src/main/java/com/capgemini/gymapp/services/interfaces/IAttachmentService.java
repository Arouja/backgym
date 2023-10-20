package com.capgemini.gymapp.services.interfaces;

import com.capgemini.gymapp.entities.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAttachmentService {
    String uploadImageToFileSystem(MultipartFile file, int id) throws IOException;


}
