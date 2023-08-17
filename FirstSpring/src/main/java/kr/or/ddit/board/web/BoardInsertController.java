package kr.or.ddit.board.web;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardInsertController {
	
	@Inject
	private IBoardService boardService;
	
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public String boardForm() {
		return "board/form";
	}
	
	@RequestMapping(value="/insert.do", method = RequestMethod.POST)
	   public String boardInsert(BoardVO boardVO, Model model) {
		// 페이지로 이동하는 방법 String, ModelAndView, ... 등등 
		// 경로를 제공하는 방법이 다를뿐 결과는 경로를 이동하는 것
		// 제일 많이 쓰는 것은 String 타입
		
	      String goPage = ""; // 페이지를 담을 공간
	      Map<String, String> errors = new HashMap<String, String>(); // 에러를 담을 공간
	      
	      // org.apache.commons 라이브러리를 이용하여 boardVO안에 들어있는
	      // 제목, 내용을 공백/null 체킹을 통해 입력값 필터를 처리할 수 있다. 
	      // => 라이브러리를 이용하면 boardVO.getBoContenet().equals("")를  
	      // StringUtils.isBlank(boardVO.getBoContent()) 와 같이 쓸 수 있다
	      if(StringUtils.isBlank(boardVO.getBoTitle())) {
	         errors.put("boTitle","제목을 입력해주세요");
	      }
	      if(StringUtils.isBlank(boardVO.getBoContent())) {
	         errors.put("boContent", "내용을입력해주세요");
	      }
	      
	      if(errors.size() > 0) { // 에러가 발생!
	    	 // return 타입이 String이라서 데이터를 넘기려면 ModelAndView 객체 사용x
	    	 // 파라미터로 받은 model은 데이터 전달자
	    	 // 내가 남기고자 하는 데이터를 대신 넘겨주는 역할을 담당한다.(request.setAttribute 대신 사용)
	    	 model.addAttribute("errors", errors);
	    	 model.addAttribute("board", boardVO);
	         goPage = "board/form";
	      }else { // 에러가 발생하지 않고 정상적인 데이터가 넘어옴(정상)
	    	 boardVO.setBoWriter("a001");
	    	 ServiceResult result = boardService.insertBoard(boardVO);
	    	 if(result.equals(ServiceResult.OK)) {
	    		 goPage = "redirect:/board/detail.do?boNo=" + boardVO.getBoNo();
	    	 } else {
	    		 errors.put("msg", "서버 에러! 다시 시도해주세요!");
	    		 model.addAttribute("errors", errors);
	    		 goPage = "board/form";
	    	 }
	      }
	      return goPage;
	   }
}
