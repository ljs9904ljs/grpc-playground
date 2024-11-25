- 참고 자료
    1. https://curiousjinan.tistory.com/entry/springboot3-grpc
    2. https://medium.com/jongho-developer/spring-boot-grpc-and-my-experience-of-grpc-fad4af471eb5


- gRPC 연습 일지
    - 1일차
        - Spring boot + protobuf + gRPC 겨우 설정하고 junit test 이용해서 호출해보았다.
    - 2일차
        - 예정했던 대로, grpc-server 쪽에 grpc 기능을 넣고, grpc-client 쪽에서 그 기능을 호출하는 형식으로 수정했다.
        - kotlin grpc 라이브러리가 suspend function을 사용해서 어쩔 수 없이 그렇게 만들었는데 혹시나 async/sync의 성능 차이가 있는지 궁금했다.
        - 그래서 두 개의 API로 분리하고 python을 이용해서 API 호출 테스트를 진행해보았다. 
            - 맨 처음에는 gatling이라는 라이브러리를 이용해서 한 번 해보려고 했다. 차트를 예쁘게 그려줬기 때문이기도 하고 기타 기능 등이 풍성했기 때문이다. 하지만 build.gradle.kts를 설정하던 중 설정이 잘못되었는지 자꾸 없는 jar 파일 의존성을 연결하려고 해서 실패했다.
                - `snakeyaml-2.3.jar`를 연결해야 하는데 자꾸 `snakeyaml-2.3-android.jar`를 연결하더라...
        - 테스트 코드를 작성할 때 thread를 100개, 1000개, 10000개로 늘려가면서 테스트했다. 하지만 그렇게 하니까 10000개를 시도할 때 내 노트북이 shut down되어 버렸다...
            - 내 컴퓨터의 thread-max 값이 얼마인지 찾아보니까(cat /proc/sys/kernel/threads-max) 62070개라고 나왔다. 그래서 10000개를 시도했었다.
        - 그래서 asyncio를 사용하는 방법을 찾아서 그렇게 테스트했더니 10만 개를 시도해도 잘 버티더라. coroutine을 사용하는 게 확실히 가볍긴 한 것 같다.
            - asyncio에서 thread pool size를 기본적으로 얼마로 가져가는지 궁금해서 찾아봤다.
                - 아래 내용에 따르면 나는 python 3.11.x 버전을 사용했고, os.cpu_count() == 20이기 때문에 thread pool size == 24이다.
                - In Python's asyncio implementation, the default thread pool size has changed across versions. (출처: https://github.com/encode/starlette/issues/980)
                    - In Python 3.7 and earlier, it was set to os.cpu_count() * 5.
                        - `max_workers = (os.cpu_count() or 1) * 5`
                            - 출처: https://github.com/python/cpython/blob/db95802bdfac4d13db3e2a391ec7b9e2f8d92dbe/Lib/concurrent/futures/thread.py#L127
                    - In Python 3.8 and later, it's limited to a maximum of 32 threads.
                        - `max_workers = min(32, (os.cpu_count() or 1) + 4)`
                            - 출처: https://github.com/python/cpython/blob/7d9d25dbedfffce61fc76bc7ccbfa9ae901bf56f/Lib/concurrent/futures/thread.py#L136
