package repository;

import com.google.protobuf.ProtocolStringList;
import entity.Course;
import entity.StudentCourse;
import exception.NullDataException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CourseRepository{

    private static final Logger logger = Logger.getLogger(CourseRepository.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public CourseRepository() {
        this.emf = MainRepository.emf;
        this.em = emf.createEntityManager();
    }

    public List<Course> findAll() throws NullDataException {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> resultList = em.createQuery("select c from Course c", Course.class).getResultList();
        if (resultList.size() == 0) {
            throw new NullDataException("NO COURSE DATA FOUND");
        }
        tx.commit();
        return resultList;
    }

    public List<Course> findCoursesByCourseNumber(ProtocolStringList courseNumberList) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> coursesResult = new ArrayList<>();
        for (String courseNumber : courseNumberList) {
            Course singleResult = em.createQuery("select c from Course c where c.courseNumber = :courseNumber", Course.class)
                    .setParameter("courseNumber", courseNumber)
                    .getSingleResult();
            coursesResult.add(singleResult);
        }
        return coursesResult;
    }

    public StudentCourse createStudentCourse(Course course) {
        StudentCourse studentCourse = StudentCourse.createStudentCourse(course);
        em.persist(studentCourse);
        return studentCourse;
    }

//    public boolean save(Course course) {
//
//    }
}
