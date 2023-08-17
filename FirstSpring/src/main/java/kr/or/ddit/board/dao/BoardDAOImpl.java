package kr.or.ddit.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Repository
public class BoardDAOImpl implements IBoardDAO {
	
	@Inject
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int insertBoard(BoardVO boardVO) {
		return sqlSession.insert("Board.insertBoard", boardVO);
	}

	@Override
	public void incrementHit(int boNo) {
		sqlSession.update("Board.incrementHit", boNo);
	}

	@Override
	public BoardVO selectBoard(int boNo) {
		return sqlSession.selectOne("Board.selectBoard", boNo);
	}

	@Override
	public int updateBoard(BoardVO boardVO) {
		return sqlSession.update("Board.updateBoard", boardVO);
	}

	@Override
	public int deleteBoard(int boNo) {
		return sqlSession.delete("Board.deleteBoard", boNo);
	}

	@Override
	public List<BoardVO> selectBoardList() {
		return sqlSession.selectList("Board.selectBoardList");
	}

	@Override
	public int selectBoardCount(PaginationInfoVO<BoardVO> pagingVO) {
		return sqlSession.selectOne("Board.selectBoardCount", pagingVO);
	}

	@Override
	public List<BoardVO> selectBoardList2(PaginationInfoVO<BoardVO> pagingVO) {
		return sqlSession.selectList("Board.selectBoardList2", pagingVO);
	}
}
