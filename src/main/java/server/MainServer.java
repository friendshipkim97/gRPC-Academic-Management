package server;

import constant.Constants.EMainServer;
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
    private StudentCourseRepository studentCourseRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public MainServer() {
        new MainRepository();
        studentCourseRepository = new StudentCourseRepository();
        studentRepository = new StudentRepository();
        courseRepository = new CourseRepository(studentCourseRepository);
    }

    public void setConnection() {
        Server server = ServerBuilder.forPort(EMainServer.ePortNumber.getNumber())
                .addService(new StudentServiceImpl(studentRepository, courseRepository, studentCourseRepository))
                .addService(new CourseServiceImpl(courseRepository))
                .addService(new StudentCourseServiceImpl(studentRepository, courseRepository, studentCourseRepository))
                .build();
        try {
            server.start();
            logger.log(Level.INFO, EMainServer.eServerStartSuccessMessage.getContent());
            server.awaitTermination();
        } catch (IOException e) {
            logger.log(Level.SEVERE, EMainServer.eServerStartErrorMessage.getContent());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, EMainServer.eServerStartShutDownMessage.getContent());
        }
    }

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        mainServer.setConnection(); }

}
