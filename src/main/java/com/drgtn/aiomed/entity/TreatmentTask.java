package com.drgtn.aiomed.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneId;

@Entity
@Table(name = "TREATMENT_TASK")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TreatmentAction action;
    @Column(name = "SUBJECT_PATIENT", nullable = false)
    private String subjectPatient;
    @Column(name = "START_TIME", nullable = false)
    private Instant startTime;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    private TreatmentPlan treatmentPlan;

    @Override
    public String toString() {
        return "TreatmentTask{" +
                "id=" + id +
                ", startTime=" + startTime.atZone(ZoneId.systemDefault()) +
                ", action=" + action +
                ", subjectPatient='" + subjectPatient + '\'' +
                ", status=" + status +
                '}';
    }
}
