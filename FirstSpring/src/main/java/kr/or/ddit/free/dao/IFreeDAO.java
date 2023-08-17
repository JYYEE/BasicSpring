package kr.or.ddit.free.dao;

import java.util.List;

import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IFreeDAO {

	public int insertFree(FreeVO freeVO);

	public FreeVO selectFree(int boNo);

	public int updateFree(FreeVO freeVO);

	public int deleteFree(int boNo);

	public int selectFreeCount(PaginationInfoVO<FreeVO> pagingVO);

	public List<FreeVO> selectFreeList(PaginationInfoVO<FreeVO> pagingVO);

	public void incrementHit(int boNo);

	public List<Integer> allBono();



}
