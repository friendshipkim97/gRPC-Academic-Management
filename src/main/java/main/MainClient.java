package main;

import com.academic.stub.academic.CourseServiceGrpc;
import com.academic.stub.academic.StudentCourseServiceGrpc;
import com.academic.stub.academic.StudentServiceGrpc;
import constant.Constants;
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
        String target = Constants.EMainClient.ePortNumber.getContent();
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

                Constants.EMainClient sChoice = Constants.EMainClient.valueOf(objReader.readLine().trim());
                switch (sChoice) {
                    case one: studentView.allStudentsDataResponse(); break;
                    case two: courseView.allCoursesDataResponse();break;
                    case three: studentView.addStudentDataResponse(objReader);break;
                    case four: studentView.deleteStudentDataResponse(objReader);break;
                    case five: courseView.addCourseDataResponse(objReader);break;
                    case six: courseView.deleteCourseDataResponse(objReader);break;
                    case seven: studentCourseView.applicationForCourse(objReader);break;
                    case eight: return;
                    default: Constants.EMainClient.eSwitchChoiceFail.getContent();
                }

            } catch (Exception e) {
                logger.info(e.getClass().getSimpleName() + Constants.EMainClient.eColon + e.getMessage());
            }
        }

    }
    private static void printMenu() {
        System.out.println();
        System.out.println(Constants.EMainClient.eMenuStar.getContent());
        System.out.println(Constants.EMainClient.eMenuTitle.getContent());
        System.out.println(Constants.EMainClient.eMenuStar.getContent());
        System.out.println(Constants.EMainClient.eMenuGuide.getContent());
        System.out.println(Constants.EMainClient.eMenuOne.getContent());
        System.out.println(Constants.EMainClient.eMenuTwo.getContent());
        System.out.println(Constants.EMainClient.eMenuThree.getContent());
        System.out.println(Constants.EMainClient.eMenuFour.getContent());
        System.out.println(Constants.EMainClient.eMenuFive.getContent());
        System.out.println(Constants.EMainClient.eMenuSix.getContent());
        System.out.println(Constants.EMainClient.eMenuSeven.getContent());
        System.out.println(Constants.EMainClient.eMenuEight.getContent());
    }

}
