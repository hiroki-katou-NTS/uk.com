package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
		
		assertThat(infor.getEmpId()).isEqualTo("1");
		assertThat(infor.getRankCode()).isNotPresent();
		assertThat(infor.getRankSymbol()).isNotPresent();
		
	}
	
	@Test
	public void testCreateRank() {
		EmpRankInfor infor = EmpRankInfor.create("1", new RankCode("01"), new RankSymbol("2"));
		assertThat(infor)
		.extracting(d->d.getEmpId(),d->d.getRankCode(), d->d.getRankSymbol())
		.containsExactly("1", Optional.ofNullable(new RankCode("01")), Optional.ofNullable(new RankSymbol("2")));
		
	}
}
