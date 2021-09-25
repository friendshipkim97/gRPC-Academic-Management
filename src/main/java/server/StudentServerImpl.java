package server;


import com.academic.stub.student.AllStudentsDataResponse;
import com.academic.stub.student.Empty;
import com.academic.stub.student.StudentRepositoryGrpc;
import com.academic.stub.student.StudentServerGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

class StudentServerImpl extends StudentServerGrpc.StudentServerImplBase {


    private StudentRepositoryGrpc.StudentRepositoryBlockingStub blockingStub;
    private static final Logger logger = Logger.getLogger(StudentServerImpl.class.getName());

    public StudentServerImpl(StudentRepositoryGrpc.StudentRepositoryBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    @Override
    public void getAllStudentsData(Empty empty, StreamObserver<AllStudentsDataResponse> responseObserver) {
        AllStudentsDataResponse response;
        try {
            response = this.blockingStub.getAllStudentsData(empty);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }

}
