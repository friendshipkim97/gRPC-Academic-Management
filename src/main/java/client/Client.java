package client;

import com.academic.stub.student.AllStudentDataRequest;
import com.academic.stub.student.AllStudentDataResponse;
import com.academic.stub.student.StudentRepositoryGrpc;
import com.academic.stub.student.StudentServerGrpc;
import domain.Student;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import repository.StudentRepository;
import server.StudentServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private StudentServerGrpc.StudentServerBlockingStub studentServerBlockingStub;

    public Client(Channel channel){
        studentServerBlockingStub = StudentServerGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) {
        String target = "localhost:50050";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();

        Client client = new Client(channel);
        List<AllStudentDataResponse.Student> results = client.AllStudentDataResponse("kimjungwoo");

        for (AllStudentDataResponse.Student result : results) {
            System.out.println("학생 명 : " + result.getStudentName() + "\n" +
                    "학생 번호 : " + result.getStudentNumber() + "\n" +
                    "학생 전공 : " + result.getMajor() + "\n");

            for (int i = 0; i < result.getCoursesCount(); i++) {
                System.out.println("학생 수강 과목 : " + result.getCoursesList().get(i));
            }
        }
    }

    // This method will create a request for you and get the response back from the result service and sent it to you
    public List<AllStudentDataResponse.Student> AllStudentDataResponse(String name){
        AllStudentDataRequest request = AllStudentDataRequest.newBuilder().setName(name).build();
        AllStudentDataResponse response;
        try {
            response = studentServerBlockingStub.getAllStudentData(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return null;
        }
        return response.getStudentsList();
    }
}
