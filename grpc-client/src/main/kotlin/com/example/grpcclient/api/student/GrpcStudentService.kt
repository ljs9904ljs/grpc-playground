package com.example.grpcclient.api.student

import com.example.api.student.grpc.StudentProto.StudentRequest
import com.example.api.student.grpc.StudentProto.StudentResponse
import com.example.api.student.grpc.StudentResponseKt
import com.example.api.student.grpc.StudentServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService
import java.util.UUID
import kotlin.random.Random

@GrpcService
class GrpcStudentService: StudentServiceGrpcKt.StudentServiceCoroutineImplBase() {

    override suspend fun getStudent(request: StudentRequest): StudentResponse {
        return StudentResponse.newBuilder()
            .setId(Random.nextLong())
            .setEmail("ljs9904ljs@postech.ac.kr")
            .setPassword("12341234")
            .setName("이준석")
            .build()
    }

}