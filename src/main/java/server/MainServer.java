package server;

import com.academic.stub.student.StudentRepositoryGrpc;
import io.grpc.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class MainServer {

    private static final Logger logger = Logger.getLogger(MainServer.class.getName());
    private static StudentRepositoryGrpc.StudentRepositoryBlockingStub blockingStub;
    private io.grpc.Server server;

    // The good old main method is defined here :)
    public static void main(String[] args) throws IOException, InterruptedException {
        MainServer mainServer = new MainServer();
        mainServer.init();
        mainServer.start();
        mainServer.blockUntilShutdown();
    }

    private void init() {
        String target = "localhost:50051";

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
        blockingStub = StudentRepositoryGrpc.newBlockingStub(channel);
    }

    private void start() throws IOException {
        Server server = ServerBuilder.forPort(50050)
                .addService(new StudentServerImpl(blockingStub))
                .build();
        try {
            server.start();
            logger.log(Level.INFO, "RESULT SERVER STARTED ON PORT 8080");
            // This awaitTermination method will help to remain the server, otherwise the server will shutdown quickly
            server.awaitTermination();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "RESULT SERVER DID NOT START");
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "SERVER SHUT DOWN ON INTERRUPTED");
        }

    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

}
