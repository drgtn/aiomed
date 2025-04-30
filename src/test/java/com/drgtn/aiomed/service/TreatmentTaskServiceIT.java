package com.drgtn.aiomed.service;

import com.drgtn.aiomed.BaseIT;
import com.drgtn.aiomed.entity.TreatmentPlan;
import com.drgtn.aiomed.entity.TreatmentTask;
import com.drgtn.aiomed.repository.TreatmentPlanRepository;
import com.drgtn.aiomed.repository.TreatmentTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.drgtn.aiomed.fixture.TreatmentPlanFixture.aTreatmentPlanActive;
import static com.drgtn.aiomed.fixture.TreatmentPlanFixture.aTreatmentPlanInactive;
import static com.drgtn.aiomed.fixture.TreatmentTaskFixture.aTreatmentTask_3;
import static org.assertj.core.api.Assertions.assertThat;

class TreatmentTaskServiceIT extends BaseIT {
    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    private TreatmentTaskRepository treatmentTaskRepository;

    @Autowired
    private TreatmentTaskService treatmentTaskService;

    @Test
    void testCreateTasksActiveTreatmentPlan() {
        TreatmentPlan aPlan = aTreatmentPlanActive();
        treatmentPlanRepository.save(aPlan);
        treatmentTaskService.createTasks();

        List<TreatmentTask> createdTasks = treatmentTaskRepository.findAll();
        assertThat(createdTasks).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "treatmentPlan").containsExactlyInAnyOrder(aTreatmentTask_3());
        assertThat(aPlan.isActive()).isFalse();

    }

    @Test
    void testCreateTasksInactiveTreatmentPlan() {
        TreatmentPlan aPlan = aTreatmentPlanInactive();
        treatmentPlanRepository.save(aPlan);
        treatmentTaskService.createTasks();

        assertThat(treatmentTaskRepository.findAll()).isEmpty();
    }
}
