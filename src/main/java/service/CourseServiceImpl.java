package service;

import com.academic.stub.academic.*;
import constant.Constants;
import entity.Course;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import repository.CourseRepository;

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
                List<Course> advancedCourses = course.getAdvancedCourseList();
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
            logger.info(e.getClass().getSimpleName() + Constants.ECourseServiceImpl.eColon.getContent() + e.getMessage());
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

            if(request.getAdvancedCourseNumberList().size() != Constants.ECourseServiceImpl.eZero.getNumber()) {
                coursesResult = courseRepository.findCoursesByCourseNumber(request.getAdvancedCourseNumberList());
                courseRepository.addCourseWithAdvancedCourse(request, coursesResult);
            } else{
                courseRepository.createCourse(request);
            }

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(Constants.ECourseServiceImpl.eTrue.getCheck()).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        }
         catch(Exception e){
                logger.info(e.getClass().getSimpleName() + Constants.ECourseServiceImpl.eColon.getContent() + e.getMessage());
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
            logger.info(e.getClass().getSimpleName() + Constants.ECourseServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    /**
     * validation
     */

    private void validationCourse(AddCourseRequest request) {
        if (request.getCourseNumber().equals(Constants.ECourseServiceImpl.eEmpty.getContent()) ||
                request.getProfessorLastName().equals(Constants.ECourseServiceImpl.eEmpty.getContent()) ||
                request.getCourseName().equals(Constants.ECourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(Constants.ECourseServiceImpl.eEmptyRequestCourseExceptionMessage.getContent());
        }
    }

    private void validationCourseNumber(DeleteCourseRequest request) {
        if (request.getCourseNumber().equals(Constants.ECourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(Constants.ECourseServiceImpl.eEmptyRequestCourseNumberExceptionMessage.getContent());
        }
    }

}
