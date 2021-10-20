package repository;

import constant.Constants.EStudentCourseRepository;
import entity.Course;
import entity.Student;
import entity.StudentCourse;
import exception.NullDataException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Logger;

public class StudentCourseRepository {

    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public StudentCourseRepository() {
        this.emf = MainRepository.emf;
        this.em = MainRepository.em;
    }

    public StudentCourse createStudentCourse(Course course) {
        StudentCourse studentCourse = StudentCourse.createStudentCourse(course);
        em.persist(studentCourse);
        return studentCourse;
    }

    public List<StudentCourse> findStudentCourseByStudent(Student student) throws NullDataException {
        List<StudentCourse> studentList = em.createQuery(EStudentCourseRepository.eFindStudentCourseByStudentQuery.getContent(), StudentCourse.class)
                .setParameter(EStudentCourseRepository.eStudent.getContent(), student)
                .getResultList();
        return studentList;
    }

    public void deleteStudentCourse(List<StudentCourse> findStudentCourses) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (StudentCourse findStudentCourse : findStudentCourses) {
            em.remove(findStudentCourse);
        }
        tx.commit();
    }

    public List<StudentCourse> findStudentCourseByCourse(Course findCourse) {
        List<StudentCourse> studentCourses = em.createQuery(EStudentCourseRepository.eFindStudentCourseByCourseQuery.getContent(), StudentCourse.class)
                .setParameter(EStudentCourseRepository.eCourse.getContent(), findCourse)
                .getResultList();
        return studentCourses;
    }
}
