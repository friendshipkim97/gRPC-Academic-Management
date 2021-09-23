package repository;

import com.academic.stub.student.AllStudentDataRequest;
import com.academic.stub.student.AllStudentDataResponse;
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
    public void getAllStudentData(AllStudentDataRequest req, StreamObserver<AllStudentDataResponse> responseObserver) {
        AllStudentDataResponse response;
        try {

            initStudents();
            ArrayList<AllStudentDataResponse.Student> studentResults = new ArrayList<>();
            List<Student> result = findAll();

            System.out.println("사이즈"+result.size());
            for (Student student : result) {
                ArrayList<String> courseNumbers = new ArrayList<>();
                List<StudentCourse> studentCourses = student.getStudentCourses();
                for (StudentCourse studentCourse : studentCourses) {
                    courseNumbers.add(studentCourse.getCourse().getCourseNumber());
                }

                AllStudentDataResponse.Student studentResult = AllStudentDataResponse
                        .Student
                        .newBuilder()
                        .setStudentName(student.getStudentName())
                        .setStudentNumber(student.getStudentNumber())
                        .setMajor(student.getMajor()).build();
                        //.addAllCourseNumber(courseNumbers).build();

                studentResults.add(studentResult);
            }

            response = AllStudentDataResponse.newBuilder()
                    .addAllStudents(studentResults).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }

    private void initStudents() {
        EntityTransaction tx = em.getTransaction();
        Student student = new Student();
        student.setStudentNumber("11111");
        student.setStudentName("kimjungwoo");
        student.setMajor("cs");
        Course course = new Course();
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        student.addStudentCourse(studentCourse);

        tx.begin();
        em.persist(course);
        em.persist(student);
        tx.commit();
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
