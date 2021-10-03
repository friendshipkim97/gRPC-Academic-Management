package service;

import com.academic.stub.academic.*;

import entity.Course;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }
}
