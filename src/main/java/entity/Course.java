package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private String courseNumber;
    private String professorLastName;
    private String courseName;

    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourseEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Course parent;

    @OneToMany(mappedBy = "parent")
    private List<Course> child = new ArrayList<>();

    // 연관관계 메서드
    public void addChildCourse(Course child){
        this.child.add(child);
        child.setParent(this);
    }

    // 연관관계 메서드
    public void addStudentCourse(StudentCourse studentCourse) {
        this.studentCourseEntities.add(studentCourse);
        studentCourse.setCourse(this);
    }

    // 생성 메서드
    public static Course createCourse(String courseNumber, String professorLastName, String courseName, Course... childCourse) {
        Course createdCourse = new Course();
        createdCourse.setCourseNumber(courseNumber);
        createdCourse.setProfessorLastName(professorLastName);
        createdCourse.setCourseName(courseName);
        for (Course course : childCourse) {
            createdCourse.addChildCourse(course);
        }
        return createdCourse;
    }
}
