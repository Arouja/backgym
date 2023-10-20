package com.capgemini.gymapp.Repositories;


import com.capgemini.gymapp.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByName(String fileName);




}