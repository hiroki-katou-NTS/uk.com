package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author sonnh1
 *
 */
public class RankPriorityTest {

	@Test
	public void create_rankPriority_empty() {

		NtsAssert.businessException("Msg_1622", () -> {
			new RankPriority(
					"000000000000-0001", // dummy
					Collections.emptyList());
		});
	}

	@Test
	public void create_rankPriority_dupicate() {

		NtsAssert.businessException("Msg_1621", () -> {
			new RankPriority(
					"000000000000-0001", // dummy
					Arrays.asList(
							new RankCode("001"), 
							new RankCode("001")));
		});
	}

	@Test
	public void create_rankPriority_success() {

		new RankPriority("000000000000-0001", // dummy
				new ArrayList<RankCode>(Arrays.asList(
						new RankCode("001"), 
						new RankCode("002"), 
						new RankCode("003"))));
	}

	@Test
	public void insert_fail_duplicate() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				Arrays.asList(new RankCode("001")));

		NtsAssert.businessException("Msg_1621", () -> {
			target.insert(new RankCode("001"));
		});
	}
	
	@Test
	public void insert_success() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		target.insert(new RankCode("004"));
	}
	
	/**
	 * Can't create RankPriority(cid = '001', listRankCd = EMPTY_LIST)
	 * So need delete 2 times to Msg_1622 appear
	 */
	@Test
	public void delete_fail_empty() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				new ArrayList<RankCode>(Arrays.asList(new RankCode("001"))));
		
		target.delete(new RankCode("001"));

		NtsAssert.businessException("Msg_1622", () -> {
			target.delete(new RankCode("001"));
		});
	}

	@Test
	public void delete_success() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		target.delete(new RankCode("001"));
	}

	@Test
	public void update_fail_duplicate() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		List<RankCode> listRankCdNew = Arrays.asList(
				new RankCode("011"), 
				new RankCode("011"),
				new RankCode("013")); // dummy

		NtsAssert.businessException("Msg_1621", () -> {
			target.update(listRankCdNew); 
		});
	}

	@Test
	public void update_fail_empty() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		List<RankCode> listRankCdEmpty = Collections.emptyList();

		NtsAssert.businessException("Msg_1622", () -> {
			target.update(listRankCdEmpty);
		});
	}

	@Test
	public void update_success() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		List<RankCode> listRankCdNew = Arrays.asList(
				new RankCode("011"), 
				new RankCode("012"), 
				new RankCode("013"));
		
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
