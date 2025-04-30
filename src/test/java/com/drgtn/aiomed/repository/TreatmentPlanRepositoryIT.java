package com.drgtn.aiomed.repository;

import com.drgtn.aiomed.BaseIT;
import com.drgtn.aiomed.entity.TreatmentPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.drgtn.aiomed.fixture.TreatmentPlanFixture.aTreatmentPlanActive;
import static com.drgtn.aiomed.fixture.TreatmentPlanFixture.aTreatmentPlanInactive;
import static org.assertj.core.api.Assertions.assertThat;

class TreatmentPlanRepositoryIT extends BaseIT {
    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @Test
    void testFindAllByActive() {
        TreatmentPlan aTreatmentPlanActive = aTreatmentPlanActive();
        treatmentPlanRepository.saveAndFlush(aTreatmentPlanActive);
        treatmentPlanRepository.saveAndFlush(aTreatmentPlanInactive());
        assertThat(treatmentPlanRepository.findAllByActiveTrue())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(aTreatmentPlanActive);
    }
}
