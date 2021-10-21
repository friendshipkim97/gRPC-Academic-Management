package constant;

import lombok.Getter;

public class Constants {

    @Getter
    public enum ECourseRepository{

        eFindAllCourseQuery("select c from Course c"),
        eNoCourseDataExceptionMessage("NO COURSE DATA FOUND"),
        eFindCourseByCourseNumberQuery("select c from Course c where c.courseNumber = :courseNumber"),
        eCourseNumber("courseNumber"),
        eNoCourseDataByCourseNumberExceptionMessage("NO COURSE DATA FOUND BY COURSE_NUMBER"),
        eDeleteStudentCourseDataWarningMessage("WARNING! DELETE STUDENT'S COURSE DATA"),
        eDeleteAdvancedCourseDataWarningMessage("WARNING! DELETE COURSE'S ADVANCED_COURSE DATA"),
        eFindCourseListByCourseNumberQuery("select c from Course c where c.courseNumber = :courseNumber"),
        eManyCoursesByCourseNumberExceptionMessage("THERE ARE SEVERAL COURSES WITH THE SAME COURSE_NUMBER"),
        eOne(1),
        eZero(0);

        private String content;
        private int number;
        ECourseRepository(String content) { this.content = content; }
        ECourseRepository(int number) { this.number = number; }
    }

    @Getter
    public enum EMainRepository{

        eAcademic("academic");

        private String content;
        EMainRepository(String content) { this.content = content; }
    }

    @Getter
    public enum EStudentCourseRepository{

        eFindStudentCourseByStudentQuery("select sc from StudentCourse sc where sc.student = :student"),
        eStudent("student"),
        eFindStudentCourseByCourseQuery("select sc from StudentCourse sc where sc.course = :course"),
        eCourse("course");

        private String content;
        EStudentCourseRepository(String content) { this.content = content; }
    }

    @Getter
    public enum EStudentRepository{

        eFindAllStudentQuery("select s from Student s"),
        eNoStudentDataExceptionMessage("NO STUDENT DATA FOUND"),
        eFindStudentCourseByStudentQuery("select sc from StudentCourse sc where sc.student = :student"),
        eStudent("student"),
        eFindStudentByStudentNumberQuery("select s from Student s where s.studentNumber = :studentNumber"),
        eStudentNumber("studentNumber"),
        eNoStudentDataByStudentNumberExceptionMessage("NO STUDENT DATA FOUND BY STUDENT_NUMBER"),
        eManyStudentsDataByStudentNumberExceptionMessage("THERE ARE SEVERAL STUDENTS WITH THE SAME STUDENT_NUMBER"),
        eAlreadyStudentNumberExceptionMessage("THIS STUDENT NUMBER ALREADY EXISTS."),
        eOne(1),
        eZero(0),
        eFalse(false),
        eTrue(true);

        private String content;
        private int number;
        private Boolean check;
        EStudentRepository(String content) { this.content = content; }
        EStudentRepository(int number) { this.number = number; }
        EStudentRepository(Boolean check) { this.check = check; }
    }

    @Getter
    public enum EMainServer{

        eServerStartSuccessMessage("ACADEMIC SERVER STARTED ON PORT 50050"),
        eServerStartErrorMessage("ACADEMIC SERVER DID NOT START"),
        eServerStartShutDownMessage("ACADEMIC SERVER SHUT DOWN ON INTERRUPTED"),
        ePortNumber(50050);

        private String content;
        private int number;
        EMainServer(String content) { this.content = content; }
        EMainServer(int number) { this.number = number; }
    }

    @Getter
    public enum ECourseServiceImpl{

        eEmptyRequestCourseExceptionMessage("THE COURSE INPUT IS A EMPTY VALUE."),
        eEmptyRequestCourseNumberExceptionMessage("THE COURSE NUMBER IS A EMPTY VALUE."),
        eEmpty(""),
        eColon(" : "),
        eZero(0),
        eTrue(true);

        private String content;
        private int number;
        private Boolean check;
        ECourseServiceImpl(String content) { this.content = content; }
        ECourseServiceImpl(int number) { this.number = number; }
        ECourseServiceImpl(Boolean check) { this.check = check; }
    }

    @Getter
    public enum EStudentCourseServiceImpl{

        eEmptyRequestCourseIdExceptionMessage("THE COURSE ID'S INPUT IS A EMPTY VALUE."),
        eEmptyRequestStudentIdExceptionMessage("THE STUDENT ID'S INPUT IS A EMPTY VALUE."),
        eAlreadyTakingCourseExceptionMessage("THIS IS A COURSE YOU ARE ALREADY TAKING"),
        eTakeAdvancedCourseExceptionMessage( "YOU DIDN'T TAKE THE ADVANCED COURSE"),
        eEmpty(""),
        eColon(" : "),
        eZero(0),
        eTrue(true),
        eFalse(false);

        private String content;
        private int number;
        private Boolean check;
        EStudentCourseServiceImpl(String content) { this.content = content; }
        EStudentCourseServiceImpl(int number) { this.number = number; }
        EStudentCourseServiceImpl(Boolean check) { this.check = check; }
    }

    @Getter
    public enum EStudentServiceImpl{

        eEmptyRequestStudentExceptionMessage("THE STUDENT INPUT IS A EMPTY VALUE."),
        eEmptyRequestStudentNumberExceptionMessage("THE STUDENT NUMBER IS A EMPTY VALUE."),
        eEmpty(""),
        eColon(" : "),
        eZero(0),
        eTrue(true),
        eFalse(false);

        private String content;
        private int number;
        private Boolean check;
        EStudentServiceImpl(String content) { this.content = content; }
        EStudentServiceImpl(int number) { this.number = number; }
        EStudentServiceImpl(Boolean check) { this.check = check; }
    }

}
