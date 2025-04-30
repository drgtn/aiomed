package com.drgtn.aiomed.fixture;

import com.drgtn.aiomed.entity.TreatmentAction;
import com.drgtn.aiomed.entity.TreatmentTask;

import java.time.Instant;

import static com.drgtn.aiomed.entity.Status.ACTIVE;
import static com.drgtn.aiomed.entity.TreatmentTask.TreatmentTaskBuilder;

public class TreatmentTaskFixture {
    private TreatmentTaskFixture() {
    }

    public static TreatmentTask aTreatmentTask_1() {
        return aTreatmentTaskBuilder()
                .startTime(Instant.parse("2025-04-30T08:00:00Z"))
                .build();
    }

    public static TreatmentTask aTreatmentTask_2() {
        return aTreatmentTaskBuilder()
                .startTime(Instant.parse("2025-05-01T08:00:00Z"))
                .build();
    }

    public static TreatmentTask aTreatmentTask_3() {
        return aTreatmentTaskBuilder()
                .startTime(Instant.parse("2025-05-01T05:00:00Z"))
                .build();
    }

    private static TreatmentTaskBuilder aTreatmentTaskBuilder() {
        return TreatmentTask.builder().action(TreatmentAction.ACTION_A)
                .subjectPatient("patient_001")
                .status(ACTIVE);
    }
}
