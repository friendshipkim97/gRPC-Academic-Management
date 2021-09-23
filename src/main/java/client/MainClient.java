package client;

import com.academic.stub.student.AllStudentDataRequest;
import com.academic.stub.student.AllStudentDataResponse;
import com.academic.stub.student.StudentServerGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClient {

    private static final Logger logger = Logger.getLogger(MainClient.class.getName());
    private static StudentServerGrpc.StudentServerBlockingStub blockingStub;

    public MainClient(Channel channel){
        blockingStub = StudentServerGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) {
        String target = "localhost:50050";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();

        MainClient client = new MainClient(channel);
        List<AllStudentDataResponse.Student> results = client.allStudentDataResponse("kimjungwoo");

        for (AllStudentDataResponse.Student result : results) {
            System.out.println("학생 명 : " + result.getStudentName() + "\n" +
                    "학생 번호 : " + result.getStudentNumber() + "\n" +
                    "학생 전공 : " + result.getMajor() + "\n");

            for (int i = 0; i < result.getCourseNumberCount(); i++) {
                System.out.println("학생 수강 과목 : " + result.getCourseNumberList().get(i));
            }
        }
    }

    // This method will create a request for you and get the response back from the result service and sent it to you
    public List<AllStudentDataResponse.Student> allStudentDataResponse(String name){
        AllStudentDataRequest request = AllStudentDataRequest.newBuilder().setName(name).build();
        AllStudentDataResponse response;
        try {
            response = blockingStub.getAllStudentData(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }
        return response.getStudentsList();
    }
}
