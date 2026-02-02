# Chap09

책의 Day 5 CLASS 01 ~ Day 5 CLASS 07 파트를 다룬다.  

### Mybatis
Mybatis 프레임워크의 가장 중요한 특징 중 첫번째는 한두 줄의 자바 코드로 **DB 연동을 처리**한다는 것이고, 두번째는 **SQL 명령어**를 자바 코드에서 분리하여 **XML 파일에 따로 관리**한다는 것이다.  
XML 파일에 저장된 SQL 명령어에 사용자가 입력한 값들을 전달하고 실행 결과를 매핑할 VO 클래스를 작성한다.  
SQL Mapper 파일은 \<mapper>를 루트 엘리먼트로 사용한다. 그리고 \<insert>, \<update>, \<delete>, \<select> 엘리먼트를 이용하여 필요한 SQL 구문들을 등록한다.  
Mybatis를 이용하여 DAO를 구현하려면 SqlSession 객체가 필요하다. 이 SqlSession 객체를 얻으려면 SqlSessionFactory 객체가 필요하다. 따라서 DAO 클래스를 구현하기에 앞서 SqlSessionFactory 객체를 생성하는 유틸리티 클래스를 작성한다.  
BoardDAO 클래스는 생성자에서 SqlSessionFactoryBean을 이용하여 SqlSession 객체를 얻어내고 있다. 이 SqlSession 객체의 메소드를 이용하여 CRUD 기능의 메소드를 모두 구현하고 있다. 구현된 각 메소드를 보면 **실행될 SQL의 id 정보**와 **parameterType 속성으로 지정된 파라미터 객체를 인자**로 넘긴다.  

### SQL Mapper XML 기본 설정
Mybatis의 구조에 대해 설명하겠다. SqlMapConfig.xml 파일은 Mybatis 메인 환경설정 파일이다. Mybatis는 이 파일을 읽어 어떤 DBMS와 커넥션을 맺을지, 어떤 SQL Mapper XML 파일들이 등록되어 있는지 알 수 있다.  
Mybatis는 SqlMap.xml 파일에 등록된 각 SQL 명령어들을 Map 구조로 저장하여 관리한다. 각 SQL 명령어는 고유한 아이디 값을 가지고 있으므로 특정 아이디로 등록된 SQL을 실행할 수 있다. SQL이 **실행될 때 필요한 값**들은 input 형태의 데이터로 할당하고, SQL이 **SELECT 구문**일 때는 output 형태의 데이터로 리턴한다.  
Mybatis 프레임워크에서 가장 중요한 파일은 SQL 명령어들이 저장되는 SQL Mapper XML(이하, Mapper) 파일이다. Mapper 파일의 구조로 가장 먼저 DTD 선언이 등장하고, \<mapper> 루트 엘리먼트가 선언된다. 이 엘리먼트는 **namespace** 속성을 가지며, DAO 클래스에서 Mapper의 SQL을 참조할 때 네임스페이스와 SQL의 아이디를 결합하여 참조한다.  
\<select> 엘리먼트는 데이터를 조회하는 SELECT 구문을 작성할 때 사용한다. 이 엘리먼트에는 parameterType과 resultType 속성을 사용할 수 있다.  
- **id 속성**  
    id 속성은 필수 속성으로, 반드시 전체 Mapper 파일들 내에서 유일한 아이디를 등록해야 한다. 루트 엘리먼트인 \<mapper>에 설정된 네임스페이스는 여러 아이디를 하나의 네임스페이스로 묶는다. 서로 다른 파일에서 아이디를 같게 선언하더라도 **네임스페이스**가 다르다면 다른 아이디로 처리된다.  
- **parameterType 속성**  
    Mapper 파일에 등록된 SQL을 실행하려면 SQL 실행에 필요한 데이터를 외부로부터 받아야 하는데, 이때 사용하는 속성이 parameterType이다. 속성값으로는 기본형이나 VO 형태의 클래스를 지정한다. Mybatis 메인 설정 파일 `sql-map-config.xml`에 등록된 alias를 사용할 수 있다.  
- **resultType 속성**  
    검색 관련 SQL 구문이 실행되면 ResultSet이 리턴되며, 이 ResultSet에 저장된 **검색 결과를 어떤 자바 객체에 매핑**할지를 resultType 속성을 이용하여 지정한다. 이 속성은 \<select> 엘리먼트에서만 사용할 수 있으며, 생략할 수 없다.  
\<insert> 엘리먼트는 데이터베이스에 데이터를 삽입하는 INSERT 구문을 작성하는 요소이다. \<selectKey>라는 자식 엘리먼트를 통해 생성된 키를 쉽게 가져올 수 있다. 이 외에도, \<update>와 \<delete> 엘리먼트는 각각 UPDATE, DELETE 역할을 수행한다.  

### SQL Mapper XML 추가 설정
검색 결과를 특정 자바 객체에 매핑하여 리턴하기 위해서 parameterType 속성을 사용한다. 하지만, 검색 결과를 하나의 자바 객체로 매핑할 수 없거나 칼럼과 변수가 달라 검색 결과가 자바 객체로 매핑되지 않았을 때에 resultMap 속성을 사용할 수 있다.  
SQL 구문 내에 '<' 기호를 사용하면 태그의 시작으로 처리하기 때문에 에러가 발생한다. 하지만 CDATA Section으로 SQL 구문을 감싸주면 에러는 사라진다. 나중에 '<'나 '>'를 연산자로 사용할 것을 대비해 모든 SQL 구문을 CDATA Section으로 처리하는 것이 좋다.  
SQL 구문은 일반적으로 대문자로 작성하여 파라미터들을 식별할 수 있게 한다.  

### Mybatis JAVA API  
MyBatis로 DAO 클래스의 CRUD 메소드를 구현하려면 Mybatis에서 제공하는 SqlSession 객체를 사용해야 한다. SqlSession 객체는 SqlSessionFactory로부터 얻어야하므로, 가장 먼저 해야할 작업은 SqlSessionFactory 객체를 생성하는 일이다.  
SqlSessionFactory 객체를 생성하려면 SqlSessionFactoryBuilder의 `build()` 메소드를 이용하는데, 이 메소드는 Mybatis 설정 파일을 로딩한다.  
SqlSessionFactory 객체는 `openSession()` 메소드를 제공하며, 이 메소드를 이용하여 SqlSession 객체를 얻을 수 있다. 이렇게 얻어낸 SqlSession 객체를 통해 글 등록 기능을 처리한다.  





### 커밋
- [7dde09f](https://github.com/ItzTree/study-archive/commit/7dde09fdc749ec46219a726dfc94da91d650e95e)  
    Mybatis 프레임워크를 위한 VO, DAO, 설정 파일 등을 작성한다.  
