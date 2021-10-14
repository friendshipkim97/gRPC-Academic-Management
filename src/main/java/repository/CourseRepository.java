package repository;

import com.academic.stub.academic.AddCourseRequest;
import com.google.protobuf.ProtocolStringList;
import entity.Course;
import entity.StudentCourse;
import exception.DuplicateDataException;
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
    private StudentCourseRepository studentCourseRepository;

    public CourseRepository(StudentCourseRepository studentCourseRepository) {
        this.emf = MainRepository.emf;
        this.em = MainRepository.em;
        this.studentCourseRepository = studentCourseRepository;
    }

    public List<Course> findAll() throws NullDataException {
        List<Course> resultList = em.createQuery("select c from Course c", Course.class).getResultList();
        if (resultList.size() == 0) { throw new NullDataException("NO COURSE DATA FOUND"); }
        return resultList;
    }

    public List<Course> findCoursesByCourseNumber(ProtocolStringList courseNumberList) throws NullDataException {
        List<Course> coursesResult = new ArrayList<>();
        for (String courseNumber : courseNumberList) {
            Course singleResult = em.createQuery("select c from Course c where c.courseNumber = :courseNumber", Course.class)
                    .setParameter("courseNumber", courseNumber)
                    .getSingleResult();
            if(singleResult == null){
                throw new NullDataException("NO COURSE DATA FOUND BY COURSE_NUMBER");
            } coursesResult.add(singleResult); }
        return coursesResult;
    }


    public Course createCourse(AddCourseRequest request) {
        Course course = Course.createCourse(request.getCourseNumber(), request.getProfessorLastName(), request.getCourseName());
        em.persist(course);
        return course;
    }

    public boolean deleteCourseByCourseNumber(String courseNumber) throws NullDataException {

        Course findCourse = findCourseByCourseNumber(courseNumber);
        List<StudentCourse> studentCourses = this.studentCourseRepository.findStudentCourseByCourse(findCourse);
        deleteStudentCourseByCourse(studentCourses);
        deleteAdvancedCourseByCourse(findCourse);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(findCourse);
        tx.commit();
        return true;
    }

    private void deleteStudentCourseByCourse(List<StudentCourse> studentCourses) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (studentCourses.size() != 0) {
            logger.info("WARNING! DELETE STUDENT'S COURSE DATA");
            for (StudentCourse studentCourse : studentCourses) {
                em.remove(studentCourse);
            }
        }
        tx.commit();
    }

    private void deleteAdvancedCourseByCourse(Course findCourse) throws NullDataException {
        List<Course> courseList = findAll();
        if(courseList.size() != 0){
            logger.info("WARNING! DELETE COURSE'S ADVANCED_COURSE DATA");
            for (Course course : courseList) {
                course.removeAdvancedCourse(findCourse);
            }
        } if (findCourse.getAdvancedCourseList().size() != 0) {
            findCourse.getAdvancedCourseList().clear();
        }
    }

    public Course findCourseByCourseNumber(String courseNumber) throws NullDataException {
        List<Course> findCourseList = em.createQuery("select c from Course c where c.courseNumber = :courseNumber", Course.class)
                .setParameter("courseNumber", courseNumber)
                .getResultList();

        if (findCourseList.size() == 0) { throw new NullDataException("NO COURSE DATA FOUND BY COURSE_NUMBER"); }
        if (findCourseList.size() > 1) { new DuplicateDataException("THERE ARE SEVERAL COURSES WITH THE SAME COURSE_NUMBER");}
        return findCourseList.get(0);
    }

    public Course addCourseWithAdvancedCourse(AddCourseRequest request, List<Course> coursesResult) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Course createdCourse = Course.createCourse(request.getCourseNumber(), request.getProfessorLastName(), request.getCourseName(), coursesResult);
        em.persist(createdCourse);
        tx.commit();
        return createdCourse;
    }
}
