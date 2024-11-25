package com.example.grpcserver

import com.example.api.student.grpc.StudentProto
import com.example.api.student.grpc.StudentServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrpcServerApplicationTests {

	@Test
	fun getStudent() {
		val channel = ManagedChannelBuilder
			.forAddress("localhost", 50000)
			.usePlaintext()  // default가 TLS 사용하는 건데, TLS 안 쓰게끔 세팅한다.
			.build()

		val stub = StudentServiceGrpcKt.StudentServiceCoroutineStub(channel)
		val request = StudentProto.StudentRequest.newBuilder()
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
