package com.drgtn.aiomed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TREATMENT_PLAN")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION", nullable = false)
    private TreatmentAction action;
    @Column(name = "SUBJECT_PATIENT", nullable = false)
    private String subjectPatient;
    @Column(name = "START_TIME", nullable = false)
    private Instant startTime;
    @Column(name = "END_TIME")
    private Instant endTime;
    @Column(name = "RECURRENCE_PATTERN", nullable = false)
    private String recurrencePattern;
    @OneToMany(mappedBy = "treatmentPlan",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST)
    private Set<TreatmentTask> treatmentTasks = new HashSet<>();
    private boolean active;

    public void addTreatmentTask(TreatmentTask treatmentTask) {
        treatmentTasks.add(treatmentTask);
        treatmentTask.setTreatmentPlan(this);
    }

    public void removeTreatmentTask(TreatmentTask treatmentTask) {
        treatmentTasks.remove(treatmentTask);
        treatmentTask.setTreatmentPlan(null);
    }
}
