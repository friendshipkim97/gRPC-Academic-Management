package domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
}
