package com.grpc.server;

import org.springframework.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;

import java.security.SecureRandom;


@GrpcService
public class EmployeeServiceImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase{

    @Override
    public void createEmployee(CreateEmployeeRequest request,
                               StreamObserver<CreateEmployeeResponse> responseObserver) {


        if (request.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }


        if (request.getDepartment().isBlank()) {
            throw new IllegalArgumentException("Department is required");
        }

        SecureRandom generateId = new SecureRandom();
        CreateEmployeeResponse response =
                CreateEmployeeResponse.newBuilder()
                        .setId(generateId.nextInt(100))
                        .setName(request.getName())
                        .setDepartment(request.getDepartment())
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
