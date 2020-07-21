package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class EmpRankInforTest {
	@Test
	public void get() {
		NtsAssert.invokeGetters(RankHelper.EmployeeRankInfor.get());
	}
	
	@Test
	public void testMakeWithoutRank() {
		EmpRankInfor infor = EmpRankInfor.makeWithoutRank("1");
		
		assertEquals(infor.getEmpId(), "1");
		assertNull(infor.getRankCode());
		assertNull(infor.getRankSymbol());
		
	}
	
	@Test
	public void testCreateRank() {
		EmpRankInfor infor = EmpRankInfor.create("1", new RankCode("01"), new RankSymbol("2"));
		
		assertEquals(infor.getEmpId(), "1");
		assertEquals(infor.getRankCode(), new RankCode("01"));
		assertEquals(infor.getRankSymbol(), new RankSymbol("2"));
		
	}
}
