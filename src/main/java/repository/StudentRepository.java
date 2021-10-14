package repository;

import com.academic.stub.academic.AddStudentRequest;
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
        List<Student> resultList = em.createQuery("select s from Student s", Student.class).getResultList();
        if (resultList.size() == 0) { throw new NullDataException("NO STUDENT DATA FOUND"); }
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
        Student findStudent = findStudentByStudentNumber(studentNumber, false);
        List<StudentCourse> studentCourses = em.createQuery("select sc from StudentCourse sc where sc.student = :student", StudentCourse.class)
                .setParameter("student", findStudent)
                .getResultList();

        for (StudentCourse studentCourse : studentCourses) { em.remove(studentCourse); }
        em.remove(findStudent);
        tx.commit();
        return true;
    }

    public Student findStudentByStudentNumber(String studentNumber, Boolean checkForStudentAdd) throws NullDataException, DuplicateDataException, ExistingDataException {

        List<Student> findStudentList = em.createQuery("select s from Student s where s.studentNumber = :studentNumber", Student.class)
                .setParameter("studentNumber", studentNumber)
                .getResultList();

        if (checkForStudentAdd == false && findStudentList.size() == 0) { throw new NullDataException("NO STUDENT DATA FOUND BY STUDENT_NUMBER"); }
        else if (checkForStudentAdd == false && findStudentList.size() > 1) { throw new DuplicateDataException("THERE ARE SEVERAL STUDENTS WITH THE SAME STUDENT_NUMBER"); }
        else if (checkForStudentAdd == true && findStudentList.size() >= 1) { throw new ExistingDataException("THIS STUDENT NUMBER ALREADY EXISTS.");}
        else if(checkForStudentAdd == true && findStudentList.size() == 0) { return null;}
        return findStudentList.get(0);

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
