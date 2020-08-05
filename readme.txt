프레임워크 설계 지침

※환경파일 xml 사용금지
context-aspect.xml,context-common.xml,context-datasource.xml 등의 환경파일은 모두 JavaConfig 방식으로 변경 한다.
xml 환경 파일을 javaConfig 방식으로 변경하는 것은 최근의 추세이며 xml 방식에 비해 많은 장점이 있다.
xml 파일로 설정하는 오타등 오류가 있는 경우에도 배포시 까지는 확인이 불가 하지만 JavaConfig 방식으로
하는 경우에는 컴파일 시에 오류를 확인 할수 있으며 로깅을 추가하여 Web의 로딩 동작을 추적이 가능 하다.

※SpringSecurity 적용
세션유무,등급별 컨트롤러 제한등은 모두 SpringSecuity 에서 처리한다.
SpringSecurity 를 적용하는 순간 모든 컨트롤러는 세션체크를 진행한다.
- 로그인인증 페이지 지정
- 각 컨트롤별 인증레벨 지정
- 중복 로그인 컨트롤

※DispatcherServlet 구성



※오류처리 구성



※오류처리 구성


