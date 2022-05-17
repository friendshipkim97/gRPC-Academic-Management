package entity;

import lombok.Getter;
import lombok.Setter;
import service.StudentServiceImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());

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
    public Boolean removeAdvancedCourse(Course course) {
        Boolean deleteCheck = false;
        for(Iterator<Course> itr = advancedCourseList.iterator(); itr.hasNext();){
            Course courseTemp = itr.next();
            if(courseTemp.equals(course)) {
                System.out.println("course = " + course.getCourseName());
                deleteCheck = true;
                itr.remove(); 
            }
        }
        return deleteCheck;
    }

    // 연관관계 삭제 메서드
//    public boolean removeCourseList(Course course) {
//        Boolean deleteCheck = false;
//        for(Iterator<Course> itr = courseList.iterator(); itr.hasNext();){
//            Course courseTemp = itr.next();
//            if(courseTemp.equals(course)) {
//                deleteCheck = true;
//                itr.remove();
//            }
//        }
//        return deleteCheck;
//    }

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
