package com.drgtn.aiomed.service;

import com.drgtn.aiomed.entity.Status;
import com.drgtn.aiomed.entity.TreatmentPlan;
import com.drgtn.aiomed.entity.TreatmentTask;
import com.drgtn.aiomed.parser.RecurrencePatternExecutor;
import com.drgtn.aiomed.repository.TreatmentPlanRepository;
import com.drgtn.aiomed.repository.TreatmentTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static java.time.ZoneId.systemDefault;

@Service
@RequiredArgsConstructor
public class TreatmentTaskService {
    private final TreatmentPlanRepository planRepository;
    private final TreatmentTaskRepository taskRepository;
    private final RecurrencePatternExecutor recurrencePatternExecutor;

    private static TreatmentTask getTreatmentTask(TreatmentPlan plan, ZonedDateTime occurrence) {
        return TreatmentTask.builder()
                .treatmentPlan(plan)
                .action(plan.getAction())
                .subjectPatient(plan.getSubjectPatient())
                .startTime(occurrence.toInstant())
                .status(Status.ACTIVE)
                .build();
    }

    @Transactional
    public void createTasks() {
        List<TreatmentPlan> plans = planRepository.findAllByActiveTrue();
        plans.forEach(plan -> {
            getOccurrences(plan).forEach(occurrence ->
                    taskRepository.save(getTreatmentTask(plan, occurrence)));
            plan.setActive(false);
        });
    }

    private List<ZonedDateTime> getOccurrences(TreatmentPlan plan) {
        return recurrencePatternExecutor.computeExecutions(plan.getRecurrencePattern(),
                plan.getStartTime().atZone(systemDefault()),
                plan.getEndTime().atZone(systemDefault()));
    }
}
