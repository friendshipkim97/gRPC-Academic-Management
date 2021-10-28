package repository;

import com.academic.stub.academic.AddCourseRequest;
import com.google.protobuf.ProtocolStringList;
import constant.Constants.ECourseRepository;
import entity.Course;
import entity.StudentCourse;
import exception.ExistingDataException;
import exception.NullDataException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Iterator;
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
        List<Course> resultList = em.createQuery(ECourseRepository.eFindAllCourseQuery.getContent(), Course.class).getResultList();
        if (resultList.size() == ECourseRepository.eZero.getNumber()) {
            throw new NullDataException(ECourseRepository.eNoCourseDataExceptionMessage.getContent()); }
        return resultList;
    }

    public List<Course> findCoursesByCourseNumber(ProtocolStringList courseNumberList){
        List<Course> coursesResult = new ArrayList<>();
        for (String courseNumber : courseNumberList) {
            Course singleResult = em.createQuery(ECourseRepository.eFindCourseByCourseNumberQuery.getContent(), Course.class)
                    .setParameter(ECourseRepository.eCourseNumber.getContent(), courseNumber)
                    .getSingleResult();
            coursesResult.add(singleResult); }
        return coursesResult;
    }


    public Course createCourse(AddCourseRequest request) {
        Course course = Course.createCourse(request.getCourseNumber(), request.getProfessorLastName(), request.getCourseName());
        em.persist(course);
        return course;
    }

    public boolean deleteCourseByCourseNumber(String courseNumber) throws NullDataException, ExistingDataException {

        Course findCourse = findCourseByCourseNumber(courseNumber);

        List<StudentCourse> studentCourses = this.studentCourseRepository.findStudentCourseByCourse(findCourse);

        deleteAdvancedCourseByCourse(findCourse);
        deleteStudentCourseByCourse(studentCourses);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(findCourse);
        tx.commit();
        return true;
    }

    private void deleteStudentCourseByCourse(List<StudentCourse> studentCourses) {
        if (studentCourses.size() != ECourseRepository.eZero.getNumber()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            logger.info(ECourseRepository.eDeleteStudentCourseDataWarningMessage.getContent());
            for (StudentCourse studentCourse : studentCourses) {
                em.remove(studentCourse);
            }
            tx.commit();
        }
    }

    private void deleteAdvancedCourseByCourse(Course findCourse) {

        List<Course> allCourseList = findAllNoException();

        findCourse.getAdvancedCourseList().clear();

        for (Iterator<Course> itr = allCourseList.iterator(); itr.hasNext(); ) {
            Course courseTemp = itr.next();
            if (courseTemp.removeAdvancedCourse(findCourse)) {
                logger.info(ECourseRepository.eDeleteAdvancedCourseDataWarningMessage.getContent());
            }
        }
    }

    public List<Course> findAllNoException(){
        List<Course> resultList = em.createQuery(ECourseRepository.eFindAllCourseQuery.getContent(), Course.class).getResultList();
        return resultList;
    }

    public Course findCourseByCourseNumber(String courseNumber) throws NullDataException, ExistingDataException {
        List<Course> findCourseList = em.createQuery(ECourseRepository.eFindCourseListByCourseNumberQuery.getContent(),
                Course.class)
                .setParameter(ECourseRepository.eCourseNumber.getContent(), courseNumber)
                .getResultList();
        if (findCourseList.size() == ECourseRepository.eZero.getNumber()) {
            throw new NullDataException(ECourseRepository.eNoCourseDataByCourseNumberExceptionMessage.getContent()); }
        else if (findCourseList.size() > ECourseRepository.eOne.getNumber()) {
            throw new ExistingDataException(ECourseRepository.eManyCoursesByCourseNumberExceptionMessage.getContent());}
        return findCourseList.get(ECourseRepository.eZero.getNumber());
    }

    public Course addCourseWithAdvancedCourse(AddCourseRequest request, List<Course> coursesResult) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Course createdCourse = Course.createCourse(request.getCourseNumber(), request.getProfessorLastName(),
                request.getCourseName(), coursesResult);
        em.persist(createdCourse);
        tx.commit();
        return createdCourse;
    }
}
