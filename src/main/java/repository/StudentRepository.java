package repository;

import entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.NoSuchElementException;
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
}
