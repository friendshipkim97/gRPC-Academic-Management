package service;

import com.academic.stub.academic.ApplicationForCourseRequest;
import com.academic.stub.academic.IsCompletedResponse;
import com.academic.stub.academic.StudentCourseServiceGrpc;
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
    public ExistingDataException applicationForCourse(ApplicationForCourseRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            validationStudentId(request);
            validationCourseId(request);
            Student findStudent = studentRepository.findStudentByStudentNumber(request.getStudentNumber(), false);
            Course findCourse = courseRepository.findCourseByCourseNumber(request.getCourseNumber());
            List<StudentCourse> studentCourses = studentCourseRepository.findStudentCourseByStudent(findStudent);
            if(studentCourses.size() != 0){ validationExistingCourse(findCourse, studentCourses); }
            validationAdvancedCourse(findCourse, studentCourses);

            StudentCourse studentCourse = studentCourseRepository.createStudentCourse(findCourse);
            studentRepository.addStudentCourse(findStudent, studentCourse);

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(true).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + " : "+ e.getMessage());
            Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return null;
        }
        return null;
    }

    /**
     * validation
     */

    private void validationCourseId(ApplicationForCourseRequest request) {
        if (request.getCourseNumber().equals("")) {
            throw new IllegalArgumentException("THE COURSE ID'S INPUT IS INVALID.");
        }
    }

    private void validationStudentId(ApplicationForCourseRequest request) {
        if (request.getStudentNumber().equals("")) {
            throw new IllegalArgumentException("THE STUDENT ID'S INPUT IS INVALID.");
        }
    }

    private void validationExistingCourse(Course findCourse, List<StudentCourse> studentCourses) throws ExistingDataException {
        for (StudentCourse studentCourse : studentCourses) {
            if (studentCourse.getCourse().getId() == findCourse.getId()) {
                throw new ExistingDataException("THIS IS A COURSE YOU ARE ALREADY TAKING");
            }
        }
    }

    private void validationAdvancedCourse(Course findCourse, List<StudentCourse> studentCourses) throws AdvancedCourseException {
        boolean advancedCourseCheck = false;
        for (Course course : findCourse.getAdvancedCourseList()) {
            for (StudentCourse studentCourse : studentCourses) {
                if (studentCourse.getCourse().getId() == course.getId()) { advancedCourseCheck = true; }
            }
        } if (advancedCourseCheck == false) {
            throw new AdvancedCourseException("YOU DIDN'T TAKE THE ADVANCED COURSE");
        }
    }

}
