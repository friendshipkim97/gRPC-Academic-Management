package view;

import com.academic.stub.academic.ApplicationForCourseRequest;
import com.academic.stub.academic.StudentCourseServiceGrpc;
import constant.Constants.EStudentCourseView;

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
        if(isCompleted) System.out.println(EStudentCourseView.eApplicationForCourseSuccessMessage.getContent());
        else System.out.println(EStudentCourseView.eApplicationForCourseFailMessage.getContent());
    }

    private ApplicationForCourseRequest receiveApplicationForCourseRequest(BufferedReader objReader) throws IOException {
        System.out.println(EStudentCourseView.eMenuStar.getContent());
        System.out.println(EStudentCourseView.eMenuApplicationForCourseGuide.getContent());
        System.out.println(EStudentCourseView.eMenuStudentNumberGuide.getContent());  String studentNumber = objReader.readLine().trim();
        System.out.println(EStudentCourseView.eMenuCourseNumberGuide.getContent());  String courseNumber = objReader.readLine().trim();
        ApplicationForCourseRequest applicationForCourseRequest = ApplicationForCourseRequest.newBuilder()
                .setStudentNumber(studentNumber)
                .setCourseNumber(courseNumber)
                .build();
        return applicationForCourseRequest;

    }
}
