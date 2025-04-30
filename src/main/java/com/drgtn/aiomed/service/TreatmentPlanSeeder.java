package com.drgtn.aiomed.service;

import com.drgtn.aiomed.entity.TreatmentAction;
import com.drgtn.aiomed.entity.TreatmentPlan;
import com.drgtn.aiomed.repository.TreatmentPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentPlanSeeder {
    private final TreatmentPlanRepository treatmentPlanRepository;

    public void seedPlans() {
        List<TreatmentPlan> plans = List.of(
                TreatmentPlan.builder()
                        .action(TreatmentAction.ACTION_A)
                        .subjectPatient("patient_001")
                        .startTime(Instant.now())
                        .endTime(Instant.now().plus(Duration.ofDays(5)))
                        .recurrencePattern("every day at 08:00 and 18:00")
                        .active(true)
                        .build(),
                TreatmentPlan.builder()
                        .action(TreatmentAction.ACTION_B)
                        .subjectPatient("patient_002")
                        .startTime(Instant.now())
                        .endTime(Instant.now().plus(Duration.ofDays(90)))
                        .recurrencePattern("every Monday at 10:00")
                        .active(true)
                        .build()
        );
        treatmentPlanRepository.saveAll(plans);
    }
}
