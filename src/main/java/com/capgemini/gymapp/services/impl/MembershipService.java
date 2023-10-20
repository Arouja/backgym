package com.capgemini.gymapp.services.impl;

import com.capgemini.gymapp.Repositories.MembershipRepository;
import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.entities.MemberShip;
import com.capgemini.gymapp.entities.Status;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.services.interfaces.IMembershipService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@EnableScheduling
public class MembershipService implements IMembershipService {


    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    UserRepository userRepository;


    @Autowired
    public void membershipService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public MemberShip addMembership(MemberShip membership){
        return membershipRepository.save(membership);
    }
    @Override
    public List<MemberShip> getAllMemberships(){
        return membershipRepository.findAll();
    }
    @Override
    public MemberShip getMembership(int id){
        return membershipRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMemberShip(int id){membershipRepository.deleteById(id);}

    @Override
    public MemberShip updateMemberShip(int id, MemberShip updatedMembership) {
        MemberShip existingMemberShip = membershipRepository.findById(id).orElse(null);
        if (existingMemberShip != null) {
            existingMemberShip.setStartDate(updatedMembership.getStartDate());
            existingMemberShip.setEndDate(updatedMembership.getEndDate());
            existingMemberShip.setStatus(updatedMembership.getStatus());
            existingMemberShip.setPrice(updatedMembership.getPrice());
            existingMemberShip.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            membershipRepository.save(existingMemberShip);

        }
        return existingMemberShip;
    }

    @Override
    public MemberShip addMemberShipAndAssignToUser(MemberShip memberShip, int userId)
    {

        MemberShip savedMemberShip = membershipRepository.save(memberShip);
        savedMemberShip.setStatus(Status.ACTIVE);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            savedMemberShip.setUser(user);
            membershipRepository.save(savedMemberShip);

            // Send email to the company
            emailService.sendMemberShipConfirmationEmail(user, savedMemberShip);
        }

        return savedMemberShip;
    }


    @Override
    public List<MemberShip> getMemberShipByUser(Integer userId) {
        return membershipRepository.findMemberShipsByUserId(userId);
    }

    @Override
    @Scheduled(fixedRate = 60000) // Schedule to run every 60 seconds (1 minute)
    @PostConstruct
    public List<MemberShip> allExpiredMemberShips() {
        LocalDate currentLocalDate = LocalDate.now();
        Date currentDate = java.sql.Date.valueOf(currentLocalDate);
        List<MemberShip> allMemberShips = membershipRepository.findAll();

        List<MemberShip> expiredMemberShips = allMemberShips.stream()
                .filter(memberShip -> {
                    LocalDate endDate = memberShip.getEndDate();
                    long daysDifference = ChronoUnit.DAYS.between(currentLocalDate, endDate);
                    return daysDifference < 10;
                }).collect(Collectors.toList());

        List<User> usersWithExpiredMemberShips = expiredMemberShips.stream()
                .map(MemberShip::getUser)
                .distinct()
                .collect(Collectors.toList());

        usersWithExpiredMemberShips.forEach(emailService::sendExpirationEmail);

        return expiredMemberShips;
    }





}
