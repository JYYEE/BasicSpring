package kr.or.ddit.main.service;

import java.util.List;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.NoticeVO;

public interface IMainService {

	public List<BoardVO> selectBoardList();

	public List<NoticeVO> selectNoticeList();

	public int selectBoardCount();

	public int selectNoticeCount();

	public List<FreeVO> selectFreeList();

	public int selectFreeCount();

}
