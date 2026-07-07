package com.grpc.server;

import io.grpc.Channel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.grpc.test.autoconfigure.AutoConfigureTestGrpcTransport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.grpc.client.GrpcChannelFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestGrpcTransport
public class EmployeeGrpcServiceTest {

    @Autowired
    private GrpcChannelFactory channelFactory;

    private EmployeeServiceGrpc.EmployeeServiceBlockingStub blockingStub;

    @BeforeEach
    void setUp() {
        Channel channel = channelFactory.createChannel("test");
        blockingStub = EmployeeServiceGrpc.newBlockingStub(channel);
    }


    @Test
    void shouldCreateEmployee() {
        CreateEmployeeRequest request =
                CreateEmployeeRequest.newBuilder()
                        .setName("John")
                        .setDepartment("IT")
                        .build();

        CreateEmployeeResponse response = blockingStub.createEmployee(request);

        assertNotNull(response);

        assertNotNull(response.getId());
        assertEquals("John", response.getName());
        assertEquals("IT", response.getDepartment());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        CreateEmployeeRequest request = CreateEmployeeRequest.newBuilder()
                .setDepartment("IT")
                .build();

        StatusRuntimeException exception = assertThrows(
                StatusRuntimeException.class,
                () -> blockingStub.createEmployee(request));

        assertEquals(Status.Code.INVALID_ARGUMENT, exception.getStatus().getCode());
        assertEquals("Name is required", exception.getStatus().getDescription());
    }

    @Test
    void shouldThrowExceptionWhenDepartmentIsEmpty() {
        CreateEmployeeRequest request = CreateEmployeeRequest.newBuilder()
                .setName("John")
                .build();

        StatusRuntimeException exception = assertThrows(
                StatusRuntimeException.class,
                () -> blockingStub.createEmployee(request));

        assertEquals(Status.Code.INVALID_ARGUMENT, exception.getStatus().getCode());
        assertEquals("Department is required", exception.getStatus().getDescription());
    }
}
