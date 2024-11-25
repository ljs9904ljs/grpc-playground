package com.example.grpcserver.api.student

import com.example.api.student.grpc.StudentProto
import com.example.api.student.grpc.StudentServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService
import kotlin.random.Random

@GrpcService
class StudentGrpcService: StudentServiceGrpcKt.StudentServiceCoroutineImplBase() {

    override suspend fun getStudent(request: StudentProto.StudentRequest): StudentProto.StudentResponse {
        println("fun getStudent 호출됨.")
        return StudentProto.StudentResponse.newBuilder()
            .setId(Random.nextLong())
            .setEmail("ljs9904ljs@postech.ac.kr")
            .setPassword("12341234")
            .setName("이준석")
            .build()
    }

}