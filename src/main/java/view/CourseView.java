package view;

import com.academic.stub.academic.*;
import constant.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseView {

    private CourseServiceGrpc.CourseServiceBlockingStub courseServerBlockingStub;

    public CourseView(CourseServiceGrpc.CourseServiceBlockingStub courseServerBlockingStub) {
        this.courseServerBlockingStub = courseServerBlockingStub;
    }

    public void allCoursesDataResponse(){
        showAllCourses(courseServerBlockingStub.getAllCoursesData(null).getCoursesList());
    }

    private void showAllCourses(List<AllCoursesDataResponse.Course> results) {
        for (AllCoursesDataResponse.Course result : results) {
            System.out.println();
            System.out.print(Constants.ECourseView.eCourseNumber.getContent() + result.getCourseNumber() + Constants.ECourseView.eSpacing.getContent() +
                    Constants.ECourseView.eCourseName.getContent() + result.getCourseName() + Constants.ECourseView.eSpacing.getContent() +
                    Constants.ECourseView.eProfessorLastName.getContent() + result.getProfessorLastName() + Constants.ECourseView.eSpacing.getContent());
            for (int i = Constants.ECourseView.eZero.getNumber(); i < result.getAdvancedCourseNumberCount(); i++) {
                System.out.println(Constants.ECourseView.eAdvancedName.getContent() + result.getAdvancedCourseNumberList().get(i));
            }
        }
    }

    public void addCourseDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.courseServerBlockingStub.addCourseData(receiveAddCourseData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println(Constants.ECourseView.eAddCourseSuccessMessage.getContent());
        else System.out.println(Constants.ECourseView.eAddCourseFailMessage.getContent());
    }

    private AddCourseRequest receiveAddCourseData(BufferedReader objReader) throws IOException {
        System.out.println(Constants.ECourseView.eMenuStar.getContent());
        System.out.println(Constants.ECourseView.eMenuAddCourseGuide.getContent());
        System.out.println(Constants.ECourseView.eMenuCourseNumberGuide.getContent());  String courseNumber = objReader.readLine().trim();
        System.out.println(Constants.ECourseView.eMenuProfessorLastNameGuide.getContent()); String professorLastName = objReader.readLine().trim();
        System.out.println(Constants.ECourseView.eMenuCourseNameGuide.getContent()); String courseName = objReader.readLine().trim();
        List<String> advancedCourseNumbers = new ArrayList<>();

        while (true) {
            System.out.println(Constants.ECourseView.eMenuAdvancedCourseNumberGuide.getContent());
            String advancedCourseNumber = objReader.readLine().trim();
            if (advancedCourseNumber.equals(Constants.ECourseView.eX.getContent())) { break; }
            advancedCourseNumbers.add(advancedCourseNumber);
        }
        AddCourseRequest request = AddCourseRequest.newBuilder()
                .setCourseNumber(courseNumber)
                .setProfessorLastName(professorLastName)
                .setCourseName(courseName)
                .addAllAdvancedCourseNumber(advancedCourseNumbers)
                .build();
        return request;
    }

    private DeleteCourseRequest receiveDeleteCourseData(BufferedReader objReader) throws IOException {
        System.out.println(Constants.ECourseView.eMenuStar.getContent());
        System.out.println(Constants.ECourseView.eMenuDeleteCourseGuide.getContent());
        System.out.println(Constants.ECourseView.eMenuCourseNumberGuide.getContent());
        String courseNumber = objReader.readLine().trim();
        DeleteCourseRequest request = DeleteCourseRequest.newBuilder()
                .setCourseNumber(courseNumber)
                .build();
        return request;
    }

    public void deleteCourseDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.courseServerBlockingStub.deleteCourseData(receiveDeleteCourseData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println(Constants.ECourseView.eDeleteCourseSuccessMessage.getContent());
        else System.out.println(Constants.ECourseView.eDeleteCourseFailMessage.getContent());
    }

}
