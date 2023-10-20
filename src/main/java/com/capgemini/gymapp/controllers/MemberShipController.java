package com.capgemini.gymapp.controllers;

import com.capgemini.gymapp.entities.MemberShip;
import com.capgemini.gymapp.services.interfaces.IMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/membership")
@RequiredArgsConstructor
public class MemberShipController {
    @Autowired
    IMembershipService membershipService;




    @PostMapping("/addMemberShip/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public MemberShip addMemberShip(@RequestBody MemberShip memberShip, @PathVariable ("userId")int userId)
    {

        return membershipService.addMemberShipAndAssignToUser(memberShip, userId);
    }


    @PostMapping("/delete-membership/{memberShipIdd}")
    public void deleteMemberShip(@PathVariable("memberShipIdd") int memberShipIdd){
        membershipService.deleteMemberShip(memberShipIdd);
    }

    @PutMapping("/update-membership/{memberShipIdd}")
    @PreAuthorize("hasRole('ADMIN')")
    public MemberShip updateMemberShip(@PathVariable("memberShipId") int memberShipId, @RequestBody MemberShip memberShip)
    {
        return membershipService.updateMemberShip(memberShipId, memberShip);
    }

    @GetMapping("/get-all-memberships")
    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberShip> allMemberShips(){
        return membershipService.getAllMemberships();
    }




}
