package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import nts.arc.error.BusinessException;
import nts.arc.testing.exception.BusinessExceptionAssert;

public class RankPriorityTest {

	@Test
	public void testRankPriority_Msg_1622() {
		BusinessExceptionAssert.id("Msg_1622", () -> RankPriorityHelper.getListRankCodeEmpty());
	}

	@Test
	public void testRankPriority_Msg_1621() {
		BusinessExceptionAssert.id("Msg_1621", () -> RankPriorityHelper.getListRankCodeNotDistinct());
	}

	@Test
	public void testInsert() {
		RankPriority rankPriority = RankPriorityHelper.getRankPriorityBasic();
		assertThatThrownBy(() -> rankPriority.insert(new RankCode("001"))).as("Msg_1621")
				.isInstanceOf(BusinessException.class);
	}

	@Test
	public void testDelete() {
		RankPriority rankPriority = RankPriorityHelper.getListRankCode1Item();
		rankPriority.delete(new RankCode("001"));
		assertThatThrownBy(() -> rankPriority.delete(new RankCode("001"))).as("Msg_1622")
				.isInstanceOf(BusinessException.class);
	}

	@Test
	public void testUpdate_Msg_1621() {
		RankPriority rankPriority = RankPriorityHelper.getRankPriorityBasic();
		List<RankCode> listRankCdNew = Arrays.asList(new RankCode("011"), new RankCode("011"), new RankCode("013"),
				new RankCode("014"));
		BusinessExceptionAssert.id("Msg_1621", () -> rankPriority.update(listRankCdNew));
	}

	@Test
	public void testUpdate_Msg_1622() {
		RankPriority rankPriority = RankPriorityHelper.getRankPriorityBasic();
		List<RankCode> listRankCdNew = Collections.emptyList();
		BusinessExceptionAssert.id("Msg_1622", () -> rankPriority.update(listRankCdNew));
	}

}
