package com.capgemini.gymapp.controllers;

import com.capgemini.gymapp.entities.Schedule;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.services.interfaces.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    @Autowired
    IScheduleService scheduleService;

    @GetMapping("/all-schedules")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/get-schedule/{id}")
    public Schedule getScheduleById(@PathVariable Integer id) {
        return scheduleService.getScheduleById(id);
    }

    @PostMapping("add-schedule/{id}")
    @PreAuthorize("hasRole('COACH')")
    public Schedule addSchedule(@RequestBody Schedule schedule, @PathVariable("id") Integer id) {
        return scheduleService.addScheduleAndAssignToCoach(schedule, id);
    }

    @DeleteMapping("/delete-schedule/{id}")
    @PreAuthorize("hasRole('COACH')")
    public void deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
    }

    @PostMapping("/book/{scheduleId}/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")
    public void bookSession(@PathVariable Integer scheduleId, @PathVariable Integer clientId) {
        scheduleService.bookSession(scheduleId, clientId);
    }

    @GetMapping("/clients/{scheduleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public Set<User> getClientsForSchedule(@PathVariable Integer scheduleId) {
        return scheduleService.getClientsForSchedule(scheduleId);
    }
}
