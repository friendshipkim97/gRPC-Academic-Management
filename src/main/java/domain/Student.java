package domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    private String studentNumber;
    private String studentName;
    private String major;

    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses = new ArrayList<>();

    // 연관관계 메서드
    public void addStudentCourse(StudentCourse studentCourse) {
        this.studentCourses.add(studentCourse);
        studentCourse.setStudent(this);
    }

    // 생성 메서드
    public Student createStudent(String studentNumber, String studentName, String major, StudentCourse... studentCourses) {
        Student student = new Student();
        student.setStudentNumber(studentNumber);
        student.setStudentName(studentName);
        student.setMajor(major);
        for (StudentCourse studentCourse : studentCourses) {
            student.addStudentCourse(studentCourse);
        }
        return student;
    }
}
