# Chap09

책의 Day 5 CLASS 01 ~ Day 5 CLASS 07 파트를 다룬다.  

### Mybatis
Mybatis 프레임워크의 가장 중요한 특징 중 첫번째는 한두 줄의 자바 코드로 **DB 연동을 처리**한다는 것이고, 두번째는 **SQL 명령어**를 자바 코드에서 분리하여 **XML 파일에 따로 관리**한다는 것이다.  
XML 파일에 저장된 SQL 명령어에 사용자가 입력한 값들을 전달하고 실행 결과를 매핑할 VO 클래스를 작성한다.  
SQL Mapper 파일은 \<mapper>를 루트 엘리먼트로 사용한다. 그리고 \<insert>, \<update>, \<delete>, \<select> 엘리먼트를 이용하여 필요한 SQL 구문들을 등록한다.  
Mybatis를 이용하여 DAO를 구현하려면 SqlSession 객체가 필요하다. 이 SqlSession 객체를 얻으려면 SqlSessionFactory 객체가 필요하다. 따라서 DAO 클래스를 구현하기에 앞서 SqlSessionFactory 객체를 생성하는 유틸리티 클래스를 작성한다.  
BoardDAO 클래스는 생성자에서 SqlSessionFactoryBean을 이용하여 SqlSession 객체를 얻어내고 있다. 이 SqlSession 객체의 메소드를 이용하여 CRUD 기능의 메소드를 모두 구현하고 있다. 구현된 각 메소드를 보면 **실행될 SQL의 id 정보**와 **parameterType 속성으로 지정된 파라미터 객체를 인자**로 넘긴다.  





### 커밋
