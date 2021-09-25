package repository;

import com.academic.stub.student.AllStudentsDataResponse;
import com.academic.stub.student.Empty;
import com.academic.stub.student.StudentRepositoryGrpc;
import domain.Course;
import domain.Student;
import domain.StudentCourse;
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

public class StudentRepositoryImpl extends StudentRepositoryGrpc.StudentRepositoryImplBase {

    private static final Logger logger = Logger.getLogger(StudentRepositoryImpl.class.getName());
    private EntityManager em;
    private EntityManagerFactory emf;

    public StudentRepositoryImpl(EntityManager em, EntityManagerFactory emf) {
        this.em = em;
        this.emf = emf;
    }

    @Override
    public void getAllStudentsData(Empty empty, StreamObserver<AllStudentsDataResponse> responseObserver) {
        AllStudentsDataResponse response;
        try {

            ArrayList<AllStudentsDataResponse.Student> studentResults = new ArrayList<>();
            List<Student> result = findAll();

            System.out.println("사이즈"+result.size());
            for (Student student : result) {
                ArrayList<String> courseNumbers = new ArrayList<>();
                List<StudentCourse> studentCourses = student.getStudentCourses();
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

            response = AllStudentsDataResponse.newBuilder()
                    .addAllStudents(studentResults).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }

    public List<Student> findAll(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Student> resultList = em.createQuery("select s from Student s", Student.class).getResultList();
        tx.commit();
        if (resultList == null) {
            throw new NoSuchElementException("NO DATA FOUND");
        }
        return resultList;
    }
}
