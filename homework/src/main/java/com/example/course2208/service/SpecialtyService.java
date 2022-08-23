package com.example.course2208.service;

import com.example.course2208.exception.NoGradesException;
import com.example.course2208.exception.NoSpecialtiesException;
import com.example.course2208.exception.SpecialtyNotFoundException;
import com.example.course2208.model.Specialty;
import com.example.course2208.model.Student;
import com.example.course2208.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public List<Student> getAllStudentBySpecialty(Integer specialtyId)
            throws SpecialtyNotFoundException {
        Optional<Specialty> tmpOptionalSpecialty = specialtyRepository.findById(specialtyId);
        if (tmpOptionalSpecialty.isEmpty()) {
            throw new SpecialtyNotFoundException();
        } else {
            return tmpOptionalSpecialty.get().getStudents();
        }
    }

    public Specialty getSpecialtyWithMostStudents() throws NoSpecialtiesException {
        Optional<Specialty> specialtyOptional = specialtyRepository.findAll().stream().max(Comparator.comparing(maxim -> maxim.getStudents().size()));
        if (specialtyOptional.isEmpty()) {
            throw new NoSpecialtiesException();
        } else {
            return specialtyOptional.get();
        }
    }

    public Integer getAverageGradeSpeciality(Integer specialtyId) throws NoSpecialtiesException, NoGradesException {
        Optional<Specialty> specialtyOptional = specialtyRepository.findById(specialtyId);
        if (specialtyOptional.isEmpty()) {
            throw new NoSpecialtiesException();
        } else {
            Integer avg = 0;
            for (int i = 0; i < specialtyOptional.get().getStudents().size(); i++) {
                avg += specialtyOptional.get().getStudents().get(i).getAverageGrade();
            }
            return avg / specialtyOptional.get().getStudents().size();
        }
    }

    public Student getStudentSpecialtyHighGrade(Integer specialtyId) {
        return (Student) specialtyRepository.findById(specialtyId).stream().map(specialty -> {
            try {
                return specialty.getStudentHighGrade();
            } catch (NoGradesException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
