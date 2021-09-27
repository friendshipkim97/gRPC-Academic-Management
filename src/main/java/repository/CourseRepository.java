package repository;

import entity.Course;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class CourseRepository{

    private static final Logger logger = Logger.getLogger(CourseRepository.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public CourseRepository() {
        this.emf = MainRepository.emf;
        this.em = emf.createEntityManager();
    }

    public List<Course> findAll(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> resultList = em.createQuery("select c from Course c", Course.class).getResultList();
        tx.commit();
        if (resultList == null) {
            throw new NoSuchElementException("NO DATA FOUND");
        }
        return resultList;
    }
}
