package service;

import com.academic.stub.academic.*;
import entity.Student;
import entity.StudentCourse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static entity.Student.createStudent;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    private StudentRepository studentRepository;

    public StudentServiceImpl() {
        studentRepository = new StudentRepository();
    }

    @Override
    public void getAllStudentsData(EmptyRequest empty, StreamObserver<AllStudentsDataResponse> responseObserver) {
        AllStudentsDataResponse response;
        try {

            ArrayList<AllStudentsDataResponse.Student> studentResults = new ArrayList<>();
            List<Student> result = studentRepository.findAll();

            for (Student student : result) {
                ArrayList<String> courseNumbers = new ArrayList<>();
                List<StudentCourse> studentCourses = student.getStudentCourse();
                for (StudentCourse studentCourse : studentCourses) {
                    courseNumbers.add(studentCourse.getCourse().getCourseNumber());
                }
                AllStudentsDataResponse.Student studentResult = AllStudentsDataResponse
                        .Student
                        .newBuilder()
                        .setStudentName(student.getStudentName())
                        .setStudentNumber(student.getStudentNumber())
                        .setMajor(student.getMajor())
                        .addAllCourseNumber(courseNumbers).build();
                studentResults.add(studentResult);
            }

            response = AllStudentsDataResponse.newBuilder()
                    .addAllStudents(studentResults).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + " : "+ e.getMessage());
            Status status = Status.NOT_FOUND.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    @Override
    public void addStudentData(AddStudentRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            Student student = createStudent(request.getStudentNumber(), request.getStudentName(), request.getMajor());
            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(studentRepository.save(student)).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + " : "+ e.getMessage());
            Status status = Status.NOT_FOUND.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    @Override
    public void deleteStudentData(DeleteStudentRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            System.out.println("service");
            System.out.println(request.getStudentId());
            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(studentRepository.delete(request.getStudentId())).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + " : "+ e.getMessage());
            Status status = Status.NOT_FOUND.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }
}


