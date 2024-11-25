package com.example.grpcclient.api.student

import com.example.grpcclient.api.student.dto.GetStudentResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Student API", description = "REST API for gRPC testing")
class StudentController(
    private val studentService: StudentService
) {

    @GetMapping("/async/student")
    @Operation(summary = "Get a student information using gRPC", description = "Returns a student")
    suspend fun getStudent(): GetStudentResponse {
        return studentService.getStudent()
    }

    @GetMapping("/sync/student")
    @Operation(summary = "Get a student information using gRPC", description = "Returns a student")
    fun getStudentSync(): GetStudentResponse {
        return runBlocking {
            studentService.getStudent()
        }
    }

}