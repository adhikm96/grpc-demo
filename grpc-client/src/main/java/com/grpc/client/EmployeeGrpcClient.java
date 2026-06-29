package com.grpc.client;

import org.springframework.grpc.client.ImportGrpcClients;
import org.springframework.stereotype.Service;

@Service
@ImportGrpcClients
public class EmployeeGrpcClient {

    private final EmployeeServiceGrpc.EmployeeServiceBlockingStub blockingStub;

    public EmployeeGrpcClient(EmployeeServiceGrpc.EmployeeServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public CreateEmployeeResponse createEmployee(String name, String department) {

        CreateEmployeeRequest request = CreateEmployeeRequest.newBuilder()
                .setName(name)
                .setDepartment(department)
                .build();

        return blockingStub.createEmployee(request);
    }
}
