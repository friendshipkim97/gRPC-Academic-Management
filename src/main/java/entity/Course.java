package entity;

import constant.Constants;
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

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name="advanced_course",
            joinColumns={@JoinColumn(name="course_id")},
            inverseJoinColumns={@JoinColumn(name="advanced_course_id")})
    private List<Course> advancedCourseList = new ArrayList<>();

    @ManyToMany(mappedBy="advancedCourseList")
    private List<Course> courseList = new ArrayList<>();

    // 연관관계 메서드
    public void addStudentCourse(StudentCourse studentCourse) {
        this.studentCourseEntities.add(studentCourse);
        studentCourse.setCourse(this);
    }

    // 연관관계 메서드
    public void addAdvancedCourse(Course child){
        advancedCourseList.add(child);
        courseList.add(this);
    }

    // 연관관계 삭제 메서드
    public void removeAdvancedCourse(Course course) {
        for (int i=0; i<advancedCourseList.size(); i++) {
            if(advancedCourseList.get(i).equals(course)){
                advancedCourseList.remove(i); }
        }
    }

    // 생성 메서드
    public static Course createCourse(String courseNumber, String professorLastName, String courseName, Course... advancedCourses) {
        Course createdCourse = new Course();
        createdCourse.setCourseNumber(courseNumber);
        createdCourse.setProfessorLastName(professorLastName);
        createdCourse.setCourseName(courseName);
        for (Course course : advancedCourses) {
            createdCourse.addAdvancedCourse(course);
        }
        return createdCourse;
    }

    public static Course createCourse(String courseNumber, String professorLastName, String courseName, List<Course> advancedCourses) {
        Course createdCourse = new Course();
        createdCourse.setCourseNumber(courseNumber);
        createdCourse.setProfessorLastName(professorLastName);
        createdCourse.setCourseName(courseName);
        for (Course course : advancedCourses) {
            createdCourse.addAdvancedCourse(course);
        }
        return createdCourse;
    }
}
