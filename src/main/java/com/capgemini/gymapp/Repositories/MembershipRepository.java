package com.capgemini.gymapp.Repositories;

import com.capgemini.gymapp.entities.MemberShip;
import com.capgemini.gymapp.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<MemberShip, Integer> {

    List<MemberShip> findMemberShipsByUserId(Integer userId);

    List<MemberShip> findByStartDateBetween(Date startDate, Date endDate);

    List<MemberShip> findByEndDateBeforeAndEndDateAfter(LocalDateTime currentDate, LocalDateTime thresholdDate);

    List<MemberShip> findByEndDateBefore(LocalDate endDate);

    @Query("SELECT m FROM MemberShip m WHERE m.endDate <= :endDateThreshold")
    List<MemberShip> getMemberShipsWithEndDateBefore(@Param("endDateThreshold") LocalDate endDateThreshold);

    List<MemberShip> findByStatus(Status status);
}
