package kr.hkit.exam09.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kr.hkit.exam09.Query;

class ConnectionTest {

	@Test
	void test() {
		//null인지 아닌지 체크하는 문장
		assertNotNull(Query.getConn());
	}

}
