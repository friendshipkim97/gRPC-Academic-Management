package repository;

import domain.Student;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentRepository {

    private static final Logger logger = Logger.getLogger(StudentRepository.class.getName());
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("academic");
    EntityManager em = emf.createEntityManager();

    public List<Student> findAll(){

        List<Student> resultList = em.createQuery("select * from Student", Student.class).getResultList();

        if (resultList == null) {
            throw new NoSuchElementException("NO DATA FOUND");
        }
        return resultList;
    }

    // The good old main method is defined here :)
    public static void main(String[] args) {
        /*
            All you have to do here is to create an instance of the Server class,
            To create the instance we use the ServerBuilder,
            with the ServerBuilder we have to provide the port that our ResultService will listen to
            and then the handler which is the ResultServiceImpl class to handle the requests.
        */
        Server server = ServerBuilder.forPort(50051)
                .addService(new StudentRepositoryImpl(new StudentRepository()))
                .build();
        try {
            server.start();
            logger.log(Level.INFO, "STUDENT REPOSITORY STARTED ON PORT 50051");
            // This awaitTermination method will help to remain the server, otherwise the server will shutdown quickly
            server.awaitTermination();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "STUDENT REPOSITORY DID NOT START");
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "STUDENT REPOSITORY SHUT DOWN ON INTERRUPTED");
        }
    }
}
