package repository;

import com.academic.stub.academic.AddStudentRequest;
import entity.Student;
import entity.StudentCourse;
import exception.NullDataException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.xml.transform.Source;
import java.util.List;
import java.util.logging.Logger;

import static entity.Student.createStudent;

public class StudentRepository{

    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public StudentRepository() {
        this.emf = MainRepository.emf;
        this.em = emf.createEntityManager();
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

        if(student.getId() == null){
            System.out.println("학생 정보를 생성합니다.");
            em.persist(student);
        } else {
            System.out.println("기존 학생 이므로 학생 정보를 수정합니다.");
            em.merge(student);
        }
        tx.commit();
        return true;
    }

    // SQLIntegrityConstraintViolationException 위반 예외 추가하기
    // NullDataException 추가
    public boolean delete(String studentId) throws NullDataException {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Long longStudentId = Long.valueOf(studentId);
        Student findStudent = em.find(Student.class, longStudentId);
        if (findStudent == null) {
            throw new NullDataException("NO STUDENT DATA FOUND BY ID");
        }
        em.remove(findStudent);
        tx.commit();
        return true;
    }

    public Student createStudent(AddStudentRequest request, StudentCourse[] studentCourseArray) {

        System.out.println("0번"+studentCourseArray[0]);
        System.out.println("1번"+studentCourseArray[1]);
        Student student = Student.createStudent(request.getStudentNumber(), request.getStudentName(), request.getMajor(), studentCourseArray);
        em.persist(student);
        return student;

    }

    public Student createStudent(AddStudentRequest request) {
        Student student = Student.createStudent(request.getStudentNumber(), request.getStudentName(), request.getMajor());
        em.persist(student);
        return student;

    }
}
