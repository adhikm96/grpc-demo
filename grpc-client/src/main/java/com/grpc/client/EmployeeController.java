package com.grpc.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeGrpcClient grpcClient;

    public EmployeeController(EmployeeGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    @PostMapping("/employee")
    public CreateEmployeeResBody createEmployee(@RequestBody CreateEmployeeBody body) {
        CreateEmployeeResponse grpcResponse = grpcClient.createEmployee(body.getName(), body.getDepartment());
        return new CreateEmployeeResBody(grpcResponse.getId(), grpcResponse.getName(), grpcResponse.getDepartment());
    }
}
