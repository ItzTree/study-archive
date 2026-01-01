# Chap01

책의 Day 1 CLASS 03 ~ Day 1 CLASS 05 파트를 다룬다.  


### 커밋
- [b432b4d](https://github.com/ItzTree/study-archive/commit/b432b4dfb7ab48c1cd5fe65a721c75b34e701bd9)  
  `resources` 폴더에 `applicationContext.xml`을 만들고 기존의 SamsungTV 클래스를 등록한다. 스프링 컨테이너를 구동하고 테스트하기 위해 TVUser 클래스를 일부 수정한다.
  `applicationContext.xml` 로딩 → `<bean>` 설정 확인 → SamsungTV 클래스 생성자 호출 → `getBean("tv")` 호출 → 컨테이너가 관리하는 SamsungTV 객체 리턴
  컨테이너가 구동되는 시점(`new GenericXmlApplicationContext`)에 설정 파일을 읽어 bean 객체를 미리 생성해둔다. 그래서 `getBean()`을 호출하지 않더라도 생성자는 호출이 되어 "===> SamsungTV 객체 생성"이라는 메시지가 출력된다.