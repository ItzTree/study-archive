# Chap10

책의 Day 5 CLASS 03 ~ Day 5 CLASS 06 파트를 다룬다.  

### 스프링과 MyBatis 연동
스프링 쪽에서 Mybatis 연동에 필요한 API를 제공하지 않기 때문에, Mybatis에서 SqlSessionFactoryBean과 SqlSessionTemplate 클래스를 이용하여 연동해야 한다.  
스프링과 Mybatis를 연동하려면 Mybatis 메인 환경설정 파일인 `sql-map-config.xml`과 Mapper 파일이 필요하다. 우선, 스프링 설정 파일에 SqlSessionFactoryBean 클래스를 bean 등록해야 SqlSessionFactoryBean 객체로부터 DB 연동 구현에 사용할 SqlSession 객체를 얻을 수 있다.  
재정의한 `setSqlSessionFactory()` 메소드 위에 @Autowired 어노테이션을 붙여야 스프링 컨테이너가 이 메소드를 자동으로 호출하고, 스프링 설정 파일에 bean 등록된 SqlSessionFactoryBean 객체를 인자로 받아 부모인 SqlSessionDaoSupport에 `setSqlSessionFactory()` 메소드로 설정해준다. 이렇게 해야 SqlSessionDaoSupport 클래스로부터 상속된 `getSqlSession()` 메소드를 호출하여 SqlSession 객체를 리턴받을 수 있다.  
다른 방법으로, 스프링 설정 파일에서 SqlSessionTemplate 클래스를 bean 등록하여 사용할 수 있다. SqlSessionTemplate 클래스에는 Setter 메소드가 없기 때문에 인젝션을 할 수 없으므로, 생성자 메소드를 이용한 Constructor 주입으로 처리해야 한다. 이후 DAO 클래스 구현 시, SqlSessionTemplate 객체를 @Autowired를 이용하여 의존성 주입 처리한다.  

### JPA
자바의 객체와 데이터베이스의 테이블이 정확하게 일치하지 않기 때문에 둘 사이를 매핑하기 위하여 많은 SQL 구문과 자바 코드를 필요로 한다. <b>ORM(Object-Relation Mapping)</b>은 정확하게 일치하지 않는 자바 객체와 테이블 사이를 매핑해준다. 이 과정에서 사용되는 SQL 구문과 자바 코드는 ORM 프레임워크가 자동으로 만들어준다.  
지금까지 스프링 JDBC나 Mybatis를 사용하여 자바 객체와 테이블을 매핑할 때, SQL 명령어를 자바 클래스나 XML 파일에 작성해야 했다. ORM 프레임워크를 사용하면 DB 연동에 필요한 SQL을 자동으로 생성한다.  
<b>JPA(Java Persistence API)</b>는 모든 ORM 구현체들의 공통 인터페이스를 제공한다. JPA API를 이용하면 ORM 프레임워크를 필요할 때 변경할 수 있다.  

### JPA 환경설정
<b>영속성 유닛(Persistence Unit)</b>은 연동할 데이터베이스 당 하나씩 등록하며, DAO 클래스를 구현할 때 EntityManagerFactory 객체 생성에 사용된다. JPA를 이용하여 DB 연동을 구현하려면 EntityManager 객체가 필요하기 때문이다.  
ORM 프레임워크의 가장 중요한 특징이자 장점은 애플리케이션 수행에 필요한 SQL 구문을 자동으로 생성한다는 것이다. 그런데 지원되는 함수 등 DBMS마다 다른 부분이 있지만, JPA가 지원하는 Dialect 클래스를 사용하면 해당 DBMS에 최적화된 SQL 구문을 생성해준다.  

### 엔티티 클래스 기본 매핑
JPA의 기본은 엔티티 클래스를 기반으로 관계형 데이터베이스에 저장된 데이터를 관리하는 것이고, 엔티티 매핑에서 가장 중요한 것은 연관 매핑 설정이다. 
- **@Entity, @Id**  
    @Entity는 특정 클래스를 JPA가 관리하는 엔티티 클래스로 인삭하는 가장 중요한 어노테이션이다. 엔티티 클래스와 매핑되는 테이블은 각 ROW를 식별하기 위한 Primary Key 칼럼을 가지고 있다. 엔티티 클래스에도 PK 칼럼과 매핑될 변수인 **식별자 필드**를 가지고 있어야 한다. 식별자 필드는 @Id를 이용하여 선언한다.  
- **@Table**  
    @Entity를 이용하면 엔티티 클래스와 이름이 같은 테이블을 자동으로 매핑하지만, @Table을 이용하면 매핑될 테이블 이름을 지정할 수 있다.  
- **@Column**  
    엔티티 클래스의 변수와 테이블의 칼럼을 매핑할 때 사용한다. 엔티티 클래스의 변수 이름과 칼럼 이름이 다를 때에 사용한다.  
- **@GeneratedValue**  
    @GeneratedValue는 @Id로 지정된 식별자 필드에 Primary Key 값을 생성하여 저장할 때 사용한다. PK 값 생성 방법에는 TABLE, SEQUENCE, IDENTITY, AUTO가 있다.  
- **@Transient**  
    엔티티 클래스의 변수 중에서 매핑되는 칼럼이 없거나 아예 매핑에서 제외해야만 되는 경우에 사용한다.  
- **@Temporal**  
    java.util.Date 타입의 날짜 데이터를 매핑할 때 사용한다. 날짜 정보만 출력할지, 시간 정보만 출력할지 등을 정할 수 있다.  

### JPA API
```java
// Transaction 시작
tx.begin();

Board board = new Board();
board.setTitle("JPA 제목");
board.setWriter("관리자");
board.setContent("JPA 글 등록 잘 되네요.");

// 글 등록
em.persist(board);
```

트랜잭션을 시작하고 엔티티 클래스로 등록된 Board 객체를 생성한 다음, 글 등록에 필요한 값들을 저장한다. 반드시 EntityManager의 persist() 메소드로 영속화해야 INSERT 작업이 처리된다.  
JPA 고유의 쿼리 구문인 JPQL을 작성하고 실행하면 JPA 구현체가 연동되는 DBMS에 맞게 JPQL을 적절한 SELECT 명령어로 변환한다.  

### 커밋
- [4b47b83](https://github.com/ItzTree/study-archive/commit/4b47b8329ce67a44d92370181d37c4d406376378)  
    Mybatis를 이용하여 DAO 클래스를 구현하는 방법 중 SqlSessionDaoSupport 클래스를 상속하여 구현한다.  
- [8daf413](https://github.com/ItzTree/study-archive/commit/8daf41353522b956020c4994b6e79cee4b764f16)  
    SqlSessionTemplate 클래스를 bean 등록하여 DAO 클래스를 구현한다.  
- [bfd8c17](https://github.com/ItzTree/study-archive/commit/bfd8c17245b21a946dab8ef88953b7b4e620bff8)  
    Dynamic SQL을 이용하여 검색 기능을 처리한다.  
- [510ae55](https://github.com/ItzTree/study-archive/commit/510ae55b9dfb80999601d6af73fc27f20fc1c72c)  
    JPA 기본 구조를 설정한다.  
- [0a26d11](https://github.com/ItzTree/study-archive/commit/0a26d11ae09e8f715a2cdc844a72d014fedeb87f)  
    BoardWeb 프로젝트를 JPA 연동한다.  