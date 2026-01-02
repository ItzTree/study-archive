# Chap01

책의 Day 1 CLASS 03 ~ Day 1 CLASS 05 파트를 다룬다.  

### 스프링 XML 주요 엘리먼트
1. \<beans> : 스프링 컨테이너는 \<bean> 저장소에 해당하는 XML 설정 파일을 참조하여 \<bean>의 생명주기를 관리한다. 루트 엘리먼트 \<beans>는 스프링 설정 파일에 꼭 있어야 한다.
  2. \<import> : 스프링 설정 파일에는 단순히 \<bean> 뿐만 아니라, 트랜잭션 관리, 예외 처리 등 다양한 설정이 필요하다. 기능별로 여러 XML 파일로 나누어 설정하기 위해 \<import> 엘리먼트를 사용한다. 이를 이용하여 여러 스프링 설정 파일을 포함함으로써 한 파일에 작성하는 것과 같은 효과를 낸다.
  3. \<bean> : 스프링 설정 파일에 클래스를 등록하기 위해 사용한다. class 속성만 사용해도 객체가 생성되지만, 특정 객체를 lookup하기 위해서는 id 속성이 필요하다.
      - **init-method**  
      스프링 컨테이너는 객체 생성 시 디폴트 생성자를 호출하기 때문에, 멤버변수 초기화를 하기 위해서는 **init-method 속성**을 통해 작업할 수 있다.
      - **destroy-method**  
      스프링 컨테이너가 객체를 삭제하기 직전에 호출될 메소드를 지정할 수 있다.
      - **lazy-init**  
      컨테이너가 구동되는 시점에 \<bean>을 pre-loading 하지만ㅉ, lazy-init 속성을 사용하면 클라이언트가 요청하는 시점에 생성하여 메모리를 효율적으로 사용한다.
      - **scope**  
      하나의 객체만 생성하도록 제어하기 위해 **singleton** 패턴을 사용한다. scope 속성값은 기본으로 싱글톤으로 지정되어 있어, 여러번 객체를 요청하더라도 하나만 생성된다. 속성값을 **prototype**으로 지정하면 매번 새로운 객체를 생성한다.

### 의존성 관리
스프링 프레임워크는 객체의 생성과 의존관계를 컨테이너가 자동으로 관리한다. 이는 IoC의 핵심 원리이기도 하다. 스프링은 IoC를 Dependency Lookup과 Dependency Injection의 형태로 지원한다.  
  **Dependency Lookup** : 컨테이너가 객체를 생성하고, 클라이언트가 검색(lookup)하여 사용하는 방식. 하지만 실제로는 사용하지 않는다.  
  **Dependency Injection** : 객체 사이의 의존성을 스프링 설정 파일을 바탕으로 컨테이너가 알아서 처리한다. 이는 다시 세터 인젝션과 생성자 인젝션으로 나뉜다.  

### 생성자 인젝션
컨테이너는 기본적으로 매개변수가 없는 디폴트 생성자를 호출하는데, 생성자 인젝션을 통해 **매개변수를 갖는 다른 생성자**를 호출하게 할 수 있다.  
원래는 bean에 등록된 순서대로 객체를 생성하지만, 생성자 인젝션으로 인해 의존성이 생긴 경우에는 순서가 뒤바뀔 수도 있다.

### 어노테이션
애플리케이션에서 사용할 객체들을 \<bean>에 따로 등록하지 않고, \<context:component-scan> 엘리먼트를 통해 자동으로 등록할 수 있다. \@Component를 클래스 선언부 위에 아이디와 함께 설정하여 객체를 요청할 수 있다.

### Setter 인젝션
Setter 메소드를 호출하여 멤버변수를 원하는 값으로 설정한다. 생성자 인젝션과 결과가 같아 어떤 방법을 사용하더라도 상관 없지만, 대부분 Setter 인젝션을 사용한다.  
Setter 메소드는 bean 객체 생성 직후에 스프링 컨테이너가 자동으로 호출한다. Setter 인젝션이 동작하려면 메소드뿐만 아니라 기본 생성자도 반드시 필요하다.  
스프링 설정 파일에는 \<constructor-arg> 대신 \<property> 엘리먼트를 사용한다.  
p 네임스페이스를 사용하여 의존성을 주입할 수도 있다. `p:변수명-ref="참조할 객체 이름"`이나 `p:변수명="설정할 값"` 꼴로 사용하면 된다.

### 컬렉션(Collection) 객체 설정
List 같은 컬렉션 객체에 의존성을 주입해야 할 때가 있다. 이와 관련하여 \<list>, \<set> 등의 엘리먼트를 사용하여 컬렉션을 매핑할 수 있다.

