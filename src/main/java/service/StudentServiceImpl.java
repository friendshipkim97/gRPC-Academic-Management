package service;

import com.academic.stub.academic.*;
import com.google.protobuf.ProtocolStringList;
import entity.Course;
import entity.Student;
import entity.StudentCourse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import repository.CourseRepository;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static entity.Student.createStudent;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public StudentServiceImpl() {
        studentRepository = new StudentRepository();
        courseRepository = new CourseRepository();
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
    public void addStudentData(AddStudentRequest request, StreamObserver<IsCompletedResponse> responseObserver){
        try {
            validationStudent(request);
            List<Course> coursesResult;
            List<StudentCourse> studentCoursesResult = new ArrayList<>();
            StudentCourse[] studentCourseArray;
            Student student;

            if (request.getCourseNumberList().size() != 0) {
                System.out.println("로직 들어오는지?");
                coursesResult = courseRepository.findCoursesByCourseNumber(request.getCourseNumberList());
                for (Course course : coursesResult) {
                    StudentCourse studentCourse = courseRepository.createStudentCourse(course);
                    System.out.println("코스이름2"+studentCourse.getId());
                    studentCoursesResult.add(studentCourse);
                }
                studentCourseArray = studentCoursesResult.toArray(new StudentCourse[request.getCourseNumberCount()]);
                student = studentRepository.createStudent(request, studentCourseArray);
            } else{
                student = studentRepository.createStudent(request);
            }

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(studentRepository.save(student)).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + " : "+ e.getMessage());
            Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

//    private void getCourseByCourseNumber(ProtocolStringList courseNumberList) {
//            courseRepository.findCoursesByCourseNumber();
//    }

    private void validationStudent(AddStudentRequest request){
        if (request.getStudentName().equals("") || request.getStudentNumber().equals("") || request.getMajor().equals("")) {
            throw new IllegalArgumentException("THE STUDENT INPUT IS INVALID.");
        }
    }

    @Override
    public void deleteStudentData(DeleteStudentRequest request, StreamObserver<IsCompletedResponse> responseObserver) {

        try {
            validationStudentId(request);
            boolean isCompletedDelete = studentRepository.delete(request.getStudentId());
            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(isCompletedDelete).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + " : "+ e.getMessage());
            Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    private void validationStudentId(DeleteStudentRequest request) {
        if (request.getStudentId().equals("")) {
            throw new IllegalArgumentException("THE STUDENT ID IS INVALID.");
        }
    }

}


