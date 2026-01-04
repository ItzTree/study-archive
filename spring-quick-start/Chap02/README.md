# Chap02

책의 Day 1 CLASS 06 ~ Day 1 CLASS 07 파트를 다룬다.  

### 비즈니스 컴포넌트 실습
VO(Value Object) 클래스는 레이어와 레이어 사이에서 관련된 데이터를 한꺼번에 주고받을 목적으로 사용한다. 테이블에 포함된 칼럼과 같은 이름의 멤버변수를 private로 선언하고, getter와 setter 메소드로 접근한다.  
DAO(Data Access Object) 클래스는 데이터베이스 연동을 담당한다. CRUD 기능의 메소드가 구현되어야 하며, 여기서는 H2 데이터베이스의 JDBC 드라이버를 사용하였다. 이 DAO 클래스 객체를 스프링 컨테이너가 생성할 수 있도록 `@Repository`를 설정한다.
Service 인터페이스는 DAO를 토대로 만들어지지만, 구현은 ServiceImpl에서 이루어져야 한다.  
ServiceImpl 클래스 선언부에는 객체 생성을 위해 `@Service`가 선언되어 있고, DAO 타입의 객체를 의존성 주입하기 위하여 `@Autowired`를 설정한다.  


### 커밋
- [90677f5](https://github.com/ItzTree/study-archive/commit/90677f50d3e6713afff148a548adbc308c64a2c2)  
  BoardService 컴포넌트를 만들어 비즈니스 컴포넌트 실습을 진행하였다. DAO에 `@Repository` 어노테이션을, ServiceImpl에 `@Autowired` 어노테이션을 사용하였다.
- 