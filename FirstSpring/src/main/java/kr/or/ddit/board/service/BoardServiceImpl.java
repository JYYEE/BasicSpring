package kr.or.ddit.board.service;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.board.dao.IBoardDAO;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Service
public class BoardServiceImpl implements IBoardService {
	
	@Inject
	private IBoardDAO boardDao;
	
	@Override
	public ServiceResult insertBoard(BoardVO boardVO) {
		ServiceResult result = null;
		// 성공하면 1, 실패하면 0
		// boardVO 안에 boNo가 셋팅되어 들어올 예정
		// selectKey 설정에 의해서 최신 글 번호가 담겨서 들어온다. 
		int status = boardDao.insertBoard(boardVO);
		if(status > 0) { // 등록 성공
			result = ServiceResult.OK;
		} else {		// 등록 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public BoardVO selectBoard(int boNo) {
		// 상세보기에서 조회수를 같이 처리
		boardDao.incrementHit(boNo); // 조회수 처리
		return boardDao.selectBoard(boNo);
	}

	@Override
	public ServiceResult updateBoard(BoardVO boardVO) {
		ServiceResult result = null;
		int status = boardDao.updateBoard(boardVO);
		if(status>0) {	// 수정 성공
			result = ServiceResult.OK;
		} else {		// 수정 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public ServiceResult deleteBoard(int boNo) {
		ServiceResult result = null;
		int status = boardDao.deleteBoard(boNo);
		if(status>0) {	// 삭제 성공
			result = ServiceResult.OK;
		} else {		// 삭제 실패
			result = ServiceResult.FAILED;
		}
		return result;
	}

	@Override
	public List<BoardVO> selectBoardList() {
		return boardDao.selectBoardList();
	}

	@Override
	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO) {
		return boardDao.selectBoardCount(pagingVO);
	}

	@Override
	public List<BoardVO> selectBoardList2(PaginationInfoVO<BoardVO> pagingVO) {
		return boardDao.selectBoardList2(pagingVO);
	}
}
