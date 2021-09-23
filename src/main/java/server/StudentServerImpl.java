package server;


import com.academic.stub.student.AllStudentDataRequest;
import com.academic.stub.student.AllStudentDataResponse;
import com.academic.stub.student.StudentRepositoryGrpc;
import com.academic.stub.student.StudentServerGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

class StudentServerImpl extends StudentServerGrpc.StudentServerImplBase {


    private static StudentRepositoryGrpc.StudentRepositoryBlockingStub blockingStub;
    private static final Logger logger = Logger.getLogger(StudentServerImpl.class.getName());

    @Override
    public void getAllStudentData(AllStudentDataRequest req, StreamObserver<AllStudentDataResponse> responseObserver) {
        AllStudentDataResponse response;
        try {
            response = blockingStub.getAllStudentData(req);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }

}
