package com.academic.stub.student;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: student.proto")
public final class StudentRepositoryGrpc {

  private StudentRepositoryGrpc() {}

  public static final String SERVICE_NAME = "com.academic.stub.student.StudentRepository";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.academic.stub.student.AllStudentDataRequest,
      com.academic.stub.student.AllStudentDataResponse> getGetAllStudentDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllStudentData",
      requestType = com.academic.stub.student.AllStudentDataRequest.class,
      responseType = com.academic.stub.student.AllStudentDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.academic.stub.student.AllStudentDataRequest,
      com.academic.stub.student.AllStudentDataResponse> getGetAllStudentDataMethod() {
    io.grpc.MethodDescriptor<com.academic.stub.student.AllStudentDataRequest, com.academic.stub.student.AllStudentDataResponse> getGetAllStudentDataMethod;
    if ((getGetAllStudentDataMethod = StudentRepositoryGrpc.getGetAllStudentDataMethod) == null) {
      synchronized (StudentRepositoryGrpc.class) {
        if ((getGetAllStudentDataMethod = StudentRepositoryGrpc.getGetAllStudentDataMethod) == null) {
          StudentRepositoryGrpc.getGetAllStudentDataMethod = getGetAllStudentDataMethod =
              io.grpc.MethodDescriptor.<com.academic.stub.student.AllStudentDataRequest, com.academic.stub.student.AllStudentDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllStudentData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.academic.stub.student.AllStudentDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.academic.stub.student.AllStudentDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StudentRepositoryMethodDescriptorSupplier("GetAllStudentData"))
              .build();
        }
      }
    }
    return getGetAllStudentDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StudentRepositoryStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentRepositoryStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentRepositoryStub>() {
        @java.lang.Override
        public StudentRepositoryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentRepositoryStub(channel, callOptions);
        }
      };
    return StudentRepositoryStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StudentRepositoryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentRepositoryBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentRepositoryBlockingStub>() {
        @java.lang.Override
        public StudentRepositoryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentRepositoryBlockingStub(channel, callOptions);
        }
      };
    return StudentRepositoryBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StudentRepositoryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentRepositoryFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentRepositoryFutureStub>() {
        @java.lang.Override
        public StudentRepositoryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentRepositoryFutureStub(channel, callOptions);
        }
      };
    return StudentRepositoryFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class StudentRepositoryImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllStudentData(com.academic.stub.student.AllStudentDataRequest request,
        io.grpc.stub.StreamObserver<com.academic.stub.student.AllStudentDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllStudentDataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAllStudentDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.academic.stub.student.AllStudentDataRequest,
                com.academic.stub.student.AllStudentDataResponse>(
                  this, METHODID_GET_ALL_STUDENT_DATA)))
          .build();
    }
  }

  /**
   */
  public static final class StudentRepositoryStub extends io.grpc.stub.AbstractAsyncStub<StudentRepositoryStub> {
    private StudentRepositoryStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentRepositoryStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentRepositoryStub(channel, callOptions);
    }

    /**
     */
    public void getAllStudentData(com.academic.stub.student.AllStudentDataRequest request,
        io.grpc.stub.StreamObserver<com.academic.stub.student.AllStudentDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllStudentDataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StudentRepositoryBlockingStub extends io.grpc.stub.AbstractBlockingStub<StudentRepositoryBlockingStub> {
    private StudentRepositoryBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentRepositoryBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentRepositoryBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.academic.stub.student.AllStudentDataResponse getAllStudentData(com.academic.stub.student.AllStudentDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllStudentDataMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StudentRepositoryFutureStub extends io.grpc.stub.AbstractFutureStub<StudentRepositoryFutureStub> {
    private StudentRepositoryFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentRepositoryFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentRepositoryFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.academic.stub.student.AllStudentDataResponse> getAllStudentData(
        com.academic.stub.student.AllStudentDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllStudentDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_STUDENT_DATA = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StudentRepositoryImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StudentRepositoryImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_STUDENT_DATA:
          serviceImpl.getAllStudentData((com.academic.stub.student.AllStudentDataRequest) request,
              (io.grpc.stub.StreamObserver<com.academic.stub.student.AllStudentDataResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StudentRepositoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StudentRepositoryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.academic.stub.student.StudentOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StudentRepository");
    }
  }

  private static final class StudentRepositoryFileDescriptorSupplier
      extends StudentRepositoryBaseDescriptorSupplier {
    StudentRepositoryFileDescriptorSupplier() {}
  }

  private static final class StudentRepositoryMethodDescriptorSupplier
      extends StudentRepositoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StudentRepositoryMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StudentRepositoryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StudentRepositoryFileDescriptorSupplier())
              .addMethod(getGetAllStudentDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
