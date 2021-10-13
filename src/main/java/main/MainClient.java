package main;

import com.academic.stub.academic.CourseServiceGrpc;
import com.academic.stub.academic.StudentCourseServiceGrpc;
import com.academic.stub.academic.StudentServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import view.CourseView;
import view.StudentCourseView;
import view.StudentView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class MainClient {

    private static final Logger logger = Logger.getLogger(MainClient.class.getName());
    private static StudentServiceGrpc.StudentServiceBlockingStub studentServerBlockingStub;
    private static CourseServiceGrpc.CourseServiceBlockingStub courseServerBlockingStub;
    private static StudentCourseServiceGrpc.StudentCourseServiceBlockingStub studentCourseServiceBlockingStub;

    public MainClient(Channel channel){
        studentServerBlockingStub = StudentServiceGrpc.newBlockingStub(channel);
        courseServerBlockingStub = CourseServiceGrpc.newBlockingStub(channel);
        studentCourseServiceBlockingStub = StudentCourseServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws IOException {
        String target = "localhost:50050";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();

        new MainClient(channel);
        StudentView studentView = new StudentView(studentServerBlockingStub);
        CourseView courseView = new CourseView(courseServerBlockingStub);
        StudentCourseView studentCourseView = new StudentCourseView(studentCourseServiceBlockingStub);

        while (true) {
            try {
                BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
                printMenu();
                String sChoice = objReader.readLine().trim();
                switch (sChoice) {
                    case "1":
                        studentView.allStudentsDataResponse();
                        break;
                    case "2":
                        courseView.allCoursesDataResponse();
                        break;
                    case "3":
                        studentView.addStudentDataResponse(objReader);
                        break;
                    case "4":
                        studentView.deleteStudentDataResponse(objReader);
                        break;
                    case "5":
                        courseView.addCourseDataResponse(objReader);
                        break;
                    case "6":
                        courseView.deleteCourseDataResponse(objReader);
                        break;
                    case "7":
                        studentCourseView.applicationForCourse(objReader);
                        break;
                    case "8":
                        return;
                    default:
                        System.out.println("올바르지 않은 선택입니다.");
                }

            } catch (Exception e) {
                logger.info(e.getClass().getSimpleName() + " : " + e.getMessage());
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
        System.out.println("************(7) : 수강 신청************************");
        System.out.println("************(8) : 나가기****************************");
    }

}
