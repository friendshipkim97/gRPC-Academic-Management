package view;

import com.academic.stub.academic.AddStudentRequest;
import com.academic.stub.academic.AllStudentsDataResponse;
import com.academic.stub.academic.DeleteStudentRequest;
import com.academic.stub.academic.StudentServiceGrpc;
import constant.Constants.EStudentView;

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
            System.out.print(EStudentView.eStudentName.getContent()+result.getStudentName()+EStudentView.eSpacing.getContent() +
                    EStudentView.eStudentNumber.getContent()+result.getStudentNumber()+EStudentView.eSpacing.getContent() +
                    EStudentView.eStudentMajor.getContent()+result.getMajor()+EStudentView.eSpacing.getContent());
            for (int i = EStudentView.eZero.getNumber(); i < result.getCourseNumberCount(); i++) {
                System.out.println(EStudentView.eStudentApplicationForCourseNumber.getContent()
                        +result.getCourseNumberList().get(i));
            }
        }
    }

    public void addStudentDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.studentServerBlockingStub.addStudentData(receiveAddStudentData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println(EStudentView.eAddStudentSuccessMessage.getContent());
        else System.out.println(EStudentView.eAddStudentFailMessage.getContent());
    }


    private AddStudentRequest receiveAddStudentData(BufferedReader objReader) throws IOException {
        System.out.println(EStudentView.eMenuStar.getContent());
        System.out.println(EStudentView.eMenuAddStudentGuide.getContent());
        System.out.println(EStudentView.eMenuStudentNameGuide.getContent());  String studentName = objReader.readLine().trim();
        System.out.println(EStudentView.eMenuStudentNumberGuide.getContent()); String studentNumber = objReader.readLine().trim();
        System.out.println(EStudentView.eMenuStudentMajorGuide.getContent()); String studentMajor = objReader.readLine().trim();
        validationAddStudentData(studentName, studentNumber, studentMajor);
        AddStudentRequest request = AddStudentRequest.newBuilder()
                .setStudentName(studentName)
                .setStudentNumber(studentNumber)
                .setMajor(studentMajor)
                .build();
        return request;
    }

    public void deleteStudentDataResponse(BufferedReader objReader) throws IOException {
            boolean isCompleted = studentServerBlockingStub.deleteStudentData(receiveDeleteStudentData(objReader))
                    .getIsCompleted();
            if(isCompleted) System.out.println(EStudentView.eDeleteStudentSuccessMessage.getContent());
            else System.out.println(EStudentView.eDeleteStudentFailMessage.getContent());
    }

    private DeleteStudentRequest receiveDeleteStudentData(BufferedReader objReader) throws IOException {
        System.out.println(EStudentView.eMenuStar.getContent());
        System.out.println(EStudentView.eMenuDeleteStudentGuide.getContent());
        System.out.println(EStudentView.eMenuStudentNumberGuide.getContent());
        String studentNumber = objReader.readLine().trim();
        validationStudentNumber(studentNumber);
        DeleteStudentRequest request = DeleteStudentRequest.newBuilder()
                .setStudentNumber(studentNumber)
                .build();
        return request;
    }

    /**
     * validation
     */
    private void validationAddStudentData(String studentName, String studentNumber, String studentMajor) {
        validationStudentName(studentName);
        validationStudentNumber(studentNumber);
        validationStudentMajor(studentMajor);
    }

    private void validationStudentName(String studentName) {
        if(studentName.matches(EStudentView.eMatchContainNumber.getContent())){
            throw new IllegalArgumentException(EStudentView.eStudentNameMatchMessage.getContent()); }
    }
    private void validationStudentNumber(String studentNumber) {
        if(studentNumber.matches(EStudentView.eMatchOnlyNumber.getContent()) == false){
            throw new IllegalArgumentException(EStudentView.eStudentNumberMatchMessage.getContent()); }
    }
    private void validationStudentMajor(String studentMajor) {
        if(studentMajor.matches(EStudentView.eMatchContainNumber.getContent())){
            throw new IllegalArgumentException(EStudentView.eMajorMatchMessage.getContent()); }
    }
}
