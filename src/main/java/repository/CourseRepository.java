package repository;

import com.academic.stub.academic.AddCourseRequest;
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
        this.em = MainRepository.em;
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

    public List<Course> findCoursesByCourseNumber(ProtocolStringList courseNumberList) throws NullDataException {
        List<Course> coursesResult = new ArrayList<>();
        for (String courseNumber : courseNumberList) {
            Course singleResult = em.createQuery("select c from Course c where c.courseNumber = :courseNumber", Course.class)
                    .setParameter("courseNumber", courseNumber)
                    .getSingleResult();
            if(singleResult == null){
                throw new NullDataException("NO COURSE DATA FOUND BY COURSENUMBER");
            }
            coursesResult.add(singleResult);
        }
        return coursesResult;
    }

    public Course createCourse(AddCourseRequest request, List<Course> courseList) {
        Course course = Course.createCourse(request.getCourseNumber(), request.getProfessorLastName(), request.getCourseName(), courseList);
        em.persist(course);
        return course;
    }

    public Course createCourse(AddCourseRequest request) {
        Course course = Course.createCourse(request.getCourseNumber(), request.getProfessorLastName(), request.getCourseName());
        em.persist(course);
        return course;
    }

    public boolean save(Course course) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(course);
        tx.commit();
        return true;
    }

    public boolean deleteCourseByCourseNumber(String courseNumber) throws NullDataException {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Course findCourse = findCourseByCourseNumber(courseNumber);

        List<StudentCourse> studentCourses = em.createQuery("select sc from StudentCourse sc where sc.course = :course", StudentCourse.class)
                .setParameter("course", findCourse)
                .getResultList();

        for (StudentCourse studentCourse : studentCourses) {
            em.remove(studentCourse);
        }

        em.remove(findCourse);
        tx.commit();
        return true;
    }

    public Course findCourseByCourseNumber(String courseNumber) throws NullDataException {
        Course findCourse = em.createQuery("select c from Course c where c.courseNumber = :courseNumber", Course.class)
                .setParameter("courseNumber", courseNumber)
                .getSingleResult();

        if (findCourse == null) {
            throw new NullDataException("NO COURSE DATA FOUND BY COURSENUMBER");
        }
        return findCourse;
    }

}
