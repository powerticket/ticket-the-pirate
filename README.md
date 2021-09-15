# ticket-the-pirate

## Table of Contents

- [설치 및 환경설정 가이드](#설치-및-환경설정-가이드)
  * [Spring initializr](#spring-initializr)
  * [Settings](#settings)
- [테이블 생성 SQL](#테이블-생성-sql)
  * [ERD](#erd)
  * [Initializing SQL](#initializing-sql)
- [API 사용 가이드](#api-사용-가이드)
  * [Quickstart](#quickstart)
  * [API](#api)
    + [Scheme](#scheme)
  * [Check with Postman](#check-with-postman)
    + [POST /product](#post--product)
    + [GET /product](#get--product)
    + [GET /product/{id}](#get--product--id-)
    + [GET /product/delivery/{id}](#get--product-delivery--id-)
    + [DELETE /product/{id}](#delete--product--id-)

## 설치 및 환경설정 가이드

### [Spring initializr](https://start.spring.io/)

![image-20210913172638851](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210913172638851.png)

### Settings

`build.gradle`

```gradle
plugins {
	id 'org.springframework.boot' version '2.5.4'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'com.tpirates'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa:2.5.4'
	implementation 'org.springframework.boot:spring-boot-starter-web:2.5.4'
	compileOnly 'org.projectlombok:lombok:1.18.20'
	runtimeOnly 'com.h2database:h2:1.4.200'
	annotationProcessor 'org.projectlombok:lombok:1.18.20'
	testImplementation 'org.springframework.boot:spring-boot-starter-test:2.5.4'
	implementation 'org.mapstruct:mapstruct:1.4.2.Final'
	annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'
}

test {
	useJUnitPlatform()
}
```

`application.yml`(dev)

```yaml
spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:testdb
    initialization-mode: always
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: validate
```

`application.yml`(prod)

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: update
```



## 테이블 생성 SQL

### ERD

![image-20210915184738189](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210915184738189.png)

### Initializing SQL

`schema.sql`

```sql
CREATE TABLE product (id bigint generated BY DEFAULT AS IDENTITY, created_at TIMESTAMP, description VARCHAR(255), name VARCHAR(255), updated_at TIMESTAMP, delivery_id bigint, PRIMARY KEY (id));
CREATE TABLE product_options (product_id bigint NOT NULL, options_id bigint NOT NULL);
CREATE TABLE product_delivery (id bigint generated BY DEFAULT AS IDENTITY, closing varchar(255), type VARCHAR(255), PRIMARY KEY (id));
CREATE TABLE product_option (id bigint generated BY DEFAULT AS IDENTITY, name VARCHAR(255), price bigint, stock bigint, PRIMARY KEY (id));

ALTER TABLE product_options ADD CONSTRAINT UK_9358l2q11cd47a05fj7yxw4ex UNIQUE (options_id);
ALTER TABLE product ADD CONSTRAINT FKgrh0dciajlqutjtdb40tjg73p FOREIGN KEY (delivery_id) REFERENCES product_delivery;
ALTER TABLE product_options ADD CONSTRAINT FK8p4cqivp1mx4r0gh75epqb9ql FOREIGN KEY (options_id) REFERENCES product_option;
ALTER TABLE product_options ADD CONSTRAINT FKh8jbibj0kf2549ogbap8x8612 FOREIGN KEY (product_id) REFERENCES product;
```

`data.sql`

```sql
INSERT INTO product_delivery (closing, type) VALUES ('fast', '12:00');
INSERT INTO product (delivery_id, description, name, created_at, updated_at) VALUES (1, '노르웨이산 연어 300g, 500g, 반마리 필렛', '노르웨이산 연어', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO product_option (name, price, stock) VALUES ('생연어 몸통살 300g', 10000, 99);
INSERT INTO product_option (name, price, stock) VALUES ('생연어 몸통살 500g', 17000, 99);
INSERT INTO product_options (product_id, options_id) VALUES (1, 1);
INSERT INTO product_options (product_id, options_id) VALUES (1, 2);

INSERT INTO product_delivery (closing, type) VALUES ('regular', '18:00');
INSERT INTO product (delivery_id, description, name, created_at, updated_at) VALUES (1, '산지직송 완도 전복 1kg (7미~60미)', '완도전복', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO product_option (name, price, stock) VALUES ('대 7~8미', 50000, 99);
INSERT INTO product_option (name, price, stock) VALUES ('중 14~15미', 34000, 99);
INSERT INTO product_option (name, price, stock) VALUES ('소 50~60미', 20000, 99);
INSERT INTO product_options (product_id, options_id) VALUES (2, 3);
INSERT INTO product_options (product_id, options_id) VALUES (2, 4);
INSERT INTO product_options (product_id, options_id) VALUES (2, 5);
```

## API 사용 가이드

### Quickstart

```bash
$ git clone https://github.com/powerticket/ticket-the-pirate.git
$ cd ticket-the-pirate
$ ./gradlew clean build
$ java -jar build/libs/*-SNAPSHOT.jar
```

or using docker

```bash
$ docker run -dp 8080:8080 powerticket/ticket-the-pirate
```

### API

#### Scheme

base url: http://localhost:8080

| Http Method | url                    | description      |
| ----------- | ---------------------- | ---------------- |
| POST        | /product               | 점포 추가        |
| GET         | /product               | 상품 목록 조회   |
| GET         | /product/{id}          | 상품 상세조회    |
| GET         | /product/delivery/{id} | 수령일 선택 목록 |
| DELETE      | /product/{id}          | 점포 삭제        |

### Check with Postman

#### POST /product

![image-20210915170813742](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210915170813742.png)

![image-20210915170820830](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210915170820830.png)

#### GET /product

![image-20210915170844411](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210915170844411.png)

#### GET /product/{id}

![image-20210915170859023](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210915170859023.png)

#### GET /product/delivery/{id}

![image-20210915170916972](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210915170916972.png)

#### DELETE /product/{id}

![image-20210915170932319](https://raw.githubusercontent.com/powerticket/typora-image-repo/image/img/image-20210915170932319.png)
