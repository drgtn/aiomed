package com.drgtn.aiomed;

import com.drgtn.aiomed.service.SchedulerService;
import com.drgtn.aiomed.service.TreatmentPlanSeeder;
import com.drgtn.aiomed.service.TreatmentTaskService;
import com.drgtn.aiomed.service.TreatmentTaskVisualizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class AiomedApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiomedApplication.class, args);
    }

    @Bean
    @ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CommandLineRunner run(TreatmentPlanSeeder treatmentPlanSeeder,
                                 TreatmentTaskService treatmentTaskService,
                                 TreatmentTaskVisualizer treatmentTaskVisualizer,
                                 SchedulerService schedulerService) {

        return args -> {
            treatmentPlanSeeder.seedPlans();

            Runnable task = () -> {
                treatmentTaskService.createTasks();
                treatmentTaskVisualizer.display();
            };

            schedulerService.schedule(task,
                    LocalDateTime.now().plusSeconds(5),
                    Duration.ofMinutes(5));

        };
    }
}
