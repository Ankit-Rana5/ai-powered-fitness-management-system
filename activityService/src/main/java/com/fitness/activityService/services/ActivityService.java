package com.fitness.activityService.services;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repo.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final UserValidationService userValidationService;

    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

//    public ActivityService(ActivityRepository activityRepository) {
//        this.activityRepository = activityRepository;
//    }

    public ActivityResponse trackActivity(ActivityRequest request) {

       boolean isValidUser= userValidationService.validateUser(request.getUserId());

       if(!isValidUser) {
           throw new RuntimeException("Invalid user ID: " + request.getUserId());
       }

        Activity activity=Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime((request.getStartTime()))
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity=activityRepository.save(activity);
        try
        {
            kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // Log the exception and continue
            System.err.println("Failed to send activity to Kafka: " + e.getMessage());
        }

           return mapToResponse(savedActivity);

    }
    private ActivityResponse mapToResponse(Activity activity){

        ActivityResponse response=new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        return response;
    }
}
