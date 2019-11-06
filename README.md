# wms-message
wms의 messaging 서비스를 위한 어플리케이션입니다.

## 개발환경
- Language : Java 8
- Build : Gradle
- Framework : Spring Boot2

## 빌드 및 실행방법
```
1. github 소스 다운로드
2. 프로젝트 실행
 2-1) IntelliJ IDE 이용
 - 프로젝트 마우스 우클릭 > Gradle > Refresh Gradle Project
 - 프로젝트 마우스 우클릭 > Run As > Spring Boot App 실행 
 2-2) terminal 이용
 - ./gradlew bootRun
3. http://localhost 호출

```

## Dependencies
Dependency| Version
------------ | ------------- 
spring-boot | 2.2.0 release
spring-boot-starter-web | 
lombok | 
spring-boot-starter-activemq | 
modelmapper | 2.3.5
slack-java-webhook | 0.0.7
commons-dbcp2 | 2.7.0
mybatis-spring-boot-starter | 1.3.1




