package view;

import com.academic.stub.academic.AddStudentRequest;
import com.academic.stub.academic.AllStudentsDataResponse;
import com.academic.stub.academic.DeleteStudentRequest;
import com.academic.stub.academic.StudentServiceGrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentView {

    private StudentServiceGrpc.StudentServiceBlockingStub studentServerBlockingStub;

    public StudentView(StudentServiceGrpc.StudentServiceBlockingStub studentServerBlockingStub) {
        this.studentServerBlockingStub = studentServerBlockingStub;
    }

    public void allStudentsDataResponse(){
        showAllStudents(this.studentServerBlockingStub.getAllStudentsData(null).getStudentsList());
    }

    private void showAllStudents(List<AllStudentsDataResponse.Student> results) {
        for (AllStudentsDataResponse.Student result : results) {
            System.out.println();
            System.out.print("학생 명 : " + result.getStudentName() + "\n" +
                    "학생 번호 : " + result.getStudentNumber() + "\n" +
                    "학생 전공 : " + result.getMajor() + "\n");
            for (int i = 0; i < result.getCourseNumberCount(); i++) {
                System.out.println("학생 수강 과목 : " + result.getCourseNumberList().get(i));
            }
        }
    }

    public void addStudentDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.studentServerBlockingStub.addStudentData(receiveAddStudentData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println("ADD STUDENT SUCCESS");
        else System.out.println("ADD STUDENT FAIL");
    }


    private AddStudentRequest receiveAddStudentData(BufferedReader objReader) throws IOException {
        System.out.println("***************************************************");
        System.out.println("********************학생 정보 입력하기****************");
        System.out.println("학생 이름을 입력하세요.");  String studentName = objReader.readLine().trim();
        System.out.println("학번을 입력하세요."); String studentNumber = objReader.readLine().trim();
        System.out.println("전공을 입력하세요."); String studentMajor = objReader.readLine().trim();

        AddStudentRequest request = AddStudentRequest.newBuilder()
                .setStudentName(studentName)
                .setStudentNumber(studentNumber)
                .setMajor(studentMajor)
                .build();
        return request;
    }

    public void deleteStudentDataResponse(BufferedReader objReader) throws IOException {
            boolean isCompleted = studentServerBlockingStub.deleteStudentData(receiveDeleteStudentData(objReader)).getIsCompleted();
            if(isCompleted) System.out.println("DELETE STUDENT SUCCESS");
            else System.out.println("DELETE STUDENT FAIL");
    }

    private DeleteStudentRequest receiveDeleteStudentData(BufferedReader objReader) throws IOException {
        System.out.println("***************************************************");
        System.out.println("********************지울 학생 정보 입력하기****************");
        System.out.println("학번을 입력하세요.");
        String studentNumber = objReader.readLine().trim();

        DeleteStudentRequest request = DeleteStudentRequest.newBuilder()
                .setStudentNumber(studentNumber)
                .build();

        return request;
    }
}
