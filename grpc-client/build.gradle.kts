plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"

    // .proto 파일을 컴파일하기 위한 플러그인
    id("com.google.protobuf") version "0.9.4"
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

val protobufVersion = "4.28.3"
val grpcVersion = "1.68.1"
val grpcKotlinVersion = "1.4.1"
val coroutineVersion = "1.9.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")

    // [protobuf] protobuf kotlin
    implementation("com.google.protobuf:protobuf-kotlin:${protobufVersion}")

    // [protobuf] protobuf java
    implementation("com.google.protobuf:protobuf-java:${protobufVersion}")
    implementation("com.google.protobuf:protobuf-java-util:${protobufVersion}")

    // [gRPC]
    implementation("io.grpc:grpc-protobuf:${grpcVersion}")          // Protobuf 메시지와 gRPC의 통합을 지원
    implementation("io.grpc:grpc-netty-shaded:${grpcVersion}")      // Netty Shaded 사용(gRPC 서버와 클라이언트의 Netty 전송 계층을 제공). gRPC 통신을 위해 netty를 사용한다.
    implementation("io.grpc:grpc-stub:${grpcVersion}")              // gRPC 클라이언트 스텁을 생성
    implementation("io.grpc:grpc-kotlin-stub:${grpcKotlinVersion}") // Kotlin을 위해, gRPC 클라이언트 스텁을 생성

    // grpc-kotlin 라이브러리가 coroutine을 사용하게 한다.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutineVersion}")
    // controller에서 노출하는 API에 suspend function을 원활히 사용하기 위해 필요하다.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${coroutineVersion}")

    // [swagger]
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")


    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

protobuf {
    // Protobuf 컴파일러를 지정하여 .proto 파일을 컴파일합니다.
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    // gRPC 플러그인을 설정하여 Protobuf 파일로부터 gRPC 관련 코드를 생성합니다.
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }

        // '링크': https://github.com/grpc/grpc-kotlin/tree/master/compiler
        // '링크'를 참고해서 작성했음.
        create("grpc-kt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    // 모든 프로토콜 버퍼 작업에 대해 gRPC 플러그인을 적용합니다.
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpc-kt")
            }

            // 이거 작성하면 protoc로 kotlin_out 생성하는 것과 같은 결과물이 나온다.
            // 그래서 쓸모가 없다.
//            it.builtins {
//                create("kotlin")
//            }
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    jvmToolchain(21)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.clean {
    // protobuf에서 생성된 파일을 정리합니다.
    delete(protobuf.generatedFilesBaseDir)
}
