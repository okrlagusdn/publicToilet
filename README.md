# publicToilet

(공중화장실 위치 알림 서비스)



### BackEnd

##### 구현 과정

1. 데이터베이스 설계

2. Spring boot 프로젝트 생성

   Spring Starter Project 생성 -> dependency 기본 추가(Spring Web, Spring Web Services, Spring boot devTools) -> src/main/resources/application.properties 파일에 아래 내용 추가

   ```
   # web context and port settings
   server.servlet.context-path = /publictoilet
   server.port=8080
   ```

3. Mybatis 연결 설정

   - pom.xml 에 아래 dependency 추가

   ```
   <dependency>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-jdbc</artifactId>
   </dependency>
   <dependency>
   	<groupId>mysql</groupId>
   	<artifactId>mysql-connector-java</artifactId>
   	<version>8.0.18</version>
   </dependency>
   <dependency>
   	<groupId>org.mybatis.spring.boot</groupId>
   	<artifactId>mybatis-spring-boot-starter</artifactId>
   	<version>1.3.0</version>
   </dependency>
   ```

   - src/main/resources/application.properties 파일에 아래 내용 추가

   ```
   # db settings
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://127.0.0.1:3306/publictoilet?useSSL=false&useUniCode=yes&characterEncoding=UTF-8&serverTimezone=Asia/Seoul
   spring.datasource.username=root
   spring.datasource.password=1234
   
   #mapper location settings
   mybatis.config-location=classpath:config/SqlMapConfig.xml
   ```

   - src/main/resources/config 에 SqlMapConfig.xml 생성 후 아래소스 입력

   ```
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
   	"http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
   	 <settings>
   	 	<setting name="mapUnderscoreToCamelCase" value="true"/>
   	 	<setting name="jdbcTypeForNull" value="NULL"/>
   	 </settings>
   	<typeAliases>
   		<package name="com.pt.be.vo"/>
   	</typeAliases>
   	<mappers>
   		<mapper resource="sql/mybatis-empservice-mapping.xml"/>
   	</mappers>
   	<!-- JUnit MyBatis 단위테스트용 -->
   </configuration>
   ```

   

------

### FrontEnd