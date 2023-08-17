package kr.or.ddit.main.dao;

import java.util.List;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.NoticeVO;

public interface IMainDAO {

	public List<BoardVO> selectBoardList();

	public List<NoticeVO> selectNoticeList();

	public int selectBoardCount();

	public int selectNoticeCount();

	public List<FreeVO> selectFreeList();

	public int selectFreeCount();

}
