package com.example.grpcclient

import com.example.grpcclient.api.student.StudentController
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc


@SpringBootTest
class GrpcClientApplicationTests {

    @Autowired
    private lateinit var studentController: StudentController

    @Test
    fun getStudent() {

        runBlocking {
            val response = studentController.getStudent()
            println(response)
        }
    }

}
