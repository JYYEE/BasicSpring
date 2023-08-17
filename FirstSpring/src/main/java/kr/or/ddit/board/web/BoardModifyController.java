package kr.or.ddit.board.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardModifyController {
	
	@Inject
	private IBoardService boardService;
	
	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String boardUpdateForm(int boNo, Model model) {
		BoardVO boardVO = boardService.selectBoard(boNo);
		model.addAttribute("board", boardVO);
		model.addAttribute("status", "u");  // u : update(수정) 
		// "board/form" 는 등록과 수정이 같은 경로이므로 수정임을 명시하는 상태를 model에 저장
		return "board/form";
	}
	
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String boardUpdate(BoardVO boardVO, Model model) {
		String goPage = "";
		ServiceResult result = boardService.updateBoard(boardVO);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/board/detail.do?boNo=" + boardVO.getBoNo();
		} else {
			model.addAttribute("board", boardVO);
			model.addAttribute("status", "u"); // 수정에 실패했어도 수정과정이므로 수정 상태를 나타내는 플래그를 같이 보냄
			goPage = "board/form";
		}
		return goPage;
	}
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String boardDelete(int boNo, Model model) {
		// boNo는 String 으로 보내지지만 받을 때 받고자 하는 타입으로 적어두면 spring이 알아서 형변환해줌 
		String goPage = "";
		ServiceResult result = boardService.deleteBoard(boNo);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/board/list.do";
		} else {
			goPage = "redirect:/board/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}
