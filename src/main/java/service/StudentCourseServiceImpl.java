package service;

import com.academic.stub.academic.ApplicationForCourseRequest;
import com.academic.stub.academic.IsCompletedResponse;
import com.academic.stub.academic.StudentCourseServiceGrpc;
import constant.Constants;
import entity.Course;
import entity.Student;
import entity.StudentCourse;
import exception.AdvancedCourseException;
import exception.ExistingDataException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import repository.CourseRepository;
import repository.StudentCourseRepository;
import repository.StudentRepository;

import java.util.List;
import java.util.logging.Logger;

public class StudentCourseServiceImpl extends StudentCourseServiceGrpc.StudentCourseServiceImplBase {

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private StudentCourseRepository studentCourseRepository;

    public StudentCourseServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
                              StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    @Override
    public void applicationForCourse(ApplicationForCourseRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            validationStudentId(request);
            validationCourseId(request);
            Student findStudent = studentRepository.findStudentByStudentNumber(request.getStudentNumber(),
                    Constants.EStudentCourseServiceImpl.eFalse.getCheck());
            Course findCourse = courseRepository.findCourseByCourseNumber(request.getCourseNumber());
            List<StudentCourse> studentCourses = studentCourseRepository.findStudentCourseByStudent(findStudent);
            if(studentCourses.size() != Constants.EStudentCourseServiceImpl.eZero.getNumber()){ validationExistingCourse(findCourse, studentCourses); }
            validationAdvancedCourse(findCourse, studentCourses);

            StudentCourse studentCourse = studentCourseRepository.createStudentCourse(findCourse);
            studentRepository.addStudentCourse(findStudent, studentCourse);

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(Constants.EStudentCourseServiceImpl.eTrue.getCheck()).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + Constants.EStudentCourseServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return ;
        }
    }

    /**
     * validation
     */

    private void validationCourseId(ApplicationForCourseRequest request) {
        if (request.getCourseNumber().equals(Constants.EStudentCourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(Constants.EStudentCourseServiceImpl.eEmptyRequestCourseIdExceptionMessage.getContent());
        }
    }

    private void validationStudentId(ApplicationForCourseRequest request) {
        if (request.getStudentNumber().equals(Constants.EStudentCourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(Constants.EStudentCourseServiceImpl.eEmptyRequestStudentIdExceptionMessage.getContent());
        }
    }

    private void validationExistingCourse(Course findCourse, List<StudentCourse> studentCourses) throws ExistingDataException {
        for (StudentCourse studentCourse : studentCourses) {
            if (studentCourse.getCourse().getId() == findCourse.getId()) {
                throw new ExistingDataException(Constants.EStudentCourseServiceImpl.eAlreadyTakingCourseExceptionMessage.getContent());
            }
        }
    }

    private void validationAdvancedCourse(Course findCourse, List<StudentCourse> studentCourses) throws AdvancedCourseException {
        boolean advancedCourseCheck = Constants.EStudentCourseServiceImpl.eFalse.getCheck();
        for (Course course : findCourse.getAdvancedCourseList()) {
            for (StudentCourse studentCourse : studentCourses) { if (studentCourse.getCourse().getId() == course.getId()) {
                advancedCourseCheck = Constants.EStudentCourseServiceImpl.eTrue.getCheck(); } }
        } if (advancedCourseCheck == Constants.EStudentCourseServiceImpl.eFalse.getCheck()) {
            throw new AdvancedCourseException(Constants.EStudentCourseServiceImpl.eTakeAdvancedCourseExceptionMessage.getContent()); }
    }

}
