package view;

import com.academic.stub.academic.AddStudentRequest;
import com.academic.stub.academic.AllStudentsDataResponse;
import com.academic.stub.academic.DeleteStudentRequest;
import com.academic.stub.academic.StudentServiceGrpc;
import constant.Constants;

import java.io.BufferedReader;
import java.io.IOException;
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
            System.out.print(Constants.EStudentView.eStudentName.getContent()+ result.getStudentName() + Constants.EStudentView.eSpacing.getContent() +
                    Constants.EStudentView.eStudentNumber.getContent() + result.getStudentNumber() + Constants.EStudentView.eSpacing.getContent() +
                    Constants.EStudentView.eStudentMajor.getContent() + result.getMajor() + Constants.EStudentView.eSpacing.getContent());
            for (int i = Constants.EStudentView.eZero.getNumber(); i < result.getCourseNumberCount(); i++) {
                System.out.println(Constants.EStudentView.eStudentApplicationForCourseNumber.getContent() + result.getCourseNumberList().get(i));
            }
        }
    }

    public void addStudentDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.studentServerBlockingStub.addStudentData(receiveAddStudentData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println(Constants.EStudentView.eAddStudentSuccessMessage.getContent());
        else System.out.println(Constants.EStudentView.eAddStudentFailMessage.getContent());
    }


    private AddStudentRequest receiveAddStudentData(BufferedReader objReader) throws IOException {
        System.out.println(Constants.EStudentView.eMenuStar.getContent());
        System.out.println(Constants.EStudentView.eMenuAddStudentGuide.getContent());
        System.out.println(Constants.EStudentView.eMenuStudentNameGuide.getContent());  String studentName = objReader.readLine().trim();
        System.out.println(Constants.EStudentView.eMenuStudentNumberGuide.getContent()); String studentNumber = objReader.readLine().trim();
        System.out.println(Constants.EStudentView.eMenuStudentMajorGuide.getContent()); String studentMajor = objReader.readLine().trim();
        AddStudentRequest request = AddStudentRequest.newBuilder()
                .setStudentName(studentName)
                .setStudentNumber(studentNumber)
                .setMajor(studentMajor)
                .build();
        return request;
    }

    public void deleteStudentDataResponse(BufferedReader objReader) throws IOException {
            boolean isCompleted = studentServerBlockingStub.deleteStudentData(receiveDeleteStudentData(objReader)).getIsCompleted();
            if(isCompleted) System.out.println(Constants.EStudentView.eDeleteStudentSuccessMessage.getContent());
            else System.out.println(Constants.EStudentView.eDeleteStudentFailMessage.getContent());
    }

    private DeleteStudentRequest receiveDeleteStudentData(BufferedReader objReader) throws IOException {
        System.out.println(Constants.EStudentView.eMenuStar.getContent());
        System.out.println(Constants.EStudentView.eMenuDeleteStudentGuide.getContent());
        System.out.println(Constants.EStudentView.eMenuStudentNumberGuide.getContent());
        String studentNumber = objReader.readLine().trim();
        DeleteStudentRequest request = DeleteStudentRequest.newBuilder()
                .setStudentNumber(studentNumber)
                .build();
        return request;
    }
}
