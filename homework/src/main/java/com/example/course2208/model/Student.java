package com.example.course2208.model;

import com.example.course2208.exception.NoGradesException;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @OneToMany
    List<Grade> grades = new ArrayList<Grade>();

    @NotNull
    @ManyToOne
    Specialty specialty;

    public Integer getAverageGrade() throws NoGradesException {
        if(grades.isEmpty()) {
            throw new NoGradesException();
        } else {
            Integer avg = 0;
            for (int i =0; i < grades.size(); i++) {
                avg += grades.get(i).getGrade();
            }
            return avg/grades.size();
        }
    }

}
