package com.academic.stub.student;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: student.proto")
public final class StudentServerGrpc {

  private StudentServerGrpc() {}

  public static final String SERVICE_NAME = "com.academic.stub.student.StudentServer";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.academic.stub.student.Empty,
      com.academic.stub.student.AllStudentsDataResponse> getGetAllStudentsDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllStudentsData",
      requestType = com.academic.stub.student.Empty.class,
      responseType = com.academic.stub.student.AllStudentsDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.academic.stub.student.Empty,
      com.academic.stub.student.AllStudentsDataResponse> getGetAllStudentsDataMethod() {
    io.grpc.MethodDescriptor<com.academic.stub.student.Empty, com.academic.stub.student.AllStudentsDataResponse> getGetAllStudentsDataMethod;
    if ((getGetAllStudentsDataMethod = StudentServerGrpc.getGetAllStudentsDataMethod) == null) {
      synchronized (StudentServerGrpc.class) {
        if ((getGetAllStudentsDataMethod = StudentServerGrpc.getGetAllStudentsDataMethod) == null) {
          StudentServerGrpc.getGetAllStudentsDataMethod = getGetAllStudentsDataMethod =
              io.grpc.MethodDescriptor.<com.academic.stub.student.Empty, com.academic.stub.student.AllStudentsDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllStudentsData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.academic.stub.student.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.academic.stub.student.AllStudentsDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServerMethodDescriptorSupplier("GetAllStudentsData"))
              .build();
        }
      }
    }
    return getGetAllStudentsDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StudentServerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentServerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentServerStub>() {
        @java.lang.Override
        public StudentServerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentServerStub(channel, callOptions);
        }
      };
    return StudentServerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StudentServerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentServerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentServerBlockingStub>() {
        @java.lang.Override
        public StudentServerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentServerBlockingStub(channel, callOptions);
        }
      };
    return StudentServerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StudentServerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentServerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentServerFutureStub>() {
        @java.lang.Override
        public StudentServerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentServerFutureStub(channel, callOptions);
        }
      };
    return StudentServerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class StudentServerImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllStudentsData(com.academic.stub.student.Empty request,
        io.grpc.stub.StreamObserver<com.academic.stub.student.AllStudentsDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllStudentsDataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAllStudentsDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.academic.stub.student.Empty,
                com.academic.stub.student.AllStudentsDataResponse>(
                  this, METHODID_GET_ALL_STUDENTS_DATA)))
          .build();
    }
  }

  /**
   */
  public static final class StudentServerStub extends io.grpc.stub.AbstractAsyncStub<StudentServerStub> {
    private StudentServerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentServerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentServerStub(channel, callOptions);
    }

    /**
     */
    public void getAllStudentsData(com.academic.stub.student.Empty request,
        io.grpc.stub.StreamObserver<com.academic.stub.student.AllStudentsDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllStudentsDataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StudentServerBlockingStub extends io.grpc.stub.AbstractBlockingStub<StudentServerBlockingStub> {
    private StudentServerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentServerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentServerBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.academic.stub.student.AllStudentsDataResponse getAllStudentsData(com.academic.stub.student.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllStudentsDataMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StudentServerFutureStub extends io.grpc.stub.AbstractFutureStub<StudentServerFutureStub> {
    private StudentServerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentServerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentServerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.academic.stub.student.AllStudentsDataResponse> getAllStudentsData(
        com.academic.stub.student.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllStudentsDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_STUDENTS_DATA = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StudentServerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StudentServerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_STUDENTS_DATA:
          serviceImpl.getAllStudentsData((com.academic.stub.student.Empty) request,
              (io.grpc.stub.StreamObserver<com.academic.stub.student.AllStudentsDataResponse>) responseObserver);
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

  private static abstract class StudentServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StudentServerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.academic.stub.student.StudentOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StudentServer");
    }
  }

  private static final class StudentServerFileDescriptorSupplier
      extends StudentServerBaseDescriptorSupplier {
    StudentServerFileDescriptorSupplier() {}
  }

  private static final class StudentServerMethodDescriptorSupplier
      extends StudentServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StudentServerMethodDescriptorSupplier(String methodName) {
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
      synchronized (StudentServerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StudentServerFileDescriptorSupplier())
              .addMethod(getGetAllStudentsDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
