# Chap04

책의 Day 2 CLASS 05 ~ Day 2 CLASS 07 파트를 다룬다.

### 어노테이션 기반 AOP
AOP를 어노테이션으로 설정하려면 스프링 설정 파일에 \<aop:aspectj-autoproxy> 엘리먼트를 선언해야 한다. 선언하기만 하면 컨테이너가 AOP 관련 어노테이션들을 알아서 인식하고 처리한다.  
AOP 관련 어노테이션들은 어드바이스 클래스에 설정해야 하고, 이를 스프링 컨테이너가 처리하게 하려면 스프링 설정 파일에 \<bean>으로 등록되거나 `@Service` 어노테이션을 사용하여 컴포넌트가 검색되어야 한다.  
어노테이션 설정으로 포인트컷을 선언할 때는 `@Pointcut`을 사용하며, 여러 포인트컷을 식별하기 위하여 **참조 메소드**를 이용한다.  
어드바이스 클래스에는 어드바이스 메소드가 있고, 이 메소드가 언제 동작할지 결정하는 어노테이션에는 `@Before`, `@AfterReturning` 등이 있다. 어드바이스 어노테이션에 포인트컷 참조 메소드를 지정하면 된다.  
AOP 설정에서 가장 중요한 애스팩트는 `@Aspect`를 이용하여 설정한다. 어드바이스 클래스 위에 애스팩트 어노테이션을 설정하면 스프링 컨테이너는 어드바이스 객체를 애스팩트 객체로 인식한다. 이 객체의 포인트컷 어노테이션과 어드바이스 어노테이션에 의해 위빙이 처리된다.  
어노테이션 기반에서는 어드바이스 클래스마다 포인트컷 설정을 해야하기 때문에, 같은 포인트컷이 반복되는 문제가 생긴다. 그래서 독립된 클래스에 포인트컷을 따로 설정할 수 있게 한다. 포인트컷이 있는 클래스 이름과 참조 메소드 이름을 조합하여 지정할 수 있다.  

### 스프링 JDBC
JDBC를 이용하면 데이터베이스에 비종속적인 DB 연동 로직을 구현할 수 있지만, 개발자가 작성해야 할 코드가 너무 많다. 스프링은 JDBC 기반의 DB 연동 프로그램을 쉽게 개발할 수 있도록 JdbcTemplate 클래스를 지원한다.  
이 클래스는 JDBC의 반복적인 코드를 제거하기 위해 제공하는 클래스다.  
1. `update()` 메소드  
    SQL 구문에 설정된 "?" 수만큼 값들을 인자에 차례대로 나열하거나, Object 배열 객체를 인자로 넘길 수 있다.
2. `queryForInt()` 메소드  
    SELECT 구문으로 검색된 정수값을 리턴받을 수 있다.
3. `queryForObject()` 메소드  
    SELECT 구문의 실행 결과를 RowMapper 객체로 매핑하여 리턴받을 때 사용한다. 검색 결과가 없거나 2개 이상이면 예외를 발생시킨다.
4. `query()` 메소드  
    SELECT 문의 실행 결과가 여러 개일 때 사용한다. `queryForObject()` 메소드와 사용법은 같고, 객체 여러 개가 `List` 컬렉션에 저장되어 리턴된다.

이제, DAO 클래스를 구현하기 위해 JdbcDaoSupport 클래스를 상속할 수 있다. `getJdbcTemplate()` 메소드를 호출하면 `JdbcTemplate` 객체가 리턴되지만, 이 객체가 리턴되려면 `DataSource` 객체를 가지고 있어야하므로 부모 클래스의 `setDataSource()`를 호출해 의존성 주입한다.  
DAO 클래스에서 `JdbcTemplate` 객체를 얻는 다른 방법은 `JdbcTemplate` 클래스를 \<bean>으로 등록하고 의존성 주입으로 처리한다.  

### 트랜잭션 처리
스프링의 트랜잭션 처리에서는 XML 기반의 AOP만 사용할 수 있고, 어노테이션은 사용할 수 없다. 애스팩트를 설정하는 것도 \<aop:advisor> 엘리먼트를 사용해야 한다.  
트랜잭션을 제어하는 어드바이스를 설정하기 위해 스프링 설정 파일에 트랜잭션 네임스페이스도 추가해야 한다. 그리고, 트랜잭션 관련 설정에서 가장 먼저 등록하는 것은 **트랜잭션 관리자 클래스**이다. 어떤 기술을 이용했는지에 따라 트랜잭션 관리자가 달라지는데, 모두 `PlatformTransactionManager` 인터페이스를 구현했기 때문에 `commit()`과 `rollback()` 메소드를 기본적으로 가지고 있다. 당분간 `DataSourceTransactionManager` 클래스를 이용할 것이므로 이를 \<bean> 등록한다.  
트랜잭션 관리 기능의 어드바이스는 \<tx:advice> 엘리먼트를 사용하여 설정한다. AOP 관련 설정에 사용한 모든 어드바이스 클래스를 직접 구현했었던 반면, **트랜잭션 관리 기능의 어드바이스는 직접 구현하지 않는다**. 이로 인해 어드바이스 메소드 이름을 알 수 없으므로 \<aop:advisor> 엘리먼트를 사용하여 포인트컷과 어드바이스를 결합한다.  
클라이언트가 `BoardServiceImpl` 객체의 `insertBoard()` 메소드를 호출하면, 비즈니스 로직이 수행된다. 수행 중에 문제가 발생하면 `txAdvice`로 등록한 어드바이스가 동작하여 `txManager`의 `rollback()` 메소드를 호출한다. 문제 없이 정상으로 수행되었다면 `commit()` 메소드를 호출한다.  


### 커밋
- [56f3fdc](https://github.com/ItzTree/study-archive/commit/56f3fdc80c14a7e97ed42eb77356dd187c087cbe)  
    BeforeAdvice 클래스에 Before 어노테이션을 적용한다.  

    ```
    [사전 처리] getUser() 메소드 ARGS 정보 : UserVO [id=test, password=test123, name=null, role=null]
    [공통 로그] 비즈니스 로직 수행 전 동작
    ===> JDBC로 getUser() 기능 처리
    관리자님 환영합니다.
    ```
- [b071d2d](https://github.com/ItzTree/study-archive/commit/b071d2dfa3ff9d586884d0fdfe6830d19c991d8b)  
    AfterReturning 등 다른 어드바이스 어노테이션을 적용하고, 외부 클래스의 포인트컷을 적용한다.  
- [678ce69](https://github.com/ItzTree/study-archive/commit/678ce6925ae21d9c790bebf67e5964717a6873a3)  
    JdbcDaoSupport 클래스를 상속하여 DAO 클래스를 구현한다.  
- [fa83550](https://github.com/ItzTree/study-archive/commit/fa835503b6f2ced5dab4398525e3300cb2b7aa0a)  
    bean과 의존성 주입으로 DAO 클래스 구현
-  [a3666ce](https://github.com/ItzTree/study-archive/commit/a3666cebed938d83c4e2b5b23583540faa00cc9d)  
    트랜잭션 처리