package repository;

import entity.Course;
import entity.StudentCourse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.logging.Logger;

public class StudentCourseRepository {

    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public StudentCourseRepository() {
        this.emf = MainRepository.emf;
        this.em = emf.createEntityManager();
    }

    public StudentCourse findStudentCourseByStudentCourseId(Long studentCourseId) {
        return em.find(StudentCourse.class, studentCourseId);
    }

    public StudentCourse createStudentCourse(Course course) {
        StudentCourse studentCourse = StudentCourse.createStudentCourse(course);
        em.persist(studentCourse);
        return studentCourse;
    }
}
