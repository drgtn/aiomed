package com.drgtn.aiomed.fixture;

import com.drgtn.aiomed.entity.TreatmentAction;
import com.drgtn.aiomed.entity.TreatmentPlan;

import java.time.Instant;

import static com.drgtn.aiomed.entity.TreatmentPlan.TreatmentPlanBuilder;

public class TreatmentPlanFixture {
    private TreatmentPlanFixture() {
    }

    public static TreatmentPlanBuilder aTreatmentPlanBuilder() {
        return TreatmentPlan.builder()
                .action(TreatmentAction.ACTION_A)
                .startTime(Instant.parse("2025-04-30T08:00:00Z"))
                .endTime(Instant.parse("2025-05-01T08:00:00Z"))
                .recurrencePattern("every day at 08:00")
                .subjectPatient("patient_001");
    }

    public static TreatmentPlan aTreatmentPlanActive() {
        return aTreatmentPlanBuilder().active(true).build();
    }

    public static TreatmentPlan aTreatmentPlanInactive() {
        return aTreatmentPlanBuilder().active(false).build();
    }
}
