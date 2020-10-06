package kr.hkit.exam09.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.hkit.exam09.BoardVO;
import kr.hkit.exam09.Query;

class QueryTest {

	@BeforeAll
	static void start() {
		Query.createTable();
	}
	
	@AfterAll
	static void end() {
		Query.dropTable();
	}
	@BeforeEach
	void before() {
		//0번이 들어가면 전체 삭제
		Query.boardDelete(0);
		
		BoardVO bv1 = new BoardVO();
		bv1.setBtitle("타이틀1");
		bv1.setBcontent("내용1");
		Query.boardInsert(bv1);
		
		
		BoardVO bv2 = new BoardVO();
		bv2.setBtitle("타이틀2");
		bv2.setBcontent("내용2");
		Query.boardInsert(bv2);
	}
	
	@Test
	void TestA() {
		List<BoardVO> list = Query.getAllBoardList();
		Assert.assertEquals(2, list.size());
		
		BoardVO vo1 = list.get(0);
		Assert.assertEquals("타이틀1", vo1.getBtitle());
		Assert.assertEquals("내용1", vo1.getBcontent());
		
		BoardVO vo2 = list.get(1);
		Assert.assertEquals("타이틀2", vo2.getBtitle());
		Assert.assertEquals("내용2", vo2.getBcontent());
	}
	
	@Test
	void TestB() {
		List<BoardVO> list = Query.getAllBoardList();
		
		//첫번째 행 삭제
		BoardVO vo = list.get(0);
		Query.boardDelete(vo.getBid());
		BoardVO vo1 = Query.getBoardDetail(vo.getBid());
		list = Query.getAllBoardList();
		//삭제 여부 확인
		Assert.assertNull(vo1.getBtitle());
		Assert.assertNull(vo1.getBcontent());
		Assert.assertEquals(0, vo1.getBid());
		
		Assert.assertEquals(1, Query.getAllBoardList().size());
		
		
		//두번째 행 삭제(첫번째 삭제되서 밀려올라옴)
		vo = list.get(0);
		Query.boardDelete(vo.getBid());
		BoardVO vo2 = Query.getBoardDetail(vo.getBid());
		list = Query.getAllBoardList();
		
		
		Assert.assertNull(vo2.getBtitle());
		Assert.assertNull(vo2.getBcontent());
		Assert.assertEquals(0, vo2.getBid());
		
		Assert.assertEquals(0, Query.getAllBoardList().size());
	}
	
	@Test
	void TestC() {
		List<BoardVO> list = Query.getAllBoardList();
		BoardVO c = new BoardVO();
		//임의의 값 작성
		c.setBcontent("내용1");
		c.setBtitle("제목1");
		
		int bid = list.get(0).getBid(); // 1
		c.setBid(bid);
		
		//bid =1로 설정
		Query.boardUpdate(c);
		BoardVO vo1result = Query.getBoardDetail(bid);
		Assert.assertEquals(vo1result.getBcontent(),c.getBcontent());
		Assert.assertEquals(vo1result.getBtitle(),c.getBtitle());
		
		
		BoardVO d = new BoardVO();
		d.setBcontent("내용2");
		d.setBtitle("제목2");
		
		bid = list.get(1).getBid(); // 2
		d.setBid(bid);
		
		Query.boardUpdate(d);
		BoardVO vo2result = Query.getBoardDetail(bid);
		Assert.assertEquals(vo2result.getBcontent(),d.getBcontent());
		Assert.assertEquals(vo2result.getBtitle(),d.getBtitle());
	}
	
}

