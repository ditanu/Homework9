package com.example.course2208.model;

import com.example.course2208.exception.NoGradesException;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany
    @NotNull
    List<Student> students = new ArrayList<Student>();

    @OneToMany
    @NotNull
    List<Course> courses = new ArrayList<Course>();

    public Student getStudentHighGrade() throws NoGradesException {
        Integer max = students.get(0).getAverageGrade();
        Integer poz = 0;
        for(int i = 1; i < students.size(); i++) {
            if (students.get(i).getAverageGrade() > max) {
                max = students.get(i).getAverageGrade();
                poz = i;
            }
        }
        return students.get(poz);
    }
}
