package repository;

import com.academic.stub.student.AllStudentDataRequest;
import com.academic.stub.student.AllStudentDataResponse;
import com.academic.stub.student.StudentRepositoryGrpc;
import domain.Student;
import domain.StudentCourse;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentRepositoryImpl extends StudentRepositoryGrpc.StudentRepositoryImplBase {

    private static final Logger logger = Logger.getLogger(StudentRepositoryImpl.class.getName());
    private StudentRepository studentRepository;

    public StudentRepositoryImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void getAllStudentData(AllStudentDataRequest req, StreamObserver<AllStudentDataResponse> responseObserver) {
        AllStudentDataResponse response;
        try {

            ArrayList<AllStudentDataResponse.Student> studentResults = new ArrayList<>();
            List<Student> result = this.studentRepository.findAll();

            for (Student student : result) {
                ArrayList<String> courseNumbers = new ArrayList<>();
                List<StudentCourse> studentCourses = student.getStudentCourses();
                for (StudentCourse studentCourse : studentCourses) {
                    courseNumbers.add(studentCourse.getCourse().getCourseNumber());
                }

                AllStudentDataResponse.Student studentResult = AllStudentDataResponse
                        .Student
                        .newBuilder()
                        .setStudentName(student.getStudentName())
                        .setStudentNumber(student.getStudentNumber())
                        .setMajor(student.getMajor())
                        .addAllCourses(courseNumbers).build();

                studentResults.add(studentResult);
            }

            response = AllStudentDataResponse.newBuilder()
                    .addAllStudents(studentResults).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }
}
