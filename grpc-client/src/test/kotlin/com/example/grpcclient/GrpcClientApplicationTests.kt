package com.example.grpcclient

import com.example.api.student.grpc.StudentProto.StudentRequest
import com.example.api.student.grpc.StudentServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrpcClientApplicationTests {


    @Test
    fun getStudent() {
        val channel = ManagedChannelBuilder
            .forAddress("localhost", 7070)
            .usePlaintext()  // default가 TLS 사용하는 건데, TLS 안 쓰게끔 세팅한다.
            .build()

        val stub = StudentServiceGrpcKt.StudentServiceCoroutineStub(channel)
        val request = StudentRequest.newBuilder()
            .setId(123)
            .setEmail("asdf")
            .setPassword("123123123")
            .setName("asdfasdf")
            .build()

        runBlocking {
            val response = stub.getStudent(request)
            println(response.id)
            println(response.email)
            println(response.password)
            println(response.name)

        }

    }

}
