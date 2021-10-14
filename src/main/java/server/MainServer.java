package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import repository.*;
import service.CourseServiceImpl;
import service.StudentCourseServiceImpl;
import service.StudentServiceImpl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class MainServer {

    private static final Logger logger = Logger.getLogger(MainServer.class.getName());

    public static void main(String[] args) {

        new MainRepository();

        StudentCourseRepository studentCourseRepository = new StudentCourseRepository();
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository(studentCourseRepository);

        Server server = ServerBuilder.forPort(50050)
                .addService(new StudentServiceImpl(studentRepository, courseRepository, studentCourseRepository))
                .addService(new CourseServiceImpl(courseRepository))
                .addService(new StudentCourseServiceImpl(studentRepository, courseRepository, studentCourseRepository))
                .build();
        try {
            server.start();
            logger.log(Level.INFO, "STUDENT REPOSITORY STARTED ON PORT 50050");
            server.awaitTermination();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "STUDENT REPOSITORY DID NOT START");
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "STUDENT REPOSITORY SHUT DOWN ON INTERRUPTED");
        }
    }

}
