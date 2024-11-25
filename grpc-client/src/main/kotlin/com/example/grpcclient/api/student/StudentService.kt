package com.example.grpcclient.api.student

import com.example.api.student.grpc.StudentProto.StudentRequest
import com.example.api.student.grpc.StudentProto.StudentResponse
import com.example.api.student.grpc.StudentServiceGrpcKt
import com.example.grpcclient.api.student.dto.GetStudentResponse
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentServiceCoroutineStub: StudentServiceGrpcKt.StudentServiceCoroutineStub
) {

    suspend fun getStudent(): GetStudentResponse {
        val studentResponse = studentServiceCoroutineStub.getStudent(
            StudentRequest.newBuilder()
                .setId(1)
                .setEmail("ljs email")
                .setPassword("pwd486")
                .setName("mynameis")
                .build()
        )

        return GetStudentResponse(
            email = studentResponse.email,
            name = studentResponse.name
        )
    }


}