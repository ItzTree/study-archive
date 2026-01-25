# Chap08

책의 Day 4 CLASS 04 ~ Day 4 CLASS 07 파트를 다룬다.  

### 검색 기능 추가 구현
검색과 관련된 소스는 getBoardList.jsp 파일에서 확인할 수 있다. 검색 화면에서 검색 조건과 검색 키워드에 해당하는 파라미터 이름은 `searchCondition`과 `searchKeyword`이다. 사용자가 입력한 파라미터 값들이 BoardVO라는 Command 객체에 자동으로 채워지므로, 이에 대한 Getter/Setter 메소드도 필요하다. 검색 조건과 검색 키워드가 전달되지 않을 때, 두 변수에는 null이 설정되는데 기본값을 적절히 설정하여 비즈니스 컴포넌트에 전달하기 위한 null 체크 로직도 필요하다. 


### 커밋
- 