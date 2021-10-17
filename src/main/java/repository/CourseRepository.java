package repository;

import com.academic.stub.academic.AddCourseRequest;
import com.google.protobuf.ProtocolStringList;
import constant.Constants;
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
        List<Course> resultList = em.createQuery(Constants.ECourseRepository.eFindAllCourseQuery.getContent(), Course.class).getResultList();
        if (resultList.size() == Constants.ECourseRepository.eZero.getNumber()) {
            throw new NullDataException(Constants.ECourseRepository.eNoCourseDataExceptionMessage.getContent()); }
        return resultList;
    }

    public List<Course> findCoursesByCourseNumber(ProtocolStringList courseNumberList) throws NullDataException {
        List<Course> coursesResult = new ArrayList<>();
        for (String courseNumber : courseNumberList) {
            Course singleResult = em.createQuery(Constants.ECourseRepository.eFindCourseByCourseNumberQuery.getContent(), Course.class)
                    .setParameter(Constants.ECourseRepository.eCourseNumber.getContent(), courseNumber)
                    .getSingleResult();
            if(singleResult == null){
                throw new NullDataException(Constants.ECourseRepository.eNoCourseDataByCourseNumberExceptionMessage.getContent());
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
        if (studentCourses.size() != Constants.ECourseRepository.eZero.getNumber()) {
            logger.info(Constants.ECourseRepository.eDeleteStudentCourseDataWarningMessage.getContent());
            for (StudentCourse studentCourse : studentCourses) {
                em.remove(studentCourse);
            }
        }
        tx.commit();
    }

    private void deleteAdvancedCourseByCourse(Course findCourse) throws NullDataException {
        List<Course> courseList = findAll();
        if(courseList.size() != Constants.ECourseRepository.eZero.getNumber()){
            logger.info(Constants.ECourseRepository.eDeleteAdvancedCourseDataWarningMessage.getContent());
            for (Course course : courseList) { course.removeAdvancedCourse(findCourse); }
        } if (findCourse.getAdvancedCourseList().size() != Constants.ECourseRepository.eZero.getNumber()) {
            findCourse.getAdvancedCourseList().clear();
        }
    }

    public Course findCourseByCourseNumber(String courseNumber) throws NullDataException {
        List<Course> findCourseList = em.createQuery(Constants.ECourseRepository.eFindCourseListByCourseNumberQuery.getContent(), Course.class)
                .setParameter(Constants.ECourseRepository.eCourseNumber.getContent(), courseNumber)
                .getResultList();

        if (findCourseList.size() == Constants.ECourseRepository.eZero.getNumber()) {
            throw new NullDataException(Constants.ECourseRepository.eNoCourseDataByCourseNumberExceptionMessage.getContent()); }
        if (findCourseList.size() > Constants.ECourseRepository.eOne.getNumber()) {
            new DuplicateDataException(Constants.ECourseRepository.eManyCoursesByCourseNumberExceptionMessage.getContent());}
        return findCourseList.get(Constants.ECourseRepository.eOne.getNumber());
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