### 커밋
- [b432b4d](https://github.com/ItzTree/study-archive/commit/b432b4dfb7ab48c1cd5fe65a721c75b34e701bd9)  
  `resources` 폴더에 `applicationContext.xml`을 만들고 기존의 SamsungTV 클래스를 등록한다. 스프링 컨테이너를 구동하고 테스트하기 위해 TVUser 클래스를 일부 수정한다.
  `applicationContext.xml` 로딩 → `<bean>` 설정 확인 → SamsungTV 클래스 생성자 호출 → `getBean("tv")` 호출 → 컨테이너가 관리하는 SamsungTV 객체 리턴
  컨테이너가 구동되는 시점(`new GenericXmlApplicationContext`)에 설정 파일을 읽어 bean 객체를 미리 생성(pre-loading)해둔다. 그래서 `getBean()`을 호출하지 않더라도 생성자는 호출이 되어 "===> SamsungTV 객체 생성"이라는 메시지가 출력된다.
- [08dd798](https://github.com/ItzTree/study-archive/commit/08dd7981bb4b64d1be82c32b2437baf31a1bb2b9)  
  applicationContext.xml 파일에 init-method와 destroy-method 속성을 설정하여 객체의 생성과 삭제를 알아본다.  

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
  같은 객체를 여러 번 요청했을 때, 싱글톤 패턴에 의해 한 번만 생성되는 것을 알아본다.
- [b13bd83](https://github.com/ItzTree/study-archive/commit/b13bd83963fbff0a8027133ae951b774b4f6bcde)  
  의존성을 알아보기 위해 SonySpeaker 클래스를 추가하였다. 하지만 여기서는 SonySpeaker 객체가 쓸데없이 두 개나 생성되는 문제와 다른 스피커로 바꿔야 할 때, 소스코드를 직접 수정해야 한다는 문제가 있다.
- [dd4e3ae](https://github.com/ItzTree/study-archive/commit/dd4e3aed95c7d28478d6902d16d49926973ebfa3)  
  생성자 인젝션을 사용하여 SonySpeaker를 매개변수로 갖는 생성자를 호출했다. SonySpeaker 객체가 먼저 생성되고 나서 SamsungTV 객체가 생성되었다.  

  ```
  ===> SonySpeaker 객체 생성
  ===> SamsungTV(2) 객체 생성
  SamsungTV---전원 켠다.
  SonySpeaker---소리 올린다.
  SonySpeaker---소리 내린다.
  SamsungTV---전원 끈다.
  ```
- [dfcdc1e](https://github.com/ItzTree/study-archive/commit/dfcdc1e562dde17d481761ce8ff8097d0f032ad2)  
  \<constructor-arg> 엘리먼트에 ref와 value 속성을 사용하여 전달할 값을 지정하고, index 속성을 사용하여 어떤 값이 몇 번째 매개변수로 매핑되는지 지정한다.
- [bd9a4ef](https://github.com/ItzTree/study-archive/commit/bd9a4ef621454080242a14a7a6978db2da3fea1b)  
  Speaker 인터페이스를 생성하고 AppleSpeaker 클래스를 만든 후 스프링 설정 파일을 수정한다.  

  ```
  ===> AppleSpeaker 객체 생성
  ===> SamsungTV(3) 객체 생성
  ===> SonySpeaker 객체 생성
  SamsungTV---전원 켠다. (가격 : 2700000)
  AppleSpeaker---소리 올린다.
  AppleSpeaker---소리 내린다.
  SamsungTV---전원 끈다.
  ```
- [020d18e](https://github.com/ItzTree/study-archive/commit/020d18ea765d7771e2a7b21dbb6382d1c6716563)  
  Setter 인젝션을 사용하기 위하여 스프링 설정 파일을 수정하고 실행한다. 생성자 인젝션과 다르게 SamsungTV가 Speaker 객체보다 먼저 생성된다.  

  ```
  ===> SamsungTV(1) 객체 생성
  ===> AppleSpeaker 객체 생성
  ===> setSpeaker() 호출
  ===> setPrice() 호출
  ===> SonySpeaker 객체 생성
  SamsungTV---전원 켠다. (가격 : 2700000)
  AppleSpeaker---소리 올린다.
  AppleSpeaker---소리 내린다.
  SamsungTV---전원 끈다.
  ```
- [7cbf966](https://github.com/ItzTree/study-archive/commit/7cbf966a5d1910692fe7f0dba3deb0866fb49d54)  
  Setter 인젝션을 설정할 때, p 네임스페이스를 사용할 수도 있다.

- [42dde72](https://github.com/ItzTree/study-archive/commit/42dde72cf55192aac9806e4ddb09c94f9e374817)  
  컬렉션 객체를 매핑하는 예시로, List 타입을 매핑한다.