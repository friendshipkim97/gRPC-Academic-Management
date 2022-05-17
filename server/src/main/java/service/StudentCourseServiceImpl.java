package service;

import com.academic.stub.academic.ApplicationForCourseRequest;
import com.academic.stub.academic.IsCompletedResponse;
import com.academic.stub.academic.StudentCourseServiceGrpc;
import constant.Constants.EStudentCourseServiceImpl;
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
                    EStudentCourseServiceImpl.eFalse.getCheck());
            Course findCourse = courseRepository.findCourseByCourseNumber(request.getCourseNumber());
            List<StudentCourse> studentCourses = studentCourseRepository.findStudentCourseByStudent(findStudent);
            if(studentCourses.size() != EStudentCourseServiceImpl.eZero.getNumber()){
                validationExistingCourse(findCourse, studentCourses); }
            validationAdvancedCourse(findCourse, studentCourses);

            StudentCourse studentCourse = studentCourseRepository.createStudentCourse(findCourse);
            studentRepository.addStudentCourse(findStudent, studentCourse);

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(EStudentCourseServiceImpl.eTrue.getCheck()).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + EStudentCourseServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return ;
        }
    }

    /**
     * validation
     */

    private void validationCourseId(ApplicationForCourseRequest request) {
        if (request.getCourseNumber().equals(EStudentCourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(EStudentCourseServiceImpl.eEmptyRequestCourseIdExceptionMessage.getContent()); } }
    private void validationStudentId(ApplicationForCourseRequest request) {
        if (request.getStudentNumber().equals(EStudentCourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(EStudentCourseServiceImpl.eEmptyRequestStudentIdExceptionMessage.getContent()); } }
    private void validationExistingCourse(Course findCourse, List<StudentCourse> studentCourses) throws ExistingDataException {
        for (StudentCourse studentCourse : studentCourses) {
            if (studentCourse.getCourse().getId() == findCourse.getId()) {
                throw new ExistingDataException(EStudentCourseServiceImpl.eAlreadyTakingCourseExceptionMessage.getContent()); } } }
    private void validationAdvancedCourse(Course findCourse, List<StudentCourse> studentCourses) throws AdvancedCourseException {
        boolean advancedCourseCheck = EStudentCourseServiceImpl.eFalse.getCheck();
        if (findCourse.getAdvancedCourseList().size() != 0) {
            for (Course course : findCourse.getAdvancedCourseList()) {
                for (StudentCourse studentCourse : studentCourses) { if (studentCourse.getCourse().getId() == course.getId()) {
                    advancedCourseCheck = EStudentCourseServiceImpl.eTrue.getCheck(); } }
            } if (advancedCourseCheck == EStudentCourseServiceImpl.eFalse.getCheck()) {
                throw new AdvancedCourseException(EStudentCourseServiceImpl.eTakeAdvancedCourseExceptionMessage.getContent()); } } }

}
