package view;

import com.academic.stub.academic.*;

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
            System.out.print("강좌 번호 : " + result.getCourseNumber() + "\n" +
                    "강좌 명 : " + result.getCourseName() + "\n" +
                    "교수 성 : " + result.getProfessorLastName() + "\n");
            for (int i = 0; i < result.getAdvancedCourseNumberCount(); i++) {
                System.out.println("강좌 선이수 과목 : " + result.getAdvancedCourseNumberList().get(i));
            }
        }
    }

    public void addCourseDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.courseServerBlockingStub.addCourseData(receiveAddCourseData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println("ADD COURSE SUCCESS");
        else System.out.println("ADD COURSE FAIL");
    }

    private AddCourseRequest receiveAddCourseData(BufferedReader objReader) throws IOException {
        System.out.println("***************************************************");
        System.out.println("********************강좌 정보 입력하기****************");
        System.out.println("강좌 번호를 입력하세요.");  String courseNumber = objReader.readLine().trim();
        System.out.println("교수 성을 입력하세요."); String professorLastName = objReader.readLine().trim();
        System.out.println("강좌 이름을 입력하세요."); String courseName = objReader.readLine().trim();
        List<String> advancedCourseNumbers = new ArrayList<>();

        while (true) {
            System.out.println("선이수 강좌 번호를 입력하세요. 입력을 마치셨으면 x를 입력하세요.");
            String advancedCourseNumber = objReader.readLine().trim();
            if (advancedCourseNumber.equals("x")) { break; }
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
        System.out.println("***************************************************");
        System.out.println("********************지울 강좌 정보 입력하기****************");
        System.out.println("강좌 번호를 입력하세요.");
        String courseNumber = objReader.readLine().trim();
        DeleteCourseRequest request = DeleteCourseRequest.newBuilder()
                .setCourseNumber(courseNumber)
                .build();
        return request;
    }

    public void deleteCourseDataResponse(BufferedReader objReader) throws IOException {
        boolean isCompleted = this.courseServerBlockingStub.deleteCourseData(receiveDeleteCourseData(objReader)).getIsCompleted();
        if(isCompleted) System.out.println("DELETE COURSE SUCCESS");
        else System.out.println("DELETE COURSE FAIL");
    }

}
