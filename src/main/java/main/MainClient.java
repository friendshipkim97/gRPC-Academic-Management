package main;

import com.academic.stub.academic.CourseServiceGrpc;
import com.academic.stub.academic.StudentCourseServiceGrpc;
import com.academic.stub.academic.StudentServiceGrpc;
import constant.Constants.EMainClient;
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
    private StudentView studentView;
    private CourseView courseView;
    private  StudentCourseView studentCourseView;

    public static void main(String[] args) throws IOException {
        MainClient mainClient = new MainClient();
        mainClient.choiceMenu();
    }

    public MainClient(){
        ManagedChannel channel = setConnection();
        studentServerBlockingStub = StudentServiceGrpc.newBlockingStub(channel);
        courseServerBlockingStub = CourseServiceGrpc.newBlockingStub(channel);
        studentCourseServiceBlockingStub = StudentCourseServiceGrpc.newBlockingStub(channel);
        studentView = new StudentView(studentServerBlockingStub);
        courseView = new CourseView(courseServerBlockingStub);
        studentCourseView = new StudentCourseView(studentCourseServiceBlockingStub);
    }

    private ManagedChannel setConnection() {
        String target = EMainClient.ePortNumber.getContent();
        return ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
    }
    private void choiceMenu() {
        while (true) {
            try {
                BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
                printMenu();

                EMainClient sChoice = EMainClient.valueOf(objReader.readLine().trim());
                switch (sChoice) {
                    case one: studentView.allStudentsDataResponse(); break;
                    case two: courseView.allCoursesDataResponse();break;
                    case three: studentView.addStudentDataResponse(objReader);break;
                    case four: studentView.deleteStudentDataResponse(objReader);break;
                    case five: courseView.addCourseDataResponse(objReader);break;
                    case six: courseView.deleteCourseDataResponse(objReader);break;
                    case seven: studentCourseView.applicationForCourse(objReader);break;
                    case eight: return;
                    default: EMainClient.eSwitchChoiceFail.getContent();
                }

            } catch (Exception e) {
                logger.info(e.getClass().getSimpleName() + EMainClient.eColon + e.getMessage());
            }
        }
    }
    private static void printMenu() {
        System.out.println();
        System.out.println(EMainClient.eMenuStar.getContent());
        System.out.println(EMainClient.eMenuTitle.getContent());
        System.out.println(EMainClient.eMenuStar.getContent());
        System.out.println(EMainClient.eMenuGuide.getContent());
        System.out.println(EMainClient.eMenuOne.getContent());
        System.out.println(EMainClient.eMenuTwo.getContent());
        System.out.println(EMainClient.eMenuThree.getContent());
        System.out.println(EMainClient.eMenuFour.getContent());
        System.out.println(EMainClient.eMenuFive.getContent());
        System.out.println(EMainClient.eMenuSix.getContent());
        System.out.println(EMainClient.eMenuSeven.getContent());
        System.out.println(EMainClient.eMenuEight.getContent());
    }

}
