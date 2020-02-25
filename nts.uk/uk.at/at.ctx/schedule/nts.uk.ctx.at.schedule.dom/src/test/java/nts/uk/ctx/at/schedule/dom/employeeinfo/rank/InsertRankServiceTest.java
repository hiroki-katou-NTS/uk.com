package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

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
						new RankCode("001"));// dummy
				result = true;
			}
		};

		NtsAssert.businessException("Msg_3",
				() -> InsertRankService.insert(
						require, // dummy
						"000000000000-0001", // dummy
						new RankCode("001"), // dummy
						new RankSymbol("A")));// dummy
	}

	@Test
	public void testInsert_success() {

		new Expectations() {
			{
				require.checkRankExist(
						"000000000000-0001", // dummy
						new RankCode("001")); // dummy
				result = false; 

				require.getRankPriority("000000000000-0001"); // dummy
			}
		};

		NtsAssert.atomTask(
				() -> InsertRankService.insert(
						require, // dummy
						"000000000000-0001", // dummy
						new RankCode("001"), // dummy
						new RankSymbol("A")), // dummy
				any -> require.insertRank(any.get(), any.get()));
	}

}
