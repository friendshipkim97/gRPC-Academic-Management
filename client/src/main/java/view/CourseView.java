package view;

import com.academic.stub.academic.*;
import constant.Constants.ECourseView;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseView {

    /**
     * variable
     */
    private CourseServiceGrpc.CourseServiceBlockingStub courseServerBlockingStub;

    /**
     * constructor
     */
    public CourseView(CourseServiceGrpc.CourseServiceBlockingStub courseServerBlockingStub) {
        this.courseServerBlockingStub = courseServerBlockingStub;
    }

    /**
     * method
     */
    public void allCoursesDataResponse(){
        showAllCourses(courseServerBlockingStub.getAllCoursesData(null).getCoursesList());
    }

    private void showAllCourses(List<AllCoursesDataResponse.Course> results) {
        for (AllCoursesDataResponse.Course result : results) {
            System.out.println();
            System.out.print(ECourseView.eCourseNumber.getContent()+result.getCourseNumber()+ECourseView.eSpacing.getContent() +
                    ECourseView.eCourseName.getContent()+result.getCourseName() + ECourseView.eSpacing.getContent() +
                    ECourseView.eProfessorLastName.getContent()+result.getProfessorLastName()+ECourseView.eSpacing.getContent());
            for (int i = ECourseView.eZero.getNumber(); i < result.getAdvancedCourseNumberCount(); i++) {
                System.out.println(ECourseView.eAdvancedName.getContent() + result.getAdvancedCourseNumberList().get(i));
            }
        }
    }

    public void addCourseDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.courseServerBlockingStub.addCourseData(receiveAddCourseData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println(ECourseView.eAddCourseSuccessMessage.getContent());
        else System.out.println(ECourseView.eAddCourseFailMessage.getContent());
    }

    private AddCourseRequest receiveAddCourseData(BufferedReader objReader) throws IOException {
        System.out.println(ECourseView.eMenuStar.getContent());
        System.out.println(ECourseView.eMenuAddCourseGuide.getContent());
        System.out.println(ECourseView.eMenuCourseNumberGuide.getContent());  String courseNumber = objReader.readLine().trim();
        System.out.println(ECourseView.eMenuProfessorLastNameGuide.getContent()); String professorLastName = objReader.readLine().trim();
        System.out.println(ECourseView.eMenuCourseNameGuide.getContent()); String courseName = objReader.readLine().trim();
        List<String> advancedCourseNumbers = new ArrayList<>();
        while (true) {
            System.out.println(ECourseView.eMenuAdvancedCourseNumberGuide.getContent());
            String advancedCourseNumber = objReader.readLine().trim();
            if (advancedCourseNumber.equals(ECourseView.eX.getContent())) { break; }
            advancedCourseNumbers.add(advancedCourseNumber);
        }
        validationAddCourseData(courseNumber, professorLastName, courseName, advancedCourseNumbers);
        AddCourseRequest request = AddCourseRequest.newBuilder()
                .setCourseNumber(courseNumber)
                .setProfessorLastName(professorLastName)
                .setCourseName(courseName)
                .addAllAdvancedCourseNumber(advancedCourseNumbers)
                .build();
        return request;
    }

    private DeleteCourseRequest receiveDeleteCourseData(BufferedReader objReader) throws IOException {
        System.out.println(ECourseView.eMenuStar.getContent());
        System.out.println(ECourseView.eMenuDeleteCourseGuide.getContent());
        System.out.println(ECourseView.eMenuCourseNumberGuide.getContent());
        String courseNumber = objReader.readLine().trim();
        validationCourseNumber(courseNumber);
        DeleteCourseRequest request = DeleteCourseRequest.newBuilder()
                .setCourseNumber(courseNumber)
                .build();
        return request;
    }

    public void deleteCourseDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.courseServerBlockingStub.deleteCourseData(receiveDeleteCourseData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println(ECourseView.eDeleteCourseSuccessMessage.getContent());
        else System.out.println(ECourseView.eDeleteCourseFailMessage.getContent());
    }

    /**
     * validation
     */
    private void validationAddCourseData(String courseNumber, String professorLastName, String courseName,
                                         List<String> advancedCourseNumbers) {
        validationCourseNumber(courseNumber);
        validationProfessorLastName(professorLastName);
        validationCourseName(courseName);
        validationAdvancedCourseNumbers(advancedCourseNumbers);
    }

    private void validationCourseNumber(String courseNumber) {
        if(courseNumber.matches(ECourseView.eMatchOnlyNumber.getContent()) == false){
            throw new IllegalArgumentException(ECourseView.eCourseNumberMatchMessage.getContent()); } }
    private void validationProfessorLastName(String professorLastName) {
        if(professorLastName.matches(ECourseView.eMatchContainNumber.getContent())){
            throw new IllegalArgumentException(ECourseView.eProfessorLastNameMatchMessage.getContent()); } }
    private void validationCourseName(String courseName) {
        if(courseName.matches(ECourseView.eMatchContainNumber.getContent())){
            throw new IllegalArgumentException(ECourseView.eCourseNameMatchMessage.getContent()); } }
    private void validationAdvancedCourseNumbers(List<String> advancedCourseNumbers) {
        for (String advancedCourseNumber : advancedCourseNumbers) {
            if(advancedCourseNumber.matches(ECourseView.eMatchOnlyNumber.getContent()) == false){
                throw new IllegalArgumentException(ECourseView.eAdvancedCourseNumberMatchMessage.getContent()); } } }

}
