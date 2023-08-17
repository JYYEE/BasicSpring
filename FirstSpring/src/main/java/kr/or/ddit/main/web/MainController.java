package kr.or.ddit.main.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.main.service.IMainService;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.NoticeVO;

@Controller
public class MainController {
	
	@Inject
	private IMainService mainService;
	
	// 경로가 "/" 또는 "main.do" 일 때 이동
	@RequestMapping(value= {"/", "main.do"}, method = RequestMethod.GET)
	public String main(Model model) {
		// 일반 게시판 게시글 총 5개의 정보를 가져와서 메인화면에 출력
		List<BoardVO> boardList = mainService.selectBoardList();
		List<NoticeVO> noticeList = mainService.selectNoticeList();
		List<FreeVO> freeList = mainService.selectFreeList();
		
		int boardCount = mainService.selectBoardCount();
		int noticeCount = mainService.selectNoticeCount();
		int freeCount = mainService.selectFreeCount();
		
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("freeList", freeList);
		
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("noticeCount", noticeCount);
		model.addAttribute("freeCount", freeCount);
		return "main";
	}
}
