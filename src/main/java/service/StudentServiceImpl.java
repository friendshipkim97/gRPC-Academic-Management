package service;

import com.academic.stub.academic.AllStudentsDataResponse;
import com.academic.stub.academic.Empty;
import com.academic.stub.academic.StudentServiceGrpc;
import entity.Student;
import entity.StudentCourse;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    private StudentRepository studentRepository;

    public StudentServiceImpl() {
        studentRepository = new StudentRepository();
    }

    @Override
    public void getAllStudentsData(Empty empty, StreamObserver<AllStudentsDataResponse> responseObserver) {
        AllStudentsDataResponse response;
        try {

            ArrayList<AllStudentsDataResponse.Student> studentResults = new ArrayList<>();
            List<Student> result = studentRepository.findAll();

            for (Student student : result) {
                ArrayList<String> courseNumbers = new ArrayList<>();
                List<StudentCourse> studentCourses = student.getStudentCourses();
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
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }

}
