package com.capgemini.gymapp.services.interfaces;

import com.capgemini.gymapp.entities.Schedule;
import com.capgemini.gymapp.entities.User;

import java.util.List;
import java.util.Set;

public interface IScheduleService {

    Schedule addScheduleAndAssignToCoach(Schedule schedule, int coachId);

    void deleteSchedule(int id);

    List<Schedule> getAllSchedules();

    Schedule getScheduleById(Integer id);

    void bookSession(Integer scheduleId, Integer clientId);

    Set<User> getClientsForSchedule(Integer scheduleId);
}
