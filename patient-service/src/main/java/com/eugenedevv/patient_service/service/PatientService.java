package com.eugenedevv.patient_service.service;

import com.eugenedevv.patient_service.dto.PatientRequestDTO;
import com.eugenedevv.patient_service.dto.PatientResponseDTO;
import com.eugenedevv.patient_service.exception.EmailAlreadyExistsException;
import com.eugenedevv.patient_service.exception.PatientNotFoundException;
import com.eugenedevv.patient_service.mapper.PatientMapper;
import com.eugenedevv.patient_service.model.Patient;
import com.eugenedevv.patient_service.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PatientService {
    private PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients() {
        return patientRepository.findAll().stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email " + patientRequestDTO.getEmail() + " already exists.");
        }
        Patient newPatient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient with id " + id + " not found.")
        );
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email " + patientRequestDTO.getEmail() + " already exists.");
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatesPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatesPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
