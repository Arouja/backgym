package com.capgemini.gymapp.services.impl;


import com.capgemini.gymapp.Repositories.ScheduleRepository;
import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.entities.Schedule;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.services.interfaces.IScheduleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScheduleService implements IScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EmailService emailService;


    @Override
    public Schedule addScheduleAndAssignToCoach(Schedule schedule, int coachId) {
        Schedule savedSchedule = scheduleRepository.save(schedule);


        Optional<User> optionalCoach = userRepository.findById(coachId);
        if (optionalCoach.isPresent()) {
            User coach = optionalCoach.get();
                savedSchedule.setCoach(coach);
                scheduleRepository.save(savedSchedule);
                emailService.sendScheduleConfirmationEmail(coach, savedSchedule);

        } else {

            throw new EntityNotFoundException("Coach not found");
        }

        return savedSchedule;
    }



    @Override
    public void deleteSchedule(int id){scheduleRepository.deleteById(id);}

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleById(Integer id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    @Override

    public void bookSession(Integer scheduleId, Integer clientId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        Optional<User> optionalClient = userRepository.findById(clientId);

        if (optionalSchedule.isPresent() && optionalClient.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            User client = optionalClient.get();
            Set<User> clients = schedule.getClients();
            clients.add(client);
            schedule.setClients(clients);

            scheduleRepository.save(schedule);
        } else {
            throw new EntityNotFoundException("Schedule or client not found");
        }
    }

    @Override
    public Set<User> getClientsForSchedule(Integer scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);

        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            return schedule.getClients();
        } else {
            throw new EntityNotFoundException("Schedule not found");
        }
    }




}
