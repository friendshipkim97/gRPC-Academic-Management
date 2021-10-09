package repository;

import com.academic.stub.academic.AddStudentRequest;
import entity.Course;
import entity.Student;
import entity.StudentCourse;
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
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Student> resultList = em.createQuery("select s from Student s", Student.class).getResultList();
        tx.commit();
        if (resultList.size() == 0) {
            throw new NullDataException("NO STUDENT DATA FOUND");
        }
        return resultList;
    }

    public boolean save(Student student){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(student);
        tx.commit();
        return true;
    }

    // SQLIntegrityConstraintViolationException 위반 예외 추가하기
    // NullDataException 추가
    public boolean deleteStudentByStudentNumber(String studentNumber) throws NullDataException {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Student findStudent = em.createQuery("select s from Student s where s.studentNumber = :studentNumber", Student.class)
                .setParameter("studentNumber", studentNumber)
                .getSingleResult();

        if (findStudent == null) {
            throw new NullDataException("NO STUDENT DATA FOUND BY ID");
        }

        List<StudentCourse> studentCourses = em.createQuery("select sc from StudentCourse sc where sc.student = :student", StudentCourse.class)
                .setParameter("student", findStudent)
                .getResultList();

        for (StudentCourse studentCourse : studentCourses) {
            em.remove(studentCourse);
        }

        em.remove(findStudent);
        tx.commit();
        return true;
    }

    public Student createStudent(AddStudentRequest request, List<StudentCourse> studentCourseList) {
        Student student = Student.createStudent(request.getStudentNumber(), request.getStudentName(), request.getMajor(), studentCourseList);
        em.persist(student);
        return student;

    }

    public Student createStudent(AddStudentRequest request) {
        Student student = Student.createStudent(request.getStudentNumber(), request.getStudentName(), request.getMajor());
        em.persist(student);
        return student;

    }
}
