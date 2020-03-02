package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import static org.assertj.core.api.Assertions.assertThat;

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
							new RankCode("01"), 
							new RankCode("01")));
		});
	}

	@Test
	public void create_rankPriority_success() {

		RankPriority target = new RankPriority("000000000000-0001", // dummy
				new ArrayList<RankCode>(Arrays.asList(
						new RankCode("01"), 
						new RankCode("02"), 
						new RankCode("03"))));
		
		assertThat(target.getListRankCd())
			.extracting(d -> d.v())
			.containsExactly("01","02","03");
		
	}

	@Test
	public void insert_fail_duplicate() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				Arrays.asList(new RankCode("01")));

		NtsAssert.businessException("Msg_1621", () -> {
			target.insert(new RankCode("01"));
		});
	}
	
	@Test
	public void insert_success() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				new ArrayList<RankCode>(
						Arrays.asList(
								new RankCode("01"), 
								new RankCode("02"), 
								new RankCode("03"))));

		target.insert(new RankCode("04"));
		
		assertThat(target.getListRankCd())
			.extracting(d -> d.v())
			.containsExactly("01","02","03", "04");
	}
	
	@Test
	public void delete_fail_empty() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				new ArrayList<RankCode>(Arrays.asList(new RankCode("01"))));
		
		NtsAssert.businessException("Msg_1622", () -> {
			target.delete(new RankCode("01"));
		});
	}

	@Test
	public void delete_success() {

		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				new ArrayList<RankCode>(
						Arrays.asList(
								new RankCode("01"), 
								new RankCode("02"), 
								new RankCode("03"))));

		target.delete(new RankCode("01"));
		
		assertThat(target.getListRankCd())
			.extracting(d -> d.v())
			.containsExactly("02","03");
	}

	@Test
	public void update_fail_duplicate() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		List<RankCode> listRankCdNew = Arrays.asList(
				new RankCode("11"), 
				new RankCode("11"),
				new RankCode("13")); // dummy

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
				new RankCode("15"), 
				new RankCode("06"), 
				new RankCode("23"));
		
		target.update(listRankCdNew);
		
		assertThat(target.getListRankCd())
			.extracting(d -> d.v())
			.containsExactly("15","06","23");
	}

	@Test
	public void getter() {
		RankPriority target = new RankPriority(
				"000000000000-0001", // dummy
				RankHelper.Priority.DUMMY);

		NtsAssert.invokeGetters(target);
	}

}
