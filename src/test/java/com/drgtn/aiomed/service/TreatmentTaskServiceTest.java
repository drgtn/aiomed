package com.drgtn.aiomed.service;

import com.drgtn.aiomed.entity.TreatmentPlan;
import com.drgtn.aiomed.entity.TreatmentTask;
import com.drgtn.aiomed.parser.RecurrencePatternExecutor;
import com.drgtn.aiomed.repository.TreatmentPlanRepository;
import com.drgtn.aiomed.repository.TreatmentTaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static com.drgtn.aiomed.fixture.TreatmentPlanFixture.aTreatmentPlanActive;
import static com.drgtn.aiomed.fixture.TreatmentTaskFixture.aTreatmentTask_1;
import static com.drgtn.aiomed.fixture.TreatmentTaskFixture.aTreatmentTask_2;
import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.ofInstant;
import static java.util.Collections.emptyList;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TreatmentTaskServiceTest {
    @Mock
    private TreatmentPlanRepository planRepository;
    @Mock
    private TreatmentTaskRepository taskRepository;
    @Mock
    private RecurrencePatternExecutor recurrencePatternExecutor;
    @InjectMocks
    private TreatmentTaskService treatmentTaskService;

    @Captor
    private ArgumentCaptor<TreatmentTask> treatmentTaskArgumentCaptor;

    @Test
    void testCreateTasksActiveTreatmentPlan() {
        TreatmentPlan aPlan = aTreatmentPlanActive();

        ZonedDateTime start = ofInstant(aPlan.getStartTime(), systemDefault());
        ZonedDateTime end = ofInstant(aPlan.getEndTime(), systemDefault());

        ZonedDateTime occurrence1 = ofInstant(aPlan.getStartTime(), systemDefault());
        ZonedDateTime occurrence2 = start.plusDays(1);

        when(planRepository.findAllByActiveTrue()).thenReturn(of(aPlan));
        when(recurrencePatternExecutor.computeExecutions(aPlan.getRecurrencePattern(), start, end)).thenReturn(of(occurrence1, occurrence2));

        treatmentTaskService.createTasks();

        verify(taskRepository, times(2)).save(treatmentTaskArgumentCaptor.capture());

        assertThat(treatmentTaskArgumentCaptor.getAllValues()).
                usingRecursiveFieldByFieldElementComparatorIgnoringFields("treatmentPlan")
                .containsExactlyInAnyOrder(aTreatmentTask_1(), aTreatmentTask_2());
        assertThat(aPlan.isActive()).isFalse();
    }

    @Test
    void testCreateTasksInactiveTreatmentPlan() {
        when(planRepository.findAllByActiveTrue()).thenReturn(emptyList());
        treatmentTaskService.createTasks();
        verifyNoInteractions(recurrencePatternExecutor);
        verifyNoInteractions(taskRepository);
        assertThat(treatmentTaskArgumentCaptor.getAllValues()).isEmpty();
    }
}
