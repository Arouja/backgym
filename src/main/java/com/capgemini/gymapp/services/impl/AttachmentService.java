package com.capgemini.gymapp.services.impl;

import com.capgemini.gymapp.Repositories.AttachmentRepository;
import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.config.FilenameUtils;
import com.capgemini.gymapp.entities.Attachment;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.services.interfaces.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class AttachmentService implements IAttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private UserRepository userRepository;
    private final String FOLDER_PATH = "/home/naoufel/Music/gym-app-front/src/images/";

    @Override
    public String uploadImageToFileSystem(MultipartFile file, int id) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Attachment fileData = attachmentRepository.save(Attachment.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath).build());

            file.transferTo(new File(filePath));
            user.setAttachment(fileData);
            userRepository.save(user);
            System.out.println(user.getAttachment().getFilePath());
            if (fileData != null) {
                return "file uploaded successfully : " + filePath;

            }
        }
        return null;
    }

}

/*

    @Override
    public byte[] downloadImage(String fileName){
        Optional<Attachment> imageData = attachmentRepository.findByName(fileName);
        return FilenameUtils.decompressImage(imageData.get().getData());
    }

*/
