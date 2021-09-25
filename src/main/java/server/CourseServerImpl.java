package server;

import com.academic.stub.course.AllCoursesDataResponse;
import com.academic.stub.course.CourseRepositoryGrpc;
import com.academic.stub.course.CourseServerGrpc;
import com.academic.stub.student.AllStudentsDataResponse;
import com.academic.stub.course.Empty;
import com.academic.stub.student.StudentRepositoryGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseServerImpl extends CourseServerGrpc.CourseServerImplBase {

    private CourseRepositoryGrpc.CourseRepositoryBlockingStub blockingStub;
    private static final Logger logger = Logger.getLogger(StudentServerImpl.class.getName());

    public CourseServerImpl(CourseRepositoryGrpc.CourseRepositoryBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    @Override
    public void getAllCoursesData(Empty empty, StreamObserver<AllCoursesDataResponse> responseObserver) {
        AllCoursesDataResponse response;
        try {
            response = this.blockingStub.getAllCoursesData(empty);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }
}
