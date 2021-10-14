package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "student_course")
public class StudentCourse {

    @Id
    @GeneratedValue
    @Column(name = "student_course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    // 생성 메서드
    public static StudentCourse createStudentCourse(Course course) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourse(course);
        return studentCourse;
    }

}
