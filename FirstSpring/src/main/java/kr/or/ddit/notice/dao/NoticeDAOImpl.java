package kr.or.ddit.notice.dao;

import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Repository
public class NoticeDAOImpl implements INoticeDAO{
	
	@Inject
	private SqlSessionTemplate sqlSession;

	@Override
	public int insertNotice(NoticeVO noticeVO) {
		return sqlSession.insert("Notice.insertNotice", noticeVO);
	}

	@Override
	public NoticeVO selectNotice(int boNo) {
		return sqlSession.selectOne("Notice.selectNotice", boNo);
	}

	@Override
	public int updateNotice(NoticeVO noticeVO) {
		return sqlSession.update("Notice.updateNotice", noticeVO);
	}

	@Override
	public int deleteNotice(int boNo) {
		return sqlSession.delete("Notice.deleteNotice", boNo);
	}

	@Override
	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO) {
		return sqlSession.selectOne("Notice.selectNoticeCount", pagingVO);
	}

	@Override
	public List<NoticeVO> selectNoticeList(PaginationInfoVO<NoticeVO> pagingVO) {
		return sqlSession.selectList("Notice.selectNoticeList", pagingVO);
	}

	@Override
	public void incrementHit(int boNo) {
		sqlSession.update("Notice.incrementHit", boNo);
	}

	@Override
	public List<Integer> allBono() {
		return sqlSession.selectList("Notice.allBono");
	}
}
