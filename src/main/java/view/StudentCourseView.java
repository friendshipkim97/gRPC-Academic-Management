package view;

import com.academic.stub.academic.ApplicationForCourseRequest;
import com.academic.stub.academic.StudentCourseServiceGrpc;

import java.io.BufferedReader;
import java.io.IOException;

public class StudentCourseView {

    private static StudentCourseServiceGrpc.StudentCourseServiceBlockingStub studentCourseServiceBlockingStub;

    public StudentCourseView(StudentCourseServiceGrpc.StudentCourseServiceBlockingStub studentCourseServiceBlockingStub) {
        this.studentCourseServiceBlockingStub = studentCourseServiceBlockingStub;
    }

    public void applicationForCourse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.studentCourseServiceBlockingStub
                .applicationForCourse(receiveApplicationForCourseRequest(objReader)).getIsCompleted();
        if(isCompleted) System.out.println("APPLICATION FOR COURSE SUCCESS");
        else System.out.println("APPLICATION FOR COURSE FAIL");
    }

    private ApplicationForCourseRequest receiveApplicationForCourseRequest(BufferedReader objReader) throws IOException {
        System.out.println("***************************************************");
        System.out.println("********************학번 입력하기****************");
        System.out.println("학번을 입력하세요.");  String studentNumber = objReader.readLine().trim();
        System.out.println("강좌번호를 입력하세요.");  String courseNumber = objReader.readLine().trim();

        ApplicationForCourseRequest applicationForCourseRequest = ApplicationForCourseRequest.newBuilder()
                .setStudentNumber(studentNumber)
                .setCourseNumber(courseNumber)
                .build();

        return applicationForCourseRequest;

    }
}
