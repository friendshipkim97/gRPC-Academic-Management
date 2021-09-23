package domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private String courseNumber;
    private String professorLastName;
    private String advancedCourseNumber;

    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourses = new ArrayList<>();

}
