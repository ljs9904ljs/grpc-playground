package com.example.grpcclient.config

import com.example.api.student.grpc.StudentServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcStubConfig {

    @Bean
    fun studentServiceCoroutineStub(): StudentServiceGrpcKt.StudentServiceCoroutineStub {
        return StudentServiceGrpcKt.StudentServiceCoroutineStub(
            channel = ManagedChannelBuilder
                .forAddress("localhost", 50000)
                .usePlaintext()  // default가 TLS 사용하는 건데, TLS 안 쓰게끔 세팅한다.
                .build()
        )
    }

}