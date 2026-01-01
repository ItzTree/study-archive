# Chap01

책의 Day 1 CLASS 03 ~ Day 1 CLASS 05 파트를 다룬다.  


### 커밋
- [b432b4d](https://github.com/ItzTree/study-archive/commit/b432b4dfb7ab48c1cd5fe65a721c75b34e701bd9)  
  `resources` 폴더에 `applicationContext.xml`을 만들고 기존의 SamsungTV 클래스를 등록한다. 스프링 컨테이너를 구동하고 테스트하기 위해 TVUser 클래스를 일부 수정한다.
  `applicationContext.xml` 로딩 → `<bean>` 설정 확인 → SamsungTV 클래스 생성자 호출 → `getBean("tv")` 호출 → 컨테이너가 관리하는 SamsungTV 객체 리턴
  컨테이너가 구동되는 시점(`new GenericXmlApplicationContext`)에 설정 파일을 읽어 bean 객체를 미리 생성(pre-loading)해둔다. 그래서 `getBean()`을 호출하지 않더라도 생성자는 호출이 되어 "===> SamsungTV 객체 생성"이라는 메시지가 출력된다.
- [08dd798](https://github.com/ItzTree/study-archive/commit/08dd7981bb4b64d1be82c32b2437baf31a1bb2b9)  
  1. \<beans> : 스프링 컨테이너는 \<bean> 저장소에 해당하는 XML 설정 파일을 참조하여 \<bean>의 생명주기를 관리한다. 루트 엘리먼트 \<beans>는 스프링 설정 파일에 꼭 있어야 한다.
  2. \<import> : 스프링 설정 파일에는 단순히 \<bean> 뿐만 아니라, 트랜잭션 관리, 예외 처리 등 다양한 설정이 필요하다. 기능별로 여러 XML 파일로 나누어 설정하기 위해 \<import> 엘리먼트를 사용한다. 이를 이용하여 여러 스프링 설정 파일을 포함함으로써 한 파일에 작성하는 것과 같은 효과를 낸다.
  3. \<bean> : 스프링 설정 파일에 클래스를 등록하기 위해 사용한다. class 속성만 사용해도 객체가 생성되지만, 특정 객체를 lookup하기 위해서는 id 속성이 필요하다.
      - init-method 속성  
      스프링 컨테이너는 객체 생성 시 디폴트 생성자를 호출하기 때문에, 멤버변수 초기화를 하기 위해서는 **init-method 속성**을 통해 작업할 수 있다.
      - destroy-method 속성  
      스프링 컨테이너가 객체를 삭제하기 직전에 호출될 메소드를 지정할 수 있다.
      - lazy-init 속성  
      컨테이너가 구동되는 시점에 \<bean>을 pre-loading 하지만, lazy-init 속성을 사용하면 클라이언트가 요청하는 시점에 생성하여 메모리를 효율적으로 사용한다.
  ```
  ===> SamsungTV 객체 생성
  객체 초기화 작업 처리...
  SamsungTV---전원 켠다.
  SamsungTV---소리 올린다.
  SamsungTV---소리 내린다.
  SamsungTV---전원 끈다.
  객체 삭제 전에 처리할 로직 처리...
  ```
- [ba3f96c](https://github.com/ItzTree/study-archive/commit/ba3f96cfcb29de3da5c795411f230c76a6875053)  
  - scope 속성
  하나의 객체만 생성하도록 제어하기 위해 **singleton** 패턴을 사용한다. scope 속성값은 기본으로 싱글톤으로 지정되어 있어, 여러번 객체를 요청하더라도 하나만 생성된다. 속성값을 **prototype**으로 지정하면 매번 새로운 객체를 생성한다.