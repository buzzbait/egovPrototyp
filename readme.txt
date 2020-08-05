프레임워크 설계 지침

※환경파일 xml 사용금지
web.xml,context-aspect.xml,context-common.xml,context-datasource.xml 등의 환경파일은 모두 JavaConfig 방식으로 변경 한다.
xml 환경 파일을 javaConfig 방식으로 변경하는 것은 최근의 추세이며 xml 방식에 비해 많은 장점이 있다.
xml 파일로 설정하는 오타등 오류가 있는 경우에도 배포시 까지는 확인이 불가 하지만 JavaConfig 방식으로
하는 경우에는 컴파일 시에 오류를 확인 할수 있으며 로깅을 추가하여 Web의 로딩 동작을 추적이 가능 하다.

※SpringSecurity 적용
세션유무,등급별 컨트롤러 제한등은 모두 SpringSecuity 에서 처리한다.
SpringSecurity 를 적용하는 순간 모든 컨트롤러는 세션체크를 진행한다.
- 로그인인증 페이지 지정
- 각 컨트롤별 인증레벨 지정
- 중복 로그인 컨트롤
- access deny redirect 페이지 지정

※DispatcherServlet 구성
ApplicationContext 와 각 도메인별 dipatcher servlet 을 구성한다.
- ApplicationContext : Repository,Service Bean  을 정의 한다.
- business servlet : 일반적인 비지니스 Controller Bean 정의
- open api servlet : 외부에 오픈된 RestController Bean 정의
- private api servlet : 내부에만 오픈된 RestController Bean 정의

일반적으로 도메인별로 N개의 servlet 을 구성한다
servlet 별로 각 주소 매핑을 다르게 한다
(API 타입의 컨트롤러는 Controller 보다는 RestController 를 사용한다)


※오류처리 구성
기본적으로 오류 처리는 HandlerExceptionResolver 보다는 ControllerAdvice(RestControllerAdvice) 를 사용한다.
HandlerExceptionResolver 는 spring 3.2 이전에서 사용하는 방식이고 3.2 이후면 ControllerAdvice 를 전역으로 사용한다.
(servlet config 에서 configureHandlerExceptionResolvers 를 재정의 하지 않는다.configureHandlerExceptionResolvers 를 사용하면 ControllerAdvice 는 작동하지 않는다)
글로벌 ControllerAdvice 에서는 요청에 따라 오류결과를 리턴한다.
ex) 페이지 이동의 경우 오류페이지 강제 redirect, ajax 호출시 json 리턴 분리 처리
- 개발자가 별도로 익셉션 처리를 하지는 않는다.(필요한 경우 내부에서  try-catch 사용)
- 메소드에 throws Exception 사용 금지( checked exception 을 사용 하지 않는다)  


※DTO(VO) with Lombok 사용
DTO 는 Map사용을 금지하고 모두 Class 로 구현 한다.
DTO 구성시 Lombok 사용을 적극적으로 한다.
(lombok 사용 규약 정할것, getter,setter,builder )

※AOP 의 Proxy 방식을 CGLib 방식으로 설정(전자정부프레임웍의 경우 디폴트는 JDK-Proxy)
AOP Proxy 방식을 context 별로 모두 CGLib 방식으로 설정 하여 인터페이스가 없어도 aop proxy를 사용 한다.
(@EnableAspectJAutoProxy 어노테이션 사용)

※Service layer는 별도로 인터페이스를 작성하지 않는다
본체 클래스는 EgovAbstractServiceImpl 을 extends 하여 작성한다.(전자정부프레임웍 적용 기준 사항)
( ex)public class MemberService extends EgovAbstractServiceImpl)

※Mybatis DAO 작성대신 Mapper를 작성 한다
Mapper 는 Interface 로 작성하며 @Mapper 어노테이션을 사용 한다(전자정부프레임웍 적용 기준 사항)

※Controller 작성 규칙
@Controller 어노테이션을 사용 한다.
파라미터는 Get 방식의 경우 HttpServletRequest 또는 @RequestParam 을 사용 한다.

@RequestMapping("/main.do")
public String main(HttpServletRequest request) {
    String id = request.getParameter("id");
    System.out.prinln(id);
    return "main";
}

@RequestMapping("/main.do")
public String main(@RequestParam(value="id", defaultValue="haenny") String id) {
    System.out.prinln(id);
    return "main";
}

Post 방식의 경우 @RequestBody  를 사용한다
@PostMapping("/main.do")
public String main(@RequestBody UserVO user) {
    System.out.prinln(user.getId());
    return "main";
}

ajax 호출의 경우 @ResponseBody를 사용한다.   
(동일 업무인 경우 RestController,Controller 를 분리하지 않는다)

※Service 작성 규칙
@Service,@Transaction 어노테이션을 사용한다.
명시적인 커밋,롤백 사용금지
throw exception 사용금지(사용시 롤백 옵션 지정 할 것)
