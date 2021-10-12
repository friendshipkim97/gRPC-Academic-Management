package service;

import com.academic.stub.academic.*;
import entity.Course;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import repository.CourseRepository;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CourseServiceImpl extends CourseServiceGrpc.CourseServiceImplBase {

    private static final Logger logger = Logger.getLogger(StudentServiceImpl.class.getName());
    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void getAllCoursesData(EmptyRequest empty, StreamObserver<AllCoursesDataResponse> responseObserver) {
        AllCoursesDataResponse response;
        try {

            ArrayList<AllCoursesDataResponse.Course> courseResults = new ArrayList<>();
            List<Course> result = courseRepository.findAll();

            for (Course course : result) {
                ArrayList<String> advancedCourseNumbers = new ArrayList<>();
                List<Course> advancedCourses = course.getChild();
                for (Course advancedCourse : advancedCourses) {
                    advancedCourseNumbers.add(advancedCourse.getCourseNumber());
                }
                AllCoursesDataResponse.Course courseResult = AllCoursesDataResponse
                        .Course
                        .newBuilder()
                        .setCourseNumber(course.getCourseNumber())
                        .setProfessorLastName(course.getProfessorLastName())
                        .setCourseName(course.getCourseName())
                        .addAllAdvancedCourseNumber(advancedCourseNumbers).build();
                courseResults.add(courseResult);
            }

            response = AllCoursesDataResponse.newBuilder()
                    .addAllCourses(courseResults).build();

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
    public void addCourseData(AddCourseRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            validationCourse(request);
            List<Course> coursesResult;

            Course createdCourse = courseRepository.createCourse(request);

            if (request.getAdvancedCourseNumberList().size() != 0) {
                coursesResult = courseRepository.findCoursesByCourseNumber(request.getAdvancedCourseNumberList());
                for (Course courseResult : coursesResult) {
                    courseRepository.updateCourseRepository(courseResult, createdCourse);
                }
            }

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(true).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        }
         catch(Exception e){
                logger.info(e.getClass().getSimpleName() + " : " + e.getMessage());
                Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
                responseObserver.onError(status.asRuntimeException());
                return;
            }
        }


    @Override
    public void deleteCourseData(DeleteCourseRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            validationCourseNumber(request);
            boolean isCompletedDelete = courseRepository.deleteCourseByCourseNumber(request.getCourseNumber());
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

    /**
     * validation
     */

    private void validationCourse(AddCourseRequest request) {
        if (request.getCourseNumber().equals("") || request.getProfessorLastName().equals("") || request.getCourseName().equals("")) {
            throw new IllegalArgumentException("THE COURSE INPUT IS INVALID.");
        }
    }

    private void validationCourseNumber(DeleteCourseRequest request) {
        if (request.getCourseNumber().equals("")) {
            throw new IllegalArgumentException("THE COURSE NUMBER IS INVALID.");
        }
    }
}
