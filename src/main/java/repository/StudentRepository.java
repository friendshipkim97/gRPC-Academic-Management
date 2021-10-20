package repository;

import com.academic.stub.academic.AddStudentRequest;
import constant.Constants.EStudentRepository;
import constant.Constants.EStudentRepository.*;
import entity.Student;
import entity.StudentCourse;
import exception.DuplicateDataException;
import exception.ExistingDataException;
import exception.NullDataException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Logger;

public class StudentRepository{

    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public StudentRepository() {
        this.emf = MainRepository.emf;
        this.em = MainRepository.em;
    }

    public List<Student> findAll() throws NullDataException {
        List<Student> resultList = em.createQuery(EStudentRepository.eFindAllStudentQuery.getContent(), Student.class).getResultList();
        if (resultList.size() == EStudentRepository.eZero.getNumber()) {
            throw new NullDataException(EStudentRepository.eNoStudentDataExceptionMessage.getContent()); }
        return resultList;
    }

    public boolean save(Student student){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(student);
        tx.commit();
        return true;
    }

    public boolean deleteStudentByStudentNumber(String studentNumber) throws NullDataException, DuplicateDataException, ExistingDataException {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student findStudent = findStudentByStudentNumber(studentNumber, EStudentRepository.eFalse.getCheck());
        List<StudentCourse> studentCourses = em.createQuery(EStudentRepository.eFindStudentCourseByStudentQuery.getContent(), StudentCourse.class)
                .setParameter(EStudentRepository.eStudent.getContent(), findStudent)
                .getResultList();

        for (StudentCourse studentCourse : studentCourses) { em.remove(studentCourse); }
        em.remove(findStudent);
        tx.commit();
        return true;
    }

    public Student findStudentByStudentNumber(String studentNumber, Boolean checkForStudentAdd) throws NullDataException, DuplicateDataException, ExistingDataException {

        List<Student> findStudentList = em.createQuery(EStudentRepository.eFindStudentByStudentNumberQuery.getContent(), Student.class)
                .setParameter(EStudentRepository.eStudentNumber.getContent(), studentNumber)
                .getResultList();

        if (checkForStudentAdd == EStudentRepository.eFalse.getCheck() && findStudentList.size() == EStudentRepository.eZero.getNumber()) {
            throw new NullDataException(EStudentRepository.eNoStudentDataByStudentNumberExceptionMessage.getContent()); }
        else if (checkForStudentAdd == EStudentRepository.eFalse.getCheck() && findStudentList.size() > EStudentRepository.eOne.getNumber()) {
            throw new DuplicateDataException(EStudentRepository.eManyStudentsDataByStudentNumberExceptionMessage.getContent()); }
        else if (checkForStudentAdd == EStudentRepository.eTrue.getCheck() && findStudentList.size() >= EStudentRepository.eOne.getNumber()) {
            throw new ExistingDataException(EStudentRepository.eAlreadyStudentNumberExceptionMessage.getContent());}
        else if(checkForStudentAdd == EStudentRepository.eTrue.getCheck() && findStudentList.size() == EStudentRepository.eZero.getNumber()) { return null;}
        return findStudentList.get(EStudentRepository.eZero.getNumber());

    }

    public Student createStudent(AddStudentRequest request) {
        Student student = Student.createStudent(request.getStudentNumber(), request.getStudentName(), request.getMajor());
        em.persist(student);
        return student;
    }

    public void addStudentCourse(Student findStudent, StudentCourse studentCourse) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        findStudent.addStudentCourse(studentCourse);
        em.persist(findStudent);
        tx.commit();
    }
}
