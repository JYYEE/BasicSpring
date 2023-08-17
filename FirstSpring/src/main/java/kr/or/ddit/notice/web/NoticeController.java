package kr.or.ddit.notice.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.notice.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Inject
	private INoticeService noticeService;
	
	// form.jsp로 이동
	@RequestMapping(value = "/form.do", method = RequestMethod.GET)
	public String noticeForm() {
		return "notice/form";
	}
	
	// 게시글 등록
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public String noticeInsert(NoticeVO noticeVO, Model model) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		
		if(StringUtils.isBlank(noticeVO.getBoTitle())) {
			errors.put("boTitle", "제목을 입력해주세요.");
		}
		
		if(StringUtils.isBlank(noticeVO.getBoContent())) {
			errors.put("boContent", "내용을 입력해주세요.");
		}
		
		if(errors.size() >0) { //에러 발생
			model.addAttribute("errors", errors);
			model.addAttribute("notice", noticeVO);
			goPage = "notice/form";
		} else { // 성공
			noticeVO.setBoWriter("a001");
			ServiceResult result = noticeService.insertNotice(noticeVO);
			if(result.equals(ServiceResult.OK)) { // 정상 등록
				goPage = "redirect:/notice/detail.do?boNo="+noticeVO.getBoNo();
			} else {
				errors.put("msg", "등록 실패! 다시 시도해주세요!");
				model.addAttribute("errors", errors);
				goPage = "notice/form";
			}
		}
		return goPage;
	}
	
	// 게시글 상세보기
	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public String noticeDetail(int boNo, Model model) {
		List<Integer> boNos = noticeService.allBono();
		
		boolean errorCheck = false;
		if(!boNos.contains(boNo)) {
			errorCheck = true;
		}
		
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		String goPage = "";
		if(noticeVO != null && !errorCheck) {
			model.addAttribute("notice", noticeVO);
			goPage = "notice/view";
		} else if(errorCheck) {
			model.addAttribute("error", "error");
			goPage = "error";
		} else {
			goPage = "redirect:/notice/form.do";
		}
		return goPage;
	}
	
	// 수정 페이지로 이동
	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String noticeUpdateForm(int boNo, Model model) {
		List<Integer> boNos = noticeService.allBono();
		boolean errorCheck = false;
		if(!boNos.contains(boNo)) {
			errorCheck = true;
		}
		
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		String goPage = "";
		if(noticeVO != null && !errorCheck) {
			model.addAttribute("notice", noticeVO);
			model.addAttribute("status", "u"); // 업데이트 상태
			goPage = "notice/form";
		} else if(errorCheck) {
			model.addAttribute("error", "error");
			goPage = "error";
		} else {
			goPage = "redirect:/notice/form.do";
		}
		return goPage;
	}
	
	// 게시글 수정
	@RequestMapping(value = "/update.do", method = RequestMethod.POST)
	public String noticeUpdate(NoticeVO noticeVO, Model model) {
		String goPage = "";
		ServiceResult result = noticeService.updateNotice(noticeVO);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/notice/detail.do?boNo="+noticeVO.getBoNo();
		} else {
			model.addAttribute("notice", noticeVO);
			model.addAttribute("status", "u");
			goPage = "notice/form";
		}
		return goPage;
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String noticeDelete(int boNo, Model model) {
		String goPage="";
		ServiceResult result = noticeService.deleteNotice(boNo);
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/notice/list.do";
		} else {
			goPage = "redirect:/notice/detail.do?boNo="+ boNo;
		}
		return goPage;
	}
	
	// 리스트 페이지로 이동
	@RequestMapping(value = "/list.do")
	public String noticeList(
			@RequestParam(name="page", required=false, defaultValue="1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord,
			Model model) {
		PaginationInfoVO<NoticeVO> pagingVO = new PaginationInfoVO<NoticeVO>();
		if(StringUtils.isNotBlank(searchWord)) { // 검색 존재
			pagingVO.setSearchType(searchType);
			pagingVO.setSearchWord(searchWord);
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchWord", searchWord);
		}
		pagingVO.setCurrentPage(currentPage);
		
		int totalRecord = noticeService.selectNoticeCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<NoticeVO> noticeList = noticeService.selectNoticeList(pagingVO);
		pagingVO.setDataList(noticeList);
		model.addAttribute("pagingVO", pagingVO);
		return "notice/list";
	}
	
}
