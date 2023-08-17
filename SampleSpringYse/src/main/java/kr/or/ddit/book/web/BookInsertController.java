package kr.or.ddit.book.web;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.book.service.BookService;


/*
 * @Controller 어노테이션이 있는 클래스는 스프링이 브라우저의 요청(request)을 받아들이는 컨트롤러라고 인지해서 
 * 자바 빈(Java Bean)으로 등록해서 관리한다.
 * 여기서 자바 빈이란 객체를 만들어서 메모리에 올리는 형태를 말한다. 
 */

@Controller // controller임을 명시하는 역할. 최초의 요청을 받는 역할. 
@RequestMapping("/book") // url위치를 나타냄. 
public class BookInsertController {
	
	/* 나
	 * @Inject : 
	 * 서버가 run되는 순간 서블릿과 root-context.xml에서 설정한 객체가 만들어짐
	 * 이때 만들어진 객체를 주입하는 역할
	 * 싱글톤 패턴에서 객체를 만들던 역할을 함.
	 * inject 어노테이션이 없으면 만들어둔 service 객체가 없기 때문에 null이 됨.  
	 */
	/*
	 * 서비스를 호출하기 위해 BookService를 의존성을 주입한다.
	 * 의존성 주입을 통한 결합도 낮추기
	 */
	@Inject
	private BookService bookService;
	
	/*
	 * @RequestMapping
	 * - 요청 URL을 어떤 메소드가 처리할 지 여부를 결정
	 * 
	 * method 속성은 http 요청 메소드를 의미한다.
	 * 일반적인 웹 페이지 개발에서 GET 메소드는 데이터를 변경하지 않는 경우에, POST 메소드는 데이터가 변경될 경우 사용된다.
	 * 
	 * ModelAndView는 컨트롤러가 반환할 데이터를 담당하는 모델(Model)과 화면을 담당하는 뷰(View)의 경로를 합쳐놓은 객체다.
	 * ModelAndView의 생성자에 문자열 타입 파라미터가 입력되면 뷰의 경로라고 간주한다.
	 * 뷰의 경로를 'book/form'과 같은 형태로 전달하는 이유는 요청(request)에 해당하는 url의 mapping되는 화면의 경로 값을 
	 * ViewResolver라는 녀석이 제일 먼저 받아 suffix, prefix속성에 의해서 앞에는 '/WEB-INF/views/'가 붙고 
	 * 뒤에는 '.jsp'가 붙어 최종 위치에 해당하는 jsp파일을 찾아준다.
	 */
	
	@RequestMapping(value="/form.do", method=RequestMethod.GET) 
	public ModelAndView bookForm() { // 페이지 이동 : forward. 이유 : 데이터 수정, 변형이 일어나지 않음. 
		// redirect는 url을 리턴. redirect일 때는 다음과 같이 사용
		// return new ModelAndView("redirect:book/form.do");
		// forward, redirect의 큰 특징 : 데이터 가용. 
		// 데이터를 가용하지 않고, 단일 페이지를 요청할 때는 페이지 이동방식 : forward
		// ModelAndView : 화면과 데이터를 관장하는 return 타입. 데이터, 화면 경로 모두 나타낼수 있음
		// 앞단의 경로와 파일확장자명은 WEB-INF -> spring-> appServlet -> servlet-context.xml에 ViewResolver 안에 존재함.
		return new ModelAndView("book/form");  // 파일 위치만 나와있음
		// 파일 위치경로(new ModelAndView("경로").setViewName). 데이터 저장 가능(new ModelAndView("경로").addObject)
	}
	
	
	/*
	 * 데이터의 변경이 일어나므로 http메소드는 POST방식으로 처리
	 * @RequestParam은 HTTP 파라미터를 map변수에 자동으로 바인딩한다.
	 * Map 타입의 경우는 @RequestParam을 붙여야만 HTTP 파라미터의 값을 map안에 바인딩해준다.
	 */
	@RequestMapping(value = "/form.do", method = RequestMethod.POST)
	public ModelAndView insertBook(@RequestParam Map<String, Object> map) {
		// map 객체를 파라미터로 받을 때는 @RequestParam 어노테이션 필요
		ModelAndView mav = new ModelAndView();
		
		// 서비스 메소드 insertBook을 호출하여 책을 등록한다.
		// 서비스 메소드 insertBook을 통해서 책을 등록하고 결과로 bookId를 리턴 받아온다.
		String bookId = bookService.insertBook(map);
		if(bookId == null) {
			// 데이터 입력이 실패할 경우 다시 데이터를 입력받아야 하므로 생성 화면으로 redirect한다.
			// ModelAndView 객체는 .setViewName메소드를 통해 뷰의 경로를 지정할 수 있다.
			mav.setViewName("redirect:/book/form.do");
			// 뷰의 경로가 redirect:로 시작하면 스프링은 뷰 파일을 찾아가는게 아니라
			// 웹 페이지의 주소(/form.do)를 찾아간다.
		} else {
			// 데이터 입력이 성공하면 상세 페이지로 이동한다.
			mav.setViewName("redirect:/book/detail.do?bookId="+bookId);
		}
		return mav;
	}
	
	
	
	
	
}	
