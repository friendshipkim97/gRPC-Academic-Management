package main;

import com.academic.stub.academic.*;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClient {

    private static final Logger logger = Logger.getLogger(MainClient.class.getName());
    private static StudentServiceGrpc.StudentServiceBlockingStub studentServerBlockingStub;
    private static CourseServiceGrpc.CourseServiceBlockingStub courseServerBlockingStub;

    public MainClient(Channel channel){
        studentServerBlockingStub = StudentServiceGrpc.newBlockingStub(channel);
        courseServerBlockingStub = CourseServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws IOException {
        String target = "localhost:50050";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();

        MainClient client = new MainClient(channel);
        while (true) {
            BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
            printMenu();
            String sChoice = objReader.readLine().trim();
            switch(sChoice){
                case "1":
                    List<AllStudentsDataResponse.Student> studentsResults = client.allStudentsDataResponse();
                    if (studentsResults != null) client.showAllStudents(studentsResults);
                    else logger.info("학생 정보가 없습니다.");
                    break;
                case "2":
                    List<AllCoursesDataResponse.Course> results = client.allCoursesDataResponse();
                    if (results != null) client.showAllCourses(results);
                    else logger.info("강좌 정보가 없습니다.");
                    break;
                case "3":
                    client.addStudentDataResponse();
                case "4":
                    client.deleteStudentDataResponse();
                    //
                case "5":
                    //
                case "6":
                    //
                default:
                    System.out.println("올바르지 않은 선택입니다.");
            }
            }
        }


    private static void printMenu() {
        System.out.println();
        System.out.println("***************************************************");
        System.out.println("******************** 학사관리 시스템******************");
        System.out.println("***************************************************");
        System.out.println("********************번호를 선택 하세요****************");
        System.out.println("************(1) : 모든 학생 정보 불러오기**************");
        System.out.println("************(2) : 모든 강좌 정보 불러오기**************");
        System.out.println("************(3) : 학생 등록하기***********************");
        System.out.println("************(4) : 학생 지우기************************");
        System.out.println("************(5) : 과목 등록하기***********************");
        System.out.println("************(6) : 과목 지우기************************");
        System.out.println("************(7) : 나가기****************************");
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

    public List<AllStudentsDataResponse.Student> allStudentsDataResponse(){

        AllStudentsDataResponse response;
        try {
            response = studentServerBlockingStub.getAllStudentsData(null);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }
        return response.getStudentsList();
    }

    public List<AllCoursesDataResponse.Course> allCoursesDataResponse(){

        AllCoursesDataResponse response;
        try {
            response = courseServerBlockingStub.getAllCoursesData(null);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }
        return response.getCoursesList();
    }

    public boolean addStudentDataResponse() throws IOException {
        AddStudentRequest addStudentRequest = receiveAddStudentData();
        IsCompletedResponse response;
        try {
            response = studentServerBlockingStub.addStudentData(addStudentRequest);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return false;
        }
        return response.getIsCompleted();
    }

    private AddStudentRequest receiveAddStudentData() throws IOException {
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("***************************************************");
        System.out.println("********************학생 정보 입력하기****************");
        System.out.println("학생 이름을 입력하세요.");
        String studentName = objReader.readLine().trim();
        System.out.println("학번을 입력하세요.");
        String studentNumber = objReader.readLine().trim();
        System.out.println("전공을 입력하세요.");
        String studentMajor = objReader.readLine().trim();

        AddStudentRequest request = AddStudentRequest.newBuilder()
                .setStudentName(studentName)
                .setStudentNumber(studentNumber)
                .setMajor(studentMajor)
                .build();

        return request;
    }

    public boolean deleteStudentDataResponse() throws IOException {
        DeleteStudentRequest deleteStudentRequest = receiveDeleteStudentData();
        IsCompletedResponse response;
        try {
            response = studentServerBlockingStub.deleteStudentData(deleteStudentRequest);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return false;
        }
        return response.getIsCompleted();
    }

    private DeleteStudentRequest receiveDeleteStudentData() throws IOException {
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("***************************************************");
        System.out.println("********************지울 학생 정보 입력하기****************");
        System.out.println("학생 번호를 입력하세요.");
        String studentId = objReader.readLine().trim();

        DeleteStudentRequest request = DeleteStudentRequest.newBuilder()
                .setStudentId(studentId)
                .build();

        return request;
    }
}
