package service;

import com.academic.stub.academic.*;
import constant.Constants.EStudentServiceImpl;
import entity.Student;
import entity.StudentCourse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import repository.CourseRepository;
import repository.StudentCourseRepository;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private StudentCourseRepository studentCourseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
                              StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    @Override
    public void getAllStudentsData(EmptyRequest empty, StreamObserver<AllStudentsDataResponse> responseObserver) {
        try {
            List<Student> result = studentRepository.findAll();
            AllStudentsDataResponse response = setAllStudentsDataResponse(result);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + EStudentServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        }
    }

    private AllStudentsDataResponse setAllStudentsDataResponse(List<Student> result) {
        ArrayList<AllStudentsDataResponse.Student> studentResults = new ArrayList<>();
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
        return AllStudentsDataResponse.newBuilder()
                .addAllStudents(studentResults).build();
    }

    @Override
    public void addStudentData(AddStudentRequest request, StreamObserver<IsCompletedResponse> responseObserver){
        try {
            validationStudent(request);
            studentRepository.findStudentByStudentNumber(request.getStudentNumber(), EStudentServiceImpl.eTrue.getCheck());
            Student student = studentRepository.createStudent(request);
            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(studentRepository.save(student)).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + EStudentServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    @Override
    public void deleteStudentData(DeleteStudentRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            validationStudentNumber(request);
            Student findStudent = studentRepository.findStudentByStudentNumber(request.getStudentNumber(),
                    EStudentServiceImpl.eFalse.getCheck());
            List<StudentCourse> findStudentCourses = studentCourseRepository.findStudentCourseByStudent(findStudent);
            if (findStudentCourses.size() != EStudentServiceImpl.eZero.getNumber()) {
                studentCourseRepository.deleteStudentCourse(findStudentCourses); }
            boolean isCompletedDelete = studentRepository.deleteStudentByFindStudent(findStudent);

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(isCompletedDelete).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + EStudentServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    /**
     * validation
     */

    private void validationStudent(AddStudentRequest request){
        if (request.getStudentName().equals(EStudentServiceImpl.eEmpty.getContent()) ||
                request.getStudentNumber().equals(EStudentServiceImpl.eEmpty.getContent())
                || request.getMajor().equals(EStudentServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(EStudentServiceImpl.eEmptyRequestStudentExceptionMessage.getContent()); }
    }

    private void validationStudentNumber(DeleteStudentRequest request) {
        if (request.getStudentNumber().equals(EStudentServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(EStudentServiceImpl.eEmptyRequestStudentNumberExceptionMessage.getContent()); } }

}


