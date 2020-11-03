

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
   		<mapper resource="sql/user.mapping.xml"/>
   	</mappers>
   	<!-- JUnit MyBatis 단위테스트용 -->
   </configuration>
   ```

   - src/main/resources/sql 에 user.mapping.xml 생성 후 아래소스 입력(필요한 기능마다 생성, 필요 쿼리 생성)

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
   	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   
   <mapper namespace="userMapper">
   	<select id="checkIdDuplication" parameterType="string"
   		resultType="int">
   		select count(*) from user where id =#{value}
   	</select>
   	<insert id="create" parameterType="user">
   		insert into user (name, id,
   		pw, isAdmin, phoneNumber, authCode)
   		values (#{name}, #{id}, #{pw},
   		#{isAdmin}, #{phoneNumber}, #{authCode})
   	</insert>
   </mapper>
   ```

4. vo(user.java) 생성(생성자, getters, setters)

5. dao(UserDAO.java, UserDAOImpl.java) 생성

   - UserDAOImpl.java

   ```
   package com.pt.be.dao;
   
   import org.apache.ibatis.session.SqlSession;
   import org.springframework.beans.factory.annotation.Autowired;
   
   import com.pt.be.vo.User;
   
   @Repository
   public class UserDAOImpl implements UserDAO {
   	
   	@Autowired
   	private SqlSession sqlSession;
   	private final String namespace = "userMapper.";
   
   	@Override
   	public boolean checkIdDuplication(String id) throws Exception {
   		return (Integer) sqlSession.selectOne(namespace+"checkIdDuplication", id)==1?true:false;
   	}
   
   	@Override
   	public boolean create(User user) throws Exception {
   		return sqlSession.insert(namespace+"create", user)>0?true:false;
   	}
   }
   ```

6. Service(UserService.java, UserServiceImpl.java) 생성

   - UserServiceImpl.java

   ```
   package com.pt.be.service;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   import com.pt.be.dao.UserDAO;
   import com.pt.be.vo.User;
   
   @Service
   public class UserServiceImpl implements UserService{
   	@Autowired
   	private UserDAO userDAO;
   	
   	@Override
   	public boolean checkIdDuplication(String id) throws Exception {
   		return userDAO.checkIdDuplication(id);
   	}
   
   	@Override
   	public boolean create(User user) throws Exception {
   		return userDAO.create(user);
   	}
   
   	@Override
   	public User get(String id) throws Exception {
   		return userDAO.get(id);
   	}
   }
   ```

7. swagger 추가(확인하기위해)

   - pom.xml에 swagger2 추가

   ```
   <!-- SWagger 2 추가 -->
   <dependency>
   	<groupId>io.springfox</groupId>
   	<artifactId>springfox-swagger2</artifactId>
   	<version>2.4.0</version>
   </dependency>
   <dependency>
   	<groupId>io.springfox</groupId>
   	<artifactId>springfox-swagger-ui</artifactId>
   	<version>2.4.0</version>
   </dependency>RESTFul API 서비스 설정 추가
   ```

   - SwaggerConfig 이름의 클래스를 생성하고, @Configuation 과 @EnableSwagger2 어노테이션을 지정,
   - ui를 통해 보여지게 될 기본적인 Swagger내용들을 설정
   - SwaggerConfig.java

   ```
   package com.pt.be;
   
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import springfox.documentation.builders.ApiInfoBuilder;
   import springfox.documentation.builders.PathSelectors;
   import springfox.documentation.service.ApiInfo;
   import springfox.documentation.spi.DocumentationType;
   import springfox.documentation.spring.web.plugins.Docket;
   import springfox.documentation.swagger2.annotations.EnableSwagger2;
   
   @Configuration
   @EnableSwagger2
   public class SwaggerConfig {
   
   	@Bean
   	public Docket postsApi() {
   		return new Docket(DocumentationType.SWAGGER_2)
   				.groupName("public-api")
   				.apiInfo(this.apiInfo())
   				.select()
   				.paths(PathSelectors.any())
   				.build();
   	}	
   
   	private ApiInfo apiInfo() {
   		return new ApiInfoBuilder()
   				.title("publictoilet API")
   				.description("publictoilet 백엔드 API")
   				.build();
   	}
   }
   ```

   - Controller 생성

   ```
   package com.pt.be.controller;
   
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.http.HttpStatus;
   import org.springframework.http.ResponseEntity;
   import org.springframework.web.bind.annotation.CrossOrigin;
   import org.springframework.web.bind.annotation.RequestBody;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RequestMethod;
   import org.springframework.web.bind.annotation.RestController;
   
   import com.fasterxml.jackson.databind.ObjectMapper;
   import com.pt.be.service.UserService;
   import com.pt.be.vo.User;
   
   import io.swagger.annotations.Api;
   import io.swagger.annotations.ApiOperation;
   
   @RestController
   @RequestMapping("/user")
   @CrossOrigin(origins = { "*" }, maxAge = 6000)
   @Api(tags = { "User" }, description = "User 관련 API")
   public class UserController {
   	@Autowired
   	private UserService userService;
   	
   	@ApiOperation(value = "계정명이 id인 사용자를 생성하고 해당 사용자의 인증코드를 반환한다.")
   	@RequestMapping(value = "/create", method = RequestMethod.POST)
   	public ResponseEntity<String> create(@RequestBody String id) throws Exception {
   		id = id.trim().replaceAll("\"", "");
   		User user = null;
   		ObjectMapper mapper = new ObjectMapper();
   		
   		//기존DB에 id가 존재하는 경우
   		if(userService.checkIdDuplication(id)) {
   			user = userService.get(id);
   			//인증란이 pass인 경우, 즉 회원가입이 완료된 id인 경우
   			if(user.getAuthCode().equals("pass") == true) {
   				return new ResponseEntity<String>(mapper.writeValueAsString(1), HttpStatus.OK);
   			}
   			user.setAuthCode(User.createRandomCode(12));
   			//userService.update(user);
   		}else {
   			user = new User(id);
   			userService.create(user);
   		}
   		return new ResponseEntity<String>(mapper.writeValueAsString(0), HttpStatus.OK);
   	}
   }
   ```

8. RESTFul API 서비스 설정 추가

   - pom.xml에 아래 dependency 추가

   ```
   <!-- RESTFul JSON 출력 -->
   <dependency>
   	<groupId>com.fasterxml.jackson.core</groupId>
   	<artifactId>jackson-databind</artifactId>
   </dependency>
   ```



------

### FrontEnd