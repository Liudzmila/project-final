package com.javarush.jira;

import com.javarush.jira.bugtracking.task.ActivityService;
import com.javarush.jira.bugtracking.task.Task;
import com.javarush.jira.bugtracking.task.TaskRepository;
import com.javarush.jira.common.internal.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.time.Duration;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableCaching
public class JiraRushApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JiraRushApplication.class, args);

        // Task 8 - test
        ActivityService activityService = context.getBean(ActivityService.class);
        TaskRepository taskRepository = context.getBean(TaskRepository.class);

        Task task11 = taskRepository.getExisted(11L);
        try {
            Duration inProgressTime = activityService.getTimeSpentInInProgress(task11);
            Duration testingTime = activityService.getTimeSpentInTesting(task11);
            System.out.println("\u001B[34mHi, Yura! It's testing for task 8:\u001B[0m");
            System.out.println("Time spent in in_progress for task 11: " +
                    inProgressTime.toDaysPart() + " days, " +
                    inProgressTime.toHoursPart() + " hours, " +
                    inProgressTime.toMinutesPart() + " minutes, " +
                    inProgressTime.toSecondsPart() + " seconds");
            System.out.println("Time spent in testing for task 11: " +
                    testingTime.toDaysPart() + " days, " +
                    testingTime.toHoursPart() + " hours, " +
                    testingTime.toMinutesPart() + " minutes, " +
                    testingTime.toSecondsPart() + " seconds");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
