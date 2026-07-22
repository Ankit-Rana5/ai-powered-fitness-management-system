package com.fitness.activityService.dto;

import com.fitness.activityService.model.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequest {

    private String userId;
    private ActivityType type;
    private Integer duration; // in minutes
    private Integer caloriesBurned;
    private LocalDateTime startTime; // ISO 8601 format
    private Map<String, Object> additionalMetrics;

    // Getters and Setters
}
