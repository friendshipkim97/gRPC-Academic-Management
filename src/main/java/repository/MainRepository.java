package repository;

import entity.Course;
import entity.Student;
import entity.StudentCourse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.logging.Logger;

@Getter
public class MainRepository {

    private static final Logger logger = Logger.getLogger(MainRepository.class.getName());
    public static EntityManagerFactory emf;
    public static EntityManager em;

    public MainRepository() {
        this.emf = Persistence.createEntityManagerFactory("academic");
        this.em = emf.createEntityManager();
        initDB();
    }

    /**
     * init DB
     */
    private void initDB() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Course course = new Course();
        Course course5 = course.createCourse("12345", "Park", "Java_Programming");
        em.persist(course5);
        Course course3 = course.createCourse("17651", "Kim", "Models_of_Software_Systems", course5);
        em.persist(course3);
        Course course6 = course.createCourse("23456", "Park", "C++_Programming");
        em.persist(course6);
        Course course4 = course.createCourse("17652", "Ko", "Methods_of_Software_Development", course6);
        em.persist(course4);
        Course course7 = course.createCourse("17653", "Kim", "Managing_Software_Development");
        em.persist(course7);
        Course course1 = course.createCourse("17654", "Ahn", "Analysis_of_Software_Artifacts", course3);
        em.persist(course1);
        Course course2 = course.createCourse("17655", "Lee", "Architectures_of_Software_Systems", course5, course3);
        em.persist(course2);

        StudentCourse studentCourse = new StudentCourse();
        StudentCourse studentCourse1 = studentCourse.createStudentCourse(course1);
        em.persist(studentCourse1);
        StudentCourse studentCourse2 = studentCourse.createStudentCourse(course2);
        em.persist(studentCourse2);
        StudentCourse studentCourse3 = studentCourse.createStudentCourse(course3);
        em.persist(studentCourse3);
        StudentCourse studentCourse4 = studentCourse.createStudentCourse(course4);
        em.persist(studentCourse4);
        StudentCourse studentCourse5 = studentCourse.createStudentCourse(course5);
        em.persist(studentCourse5);
        StudentCourse studentCourse6 = studentCourse.createStudentCourse(course6);
        em.persist(studentCourse6);
        StudentCourse studentCourse7 = studentCourse.createStudentCourse(course7);
        em.persist(studentCourse7);

        Student student = new Student();

        Student student1 = student.createStudent("20100123", "Hwang Myunghan", "CS",
                studentCourse5, studentCourse6, studentCourse3, studentCourse4);
        Student student2 = student.createStudent("20090421", "Kim Jason", "EE",
                studentCourse3, studentCourse4, studentCourse7, studentCourse1);
        Student student3 = student.createStudent("20110512", "Park Hongsun", "CS",
                studentCourse5, studentCourse6, studentCourse4, studentCourse1);
        Student student4 = student.createStudent("20100123", "Kim Yunmi", "CS",
                studentCourse5, studentCourse3, studentCourse4, studentCourse7);
        Student student5 = student.createStudent("20100125", "Kim Hokyung", "ME",
                studentCourse5, studentCourse6, studentCourse3, studentCourse1);
        Student student6 = student.createStudent("20100323", "Jung Philsoo", "CS",
                studentCourse5, studentCourse6, studentCourse3, studentCourse2);
        Student student7 = student.createStudent("20080678", "Ahn Jonghyuk", "EE",
                studentCourse5, studentCourse3, studentCourse4, studentCourse1);
        Student student8 = student.createStudent("20110298", "Lee Mijung", "ME",
                studentCourse5, studentCourse6, studentCourse3, studentCourse4);
        Student student9 = student.createStudent("20120808", "Kim Sungsuk", "EE",
                studentCourse5, studentCourse6, studentCourse3, studentCourse4);
        Student student10 = student.createStudent("20080603", "Park Kitea", "ME",
                studentCourse3, studentCourse4, studentCourse7);
        Student student11 = student.createStudent("20070452", "Ko Kyungmin", "CS",
                studentCourse5, studentCourse4, studentCourse2);
        Student student12 = student.createStudent("20130091", "Kim Chulmin", "CS",
                studentCourse5, studentCourse6, studentCourse3, studentCourse4);
        Student student13 = student.createStudent("20110876", "Park Kiyoung", "EE",
                studentCourse5, studentCourse3, studentCourse4, studentCourse1);
        Student student14 = student.createStudent("20100128", "Kim Minsu", "CS",
                studentCourse5, studentCourse3, studentCourse4, studentCourse7);
        Student student15 = student.createStudent("20100131", "Kim JungMi", "CS",
                studentCourse3, studentCourse4, studentCourse7, studentCourse2);
        Student student16 = student.createStudent("20130094", "Jang Goyoung", "CS",
                studentCourse5, studentCourse6, studentCourse7);
        Student student17 = student.createStudent("20130095", "Kim Soyoung", "CS",
                studentCourse5, studentCourse6, studentCourse3, studentCourse4);
        em.persist(student1);
        em.persist(student2);
        em.persist(student3);
        em.persist(student4);
        em.persist(student5);
        em.persist(student6);
        em.persist(student7);
        em.persist(student8);
        em.persist(student9);
        em.persist(student10);
        em.persist(student11);
        em.persist(student12);
        em.persist(student13);
        em.persist(student14);
        em.persist(student15);
        em.persist(student16);
        em.persist(student17);

        tx.commit();
    }
}
