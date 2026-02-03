# Chap09

책의 Day 5 CLASS 03 ~ Day 5 CLASS 07 파트를 다룬다.  

### 스프링과 MyBatis 연동
스프링 쪽에서 Mybatis 연동에 필요한 API를 제공하지 않기 때문에, Mybatis에서 SqlSessionFactoryBean과 SqlSessionTemplate 클래스를 이용하여 연동해야 한다.  
스프링과 Mybatis를 연동하려면 Mybatis 메인 환경설정 파일인 `sql-map-config.xml`과 Mapper 파일이 필요하다. 우선, 스프링 설정 파일에 SqlSessionFactoryBean 클래스를 bean 등록해야 SqlSessionFactoryBean 객체로부터 DB 연동 구현에 사용할 SqlSession 객체를 얻을 수 있다.  
재정의한 `setSqlSessionFactory()` 메소드 위에 @Autowired 어노테이션을 붙여야 스프링 컨테이너가 이 메소드를 자동으로 호출하고, 스프링 설정 파일에 bean 등록된 SqlSessionFactoryBean 객체를 인자로 받아 부모인 SqlSessionDaoSupport에 `setSqlSessionFactory()` 메소드로 설정해준다. 이렇게 해야 SqlSessionDaoSupport 클래스로부터 상속된 `getSqlSession()` 메소드를 호출하여 SqlSession 객체를 리턴받을 수 있다.  
다른 방법으로, 스프링 설정 파일에서 SqlSessionTemplate 클래스를 bean 등록하여 사용할 수 있다. SqlSessionTemplate 클래스에는 Setter 메소드가 없기 때문에 인젝션을 할 수 없으므로, 생성자 메소드를 이용한 Constructor 주입으로 처리해야 한다. 이후 DAO 클래스 구현 시, SqlSessionTemplate 객체를 @Autowired를 이용하여 의존성 주입 처리한다.  





### 커밋
- [4b47b83](https://github.com/ItzTree/study-archive/commit/4b47b8329ce67a44d92370181d37c4d406376378)  
    Mybatis를 이용하여 DAO 클래스를 구현하는 방법 중 SqlSessionDaoSupport 클래스를 상속하여 구현한다.  
- 