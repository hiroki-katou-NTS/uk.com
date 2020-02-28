package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.InsertRankService.Require;

/**
 * 
 * @author sonnh1
 *
 */
public class InsertRankServiceTest {

	@Injectable
	private Require require;

	@Test
	public void insert_fail_exist() {

		new Expectations() {
			{
				require.checkRankExist(
						"000000000000-0001", // dummy
						new RankCode("01"));// dummy
				result = true;
			}
		};

		NtsAssert.businessException("Msg_3",
				() -> InsertRankService.insert(
						require, // dummy
						"000000000000-0001", // dummy
						new RankCode("01"), // dummy
						new RankSymbol("A")));// dummy
	}
	
	@Test
	public void testInsert_success_1() {

		new Expectations() {
			{
				require.checkRankExist(
						"000000000000-0001", // dummy
						new RankCode("01")); // dummy
				result = false; 

				require.getRankPriority("000000000000-0001"); // dummy
			}
		};

		NtsAssert.atomTask(
				() -> InsertRankService.insert(
						require, // dummy
						"000000000000-0001", // dummy
						new RankCode("01"), // dummy
						new RankSymbol("A")), // dummy
				any -> require.insertRank(any.get(), any.get()));
	}

	@Test
	public void testInsert_success_2() {
		
		List<RankCode> lstRankCode = new ArrayList<>();
		lstRankCode.add(new RankCode("01"));
		lstRankCode.add(new RankCode("02"));
		lstRankCode.add(new RankCode("03"));

		new Expectations() {
			{
				require.checkRankExist(
						"000000000000-0001", // dummy
						new RankCode("10")); // dummy
				result = false; 

				require.getRankPriority("000000000000-0001"); // dummy
				result = Optional.of(new RankPriority(
						"000000000000-0001",
						RankHelper.Priority.DUMMY));
			}
		};

		NtsAssert.atomTask(
				() -> InsertRankService.insert(
						require, // dummy
						"000000000000-0001", // dummy
						new RankCode("10"), // dummy
						new RankSymbol("A")), // dummy
				any -> require.insertRank(any.get(), any.get()));
	}

}
