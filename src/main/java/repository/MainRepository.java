package repository;

import constant.Constants;
import entity.Course;
import entity.Student;
import entity.StudentCourse;
import lombok.Getter;

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
        this.emf = Persistence.createEntityManagerFactory(Constants.EMainRepository.eAcademic.getContent());
        this.em = emf.createEntityManager();
        initDB();
    }

    /**
     * init DB
     */
    private void initDB(){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Course course = new Course();

        Course course5 = course.createCourse("12345", "Park", "Java_Programming");
        em.persist(course5);
        Course course6 = course.createCourse("23456", "Park", "C++_Programming");
        em.persist(course6);
        Course course3 = course.createCourse("17651", "Kim", "Models_of_Software_Systems", course5);
        em.persist(course3);
        Course course4 = course.createCourse("17652", "Ko", "Methods_of_Software_Development", course6);
        em.persist(course4);
        Course course7 = course.createCourse("17653", "Kim", "Managing_Software_Development");
        em.persist(course7);
        Course course1 = course.createCourse("17654", "Ahn", "Analysis_of_Software_Artifacts", course3);
        em.persist(course1);
        Course course2 = course.createCourse("17655", "Lee", "Architectures_of_Software_Systems", course5, course3);
        em.persist(course2);

        StudentCourse studentCourse = new StudentCourse();
        Student student = new Student();
        StudentCourse studentCourse1 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse2 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse3 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse4 = studentCourse.createStudentCourse(course4);
        em.persist(studentCourse1);
        em.persist(studentCourse2);
        em.persist(studentCourse3);
        em.persist(studentCourse4);
        Student student1 = student.createStudent("20100123", "Hwang Myunghan", "CS", studentCourse1, studentCourse2, studentCourse3, studentCourse4);

        StudentCourse studentCourse5 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse6 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse7 = studentCourse.createStudentCourse(course7);
        StudentCourse studentCourse8 = studentCourse.createStudentCourse(course1);
        em.persist(studentCourse5);
        em.persist(studentCourse6);
        em.persist(studentCourse7);
        em.persist(studentCourse8);
        Student student2 = student.createStudent("20090421", "Kim Jason", "EE", studentCourse5, studentCourse6, studentCourse7, studentCourse8);

        StudentCourse studentCourse9 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse10 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse11 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse12 = studentCourse.createStudentCourse(course1);
        em.persist(studentCourse9);
        em.persist(studentCourse10);
        em.persist(studentCourse11);
        em.persist(studentCourse12);
        Student student3 = student.createStudent("20110512", "Park Hongsun", "CS", studentCourse9, studentCourse10, studentCourse11, studentCourse12);

        StudentCourse studentCourse13 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse14 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse15 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse16 = studentCourse.createStudentCourse(course7);
        em.persist(studentCourse13);
        em.persist(studentCourse14);
        em.persist(studentCourse15);
        em.persist(studentCourse16);
        Student student4 = student.createStudent("20100123", "Kim Yunmi", "CS", studentCourse13, studentCourse14, studentCourse15, studentCourse16);

        StudentCourse studentCourse17 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse18 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse19 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse20 = studentCourse.createStudentCourse(course1);
        em.persist(studentCourse17);
        em.persist(studentCourse18);
        em.persist(studentCourse19);
        em.persist(studentCourse20);
        Student student5 = student.createStudent("20100125", "Kim Hokyung", "ME", studentCourse17, studentCourse18, studentCourse19, studentCourse20);

        StudentCourse studentCourse21 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse22 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse23 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse24 = studentCourse.createStudentCourse(course2);
        em.persist(studentCourse21);
        em.persist(studentCourse22);
        em.persist(studentCourse23);
        em.persist(studentCourse24);
        Student student6 = student.createStudent("20100323", "Jung Philsoo", "CS", studentCourse21, studentCourse22, studentCourse23, studentCourse24);

        StudentCourse studentCourse25 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse26 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse27 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse28 = studentCourse.createStudentCourse(course1);
        em.persist(studentCourse25);
        em.persist(studentCourse26);
        em.persist(studentCourse27);
        em.persist(studentCourse28);
        Student student7 = student.createStudent("20080678", "Ahn Jonghyuk", "EE", studentCourse25, studentCourse26, studentCourse27, studentCourse28);

        StudentCourse studentCourse29 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse30 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse31 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse32 = studentCourse.createStudentCourse(course4);
        em.persist(studentCourse29);
        em.persist(studentCourse30);
        em.persist(studentCourse31);
        em.persist(studentCourse32);
        Student student8 = student.createStudent("20110298", "Lee Mijung", "ME", studentCourse29, studentCourse30, studentCourse31, studentCourse32);

        StudentCourse studentCourse33 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse34 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse35 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse36 = studentCourse.createStudentCourse(course4);
        em.persist(studentCourse33);
        em.persist(studentCourse34);
        em.persist(studentCourse35);
        em.persist(studentCourse36);
        Student student9 = student.createStudent("20120808", "Kim Sungsuk", "EE", studentCourse33, studentCourse34, studentCourse35, studentCourse36);

        StudentCourse studentCourse37 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse38 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse39 = studentCourse.createStudentCourse(course7);
        em.persist(studentCourse37);
        em.persist(studentCourse38);
        em.persist(studentCourse39);
        Student student10 = student.createStudent("20080603", "Park Kitea", "ME", studentCourse37, studentCourse38, studentCourse39);

        StudentCourse studentCourse40 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse41 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse42 = studentCourse.createStudentCourse(course2);
        em.persist(studentCourse40);
        em.persist(studentCourse41);
        em.persist(studentCourse42);
        Student student11 = student.createStudent("20070452", "Ko Kyungmin", "CS", studentCourse40, studentCourse41, studentCourse42);

        StudentCourse studentCourse43 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse44 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse45 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse46 = studentCourse.createStudentCourse(course4);
        em.persist(studentCourse43);
        em.persist(studentCourse44);
        em.persist(studentCourse45);
        em.persist(studentCourse46);
        Student student12 = student.createStudent("20130091", "Kim Chulmin", "CS", studentCourse43, studentCourse44, studentCourse45, studentCourse46);

        StudentCourse studentCourse47 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse48 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse49 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse50 = studentCourse.createStudentCourse(course1);
        em.persist(studentCourse47);
        em.persist(studentCourse48);
        em.persist(studentCourse49);
        em.persist(studentCourse50);
        Student student13 = student.createStudent("20110876", "Park Kiyoung", "EE", studentCourse47, studentCourse48, studentCourse49, studentCourse50);

        StudentCourse studentCourse51 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse52 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse53 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse54 = studentCourse.createStudentCourse(course7);
        em.persist(studentCourse51);
        em.persist(studentCourse52);
        em.persist(studentCourse53);
        em.persist(studentCourse54);
        Student student14 = student.createStudent("20100128", "Kim Minsu", "CS", studentCourse51, studentCourse52, studentCourse53, studentCourse54);

        StudentCourse studentCourse55 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse56 = studentCourse.createStudentCourse(course4);
        StudentCourse studentCourse57 = studentCourse.createStudentCourse(course7);
        StudentCourse studentCourse58 = studentCourse.createStudentCourse(course2);
        em.persist(studentCourse55);
        em.persist(studentCourse56);
        em.persist(studentCourse57);
        em.persist(studentCourse58);
        Student student15 = student.createStudent("20100131", "Kim JungMi", "CS", studentCourse55, studentCourse56, studentCourse57, studentCourse58);

        StudentCourse studentCourse59 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse60 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse61 = studentCourse.createStudentCourse(course7);
        em.persist(studentCourse59);
        em.persist(studentCourse60);
        em.persist(studentCourse61);
        Student student16 = student.createStudent("20130094", "Jang Goyoung", "CS", studentCourse59, studentCourse60, studentCourse61);

        StudentCourse studentCourse62 = studentCourse.createStudentCourse(course5);
        StudentCourse studentCourse63 = studentCourse.createStudentCourse(course6);
        StudentCourse studentCourse64 = studentCourse.createStudentCourse(course3);
        StudentCourse studentCourse65 = studentCourse.createStudentCourse(course4);
        em.persist(studentCourse62);
        em.persist(studentCourse63);
        em.persist(studentCourse64);
        em.persist(studentCourse65);
        Student student17 = student.createStudent("20130095", "Kim Soyoung", "CS", studentCourse62, studentCourse63, studentCourse64, studentCourse65);

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
