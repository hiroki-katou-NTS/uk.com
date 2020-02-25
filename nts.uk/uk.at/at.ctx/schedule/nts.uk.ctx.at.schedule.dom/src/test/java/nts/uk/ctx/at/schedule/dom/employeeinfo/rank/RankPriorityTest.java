package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class RankPriorityTest {

	@Test
	public void testRankPriority_Msg_1622() {

		NtsAssert.businessException("Msg_1622", () -> {
			new RankPriority(
					"000000000000-0001", // dummy
					Collections.emptyList());
		});
	}

	@Test
	public void testRankPriority_Msg_1621() {

		NtsAssert.businessException("Msg_1621", () -> {
			new RankPriority(
					"000000000000-0001", // dummy
					Arrays.asList(new RankCode("001"), new RankCode("001"), new RankCode("002")));
		});
	}

	@Test
	public void testRankPriority_success() {

		new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);
	}

	@Test
	public void testInsert_Msg_1621() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		NtsAssert.businessException("Msg_1621", () -> {
			target.insert(new RankCode("001"));
		});
	}
	
	@Test
	public void testInsert_success() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		target.insert(new RankCode("004"));
	}

	@Test
	public void testDelete_Msg_1622() {
		List<RankCode> lstRankCode = new ArrayList<>();
		lstRankCode.add(new RankCode("001"));

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				lstRankCode);
		
		target.delete(new RankCode("001"));

		NtsAssert.businessException("Msg_1622", () -> {
			target.delete(new RankCode("001"));
		});
	}

	@Test
	public void testDelete_success() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		target.delete(new RankCode("001"));
	}

	@Test
	public void testUpdate_Msg_1621() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		List<RankCode> listRankCdNew = Arrays.asList(new RankCode("011"), new RankCode("011"), new RankCode("013"));

		NtsAssert.businessException("Msg_1621", () -> {
			target.update(listRankCdNew);
		});
	}

	@Test
	public void testUpdate_Msg_1622() {
		RankPriority target = new RankPriority("000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		List<RankCode> listRankCdEmpty = Collections.emptyList();

		NtsAssert.businessException("Msg_1622", () -> {
			target.update(listRankCdEmpty);
		});
	}

	@Test
	public void testUpdate_success() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		List<RankCode> listRankCdNew = Arrays.asList(new RankCode("011"), new RankCode("012"), new RankCode("013"));
		target.update(listRankCdNew);
	}

	@Test
	public void getter() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		NtsAssert.invokeGetters(target);
	}

}
