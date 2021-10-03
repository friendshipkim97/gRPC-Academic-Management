package repository;

import com.mysql.cj.log.Log;
import entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentRepository{

    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public StudentRepository() {
        this.emf = MainRepository.emf;
        this.em = emf.createEntityManager();
    }

    public List<Student> findAll(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Student> resultList = em.createQuery("select s from Student s", Student.class).getResultList();
        tx.commit();
        if (resultList == null) {
            throw new NoSuchElementException("NO DATA FOUND");
        }
        return resultList;
    }

    public boolean save(Student student){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (student == null) {
            System.out.println("학생의 정보가 없습니다.");
            return false;
        }
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

    public boolean delete(String studentId) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (studentId == null) {
            System.out.println("아이디가 입력되지 않았습니다.");
            return false;
        }
        Long longStudentId = Long.valueOf(studentId);
        Student findStudent = em.find(Student.class, longStudentId);
        if (findStudent == null) {
            System.out.println("해당 아이디를 가진 학생이 없습니다.");
            return false;
        }
        em.remove(findStudent);
        tx.commit();
        return true;
    }
}
