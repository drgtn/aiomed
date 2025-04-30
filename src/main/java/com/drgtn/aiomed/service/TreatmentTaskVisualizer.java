package com.drgtn.aiomed.service;

import com.drgtn.aiomed.repository.TreatmentTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TreatmentTaskVisualizer {
    private final TreatmentTaskRepository treatmentTaskRepository;

    public void display() {
        treatmentTaskRepository.findAll().forEach(treatmentTask -> {
            log.info(treatmentTask.toString());
        });
    }
}
