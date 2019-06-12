# Dubbo项目创建导航(Springboot)

## 一、环境准备

​	以官方建议的zookeeper作为注册中心，

​	这里zookeeper用docker部署，对外暴露端口2181，协议端口20882

## 二、创建父工程

​	父工程统一管理依赖包的版本控制，在父工程先引用springboot的依赖

​	modules用于把子工程引用进来

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zei.happy</groupId>
    <artifactId>dubbo-create</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>

    <modules>
      <!--
        <module>common</module>
        <module>person-provide</module>
        <module>person-consumer</module>
			-->
    </modules>

    <!--配置springboot-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <!--配置参数-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-boot.version>2.1.3.RELEASE</spring-boot.version>
        <dubbo.version>0.2.0</dubbo.version>
        <lombok.version>1.18.8</lombok.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.boot</groupId>
                <artifactId>dubbo-spring-boot-starter</artifactId>
                <version>${dubbo.version}</version>
            </dependency>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
                <scope>provided</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <finalName>dubbocreate</finalName>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <configuration>
                    <delimiters>
                        <delimit>$</delimit>
                    </delimiters>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## 三、创建公共模块

​		创建简单的maven项目，用于放公共的类(实体类对象、接口等)

​		*每创建一个子模块，都要在父模块的modules中加入引用*

### 3.1、pom依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>dubbo-create</artifactId>
        <groupId>com.zei.happy</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>common</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

    </dependencies>
</project>
```

### 3.2、测试用实体

```java
package com.zei.happy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Person implements Serializable{
    
    private Integer id;
    
    private String name;
    
    private Integer age;
}
```

### 3.3、用于公共调用的接口

```java
package com.zei.happy.service;

import com.zei.happy.domain.Person;

public interface PersonService {

    Person getPerson(int id);
}
```

## 四、创建服务提供者provide

### 4.1、pom依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.zei.happy</groupId>
		<artifactId>dubbo-create</artifactId>
		<version>1.0-SNAPSHOT</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<artifactId>person-provide</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>person-provide</name>

	<dependencies>
		<dependency>
			<groupId>com.zei.happy</groupId>
			<artifactId>common</artifactId>
			<version>1.0-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>com.alibaba.boot</groupId>
			<artifactId>dubbo-spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

### 4.2、配置文件application.yml

​		这里只需要简单的配置服务注册中心的地址

```yaml
server:
  port: 8888
dubbo:
  application:
    name: person-provide
  registry:
    address: zookeeper://10.211.55.7:2181
  protocol:
    port: 20882
```

### 4.3、启动类上添加注解

​		@EnableDubbo

### 4.4、接口实现类

​		注意这里的@Service注解，应为dubbo提供的Service

```java
package com.zei.happy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zei.happy.domain.Person;

@Service
public class PersonServiceImpl implements PersonService{

    @Override
    public Person getPerson(int id) {
        return new Person().setId(id).setName("张三" + id + "号").setAge(id+id+id);
    }
}
```

## 五、创建服务消费者consumer

​	加依赖、配置服务注册中心地址、启动类上加注解都和服务提供者一致

​	这里以controller的调用为示例，原先本地调用使用@Autowired注解，这里改成@Reference

​	完成的效果如同调用本地方法一样

```java
package com.zei.happy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zei.happy.domain.Person;
import com.zei.happy.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Reference
    PersonService personService;

    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable("id") int id){
        Person person = personService.getPerson(id);
        return person;
    }
}
```

## 六、Dubbo框架之我见

​	dubbo是我接触的第一个RPC框架，官网对于RPC的讲解十分简单明了，让我一个没有分布式开发经验的人也能一目了然。而且dubbo的搭建、使用也是极其的简易，不探其原理，在架构师搭好框架的情况下，开发人员甚至不需要有这方面的知识也能无缝的接入使用。当然，在使用过程中也遇到过不少坑，例如在常规开发中，很少会是简单的一provider对一consumer的情况，更多的情况下一个服务既是provider又是consumer(前后端分离场景)，而一个provider又面对的是多consumer，在配置上我们还需要将qos考虑在内。

​	在慢慢深入的学习分布式应用后，会慢慢的发现dubbo的无力，例如在需要使用服务熔断的时候，大多场景会将hystrix作为第一选择，那会对springcloud形成了依赖，可这为何不直接使用springcloud呢。

​	其实，对于dubbo的学习我倒认为不必太过深入，毕竟是个过时已久的东西，虽然简单好用，现在官方有dubbox、springcloud、thrift等更多更高效、更全面的RPC框架提供选择。
