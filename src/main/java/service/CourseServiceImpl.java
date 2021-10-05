package service;

import com.academic.stub.academic.*;
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

    public CourseServiceImpl() {
        courseRepository = new CourseRepository();
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

//    @Override
//    public void addCourseData(AddCourseRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
//        try {
//            validationCourse(request);
//            Course course = createCourse(request.getCourseNumber(), request.getProfessorLastName(), request.getCourseName());
//            IsCompletedResponse isCompleted = IsCompletedResponse.newBuilder()
//                    .setIsCompleted(courseRepository.save(course)).build();
//
//            responseObserver.onNext(isCompleted);
//            responseObserver.onCompleted();
//        } catch (Exception e) {
//            logger.info(e.getClass().getSimpleName() + " : "+ e.getMessage());
//            Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage());
//            responseObserver.onError(status.asRuntimeException());
//            return;
//        }
//    }

    private void validationCourse(AddCourseRequest request) {
        if (request.getCourseNumber().equals("") || request.getProfessorLastName().equals("") || request.getCourseName().equals("")) {
            throw new IllegalArgumentException("THE COURSE INPUT IS INVALID.");
        }
    }

    @Override
    public void deleteCourseData(DeleteCourseRequest request, StreamObserver<IsCompletedResponse> responseObserver) {
        super.deleteCourseData(request, responseObserver);
    }
}
