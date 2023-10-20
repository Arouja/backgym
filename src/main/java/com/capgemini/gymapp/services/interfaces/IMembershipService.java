package com.capgemini.gymapp.services.interfaces;

import com.capgemini.gymapp.entities.MemberShip;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface IMembershipService {
    MemberShip addMembership(MemberShip membership);

    List<MemberShip> getAllMemberships();

    MemberShip getMembership(int id);

    void deleteMemberShip(int id);

    MemberShip updateMemberShip(int id, MemberShip updatedMembership);

    MemberShip addMemberShipAndAssignToUser(MemberShip memberShip, int userId);

    List<MemberShip> getMemberShipByUser(Integer userId);

    @Scheduled(fixedRate = 60000) // Schedule to run every 60 seconds (1 minute)
    List<MemberShip> allExpiredMemberShips();
}
