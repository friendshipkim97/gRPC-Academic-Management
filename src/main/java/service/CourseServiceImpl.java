package service;

import com.academic.stub.academic.*;
import constant.Constants.ECourseServiceImpl;
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
        try {
            List<Course> result = courseRepository.findAll();
            AllCoursesDataResponse response = setAllCoursesDataResponse(result);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.info(e.getClass().getSimpleName() + ECourseServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    private AllCoursesDataResponse setAllCoursesDataResponse(List<Course> result) {
        ArrayList<AllCoursesDataResponse.Course> courseResults = new ArrayList<>();
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
        return AllCoursesDataResponse.newBuilder()
                .addAllCourses(courseResults).build();
    }

    @Override
    public void addCourseData(AddCourseRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        try {
            validationCourse(request);
            List<Course> coursesResult;
            if(request.getAdvancedCourseNumberList().size() != ECourseServiceImpl.eZero.getNumber()) {
                coursesResult = courseRepository.findCoursesByCourseNumber(request.getAdvancedCourseNumberList());
                courseRepository.addCourseWithAdvancedCourse(request, coursesResult);
            } else{ courseRepository.createCourse(request); }

            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
                    .setIsCompleted(ECourseServiceImpl.eTrue.getCheck()).build();

            responseObserver.onNext(isCompleted);
            responseObserver.onCompleted();
        }
         catch(Exception e){
                logger.info(e.getClass().getSimpleName() + ECourseServiceImpl.eColon.getContent() + e.getMessage());
                Status status = Status.INTERNAL.withDescription(e.getMessage());
                responseObserver.onError(status.asRuntimeException());
                return; } }


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
            logger.info(e.getClass().getSimpleName() + ECourseServiceImpl.eColon.getContent() + e.getMessage());
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
            return;
        }
    }

    /**
     * validation
     */

    private void validationCourse(AddCourseRequest request) {
        if (request.getCourseNumber().equals(ECourseServiceImpl.eEmpty.getContent()) ||
                request.getProfessorLastName().equals(ECourseServiceImpl.eEmpty.getContent()) ||
                request.getCourseName().equals(ECourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(ECourseServiceImpl.eEmptyRequestCourseExceptionMessage.getContent());
        }
    }

    private void validationCourseNumber(DeleteCourseRequest request) {
        if (request.getCourseNumber().equals(ECourseServiceImpl.eEmpty.getContent())) {
            throw new IllegalArgumentException(ECourseServiceImpl.eEmptyRequestCourseNumberExceptionMessage.getContent());
        }
    }

}
