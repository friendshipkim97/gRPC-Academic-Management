package repository;

import com.academic.stub.course.AllCoursesDataResponse;
import com.academic.stub.course.CourseRepositoryGrpc;
import com.academic.stub.course.Empty;
import domain.Course;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseRepositoryImpl extends CourseRepositoryGrpc.CourseRepositoryImplBase {
    private static final Logger logger = Logger.getLogger(CourseRepositoryImpl.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public CourseRepositoryImpl(EntityManager em, EntityManagerFactory emf) {
        this.em = em;
        this.emf = emf;
    }

    @Override
        public void getAllCoursesData(Empty empty, StreamObserver<AllCoursesDataResponse> responseObserver) {
        AllCoursesDataResponse response;
        try {

            ArrayList<AllCoursesDataResponse.Course> courseResults = new ArrayList<>();
            List<Course> result = findAll();

            System.out.println("사이즈"+result.size());
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

    public List<Course> findAll(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> resultList = em.createQuery("select c from Course c", Course.class).getResultList();
        tx.commit();
        if (resultList == null) {
            throw new NoSuchElementException("NO DATA FOUND");
        }
        return resultList;
    }
}
