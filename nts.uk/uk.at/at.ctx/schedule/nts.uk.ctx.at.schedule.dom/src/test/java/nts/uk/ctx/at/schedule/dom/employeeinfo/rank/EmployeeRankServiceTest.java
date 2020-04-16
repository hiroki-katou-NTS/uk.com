package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankService.Require;

public class EmployeeRankServiceTest {
	@Injectable
	private Require require;

	@Test
	public void insert() {

		new Expectations() {
			{
				require.exists(
						"000000000000000000000000000000000011");// dummy
				result = false;
			}
		};
	
		NtsAssert.businessException("System Er",
				() -> EmployeeRankService.insert(
						require, // dummy
						"000000000000000000000000000000000011", // dummy
						new RankCode("01")));// dummy
	}
	
	@Test
	public void update() {

		new Expectations() {
			{
				require.exists(
						"000000000000000000000000000000000011");// dummy
				result = true;
			}
		};

		NtsAssert.businessException("System Er",
				() -> EmployeeRankService.insert(
						require, // dummy
						"000000000000000000000000000000000011", // dummy
						new RankCode("01")));// dummy
	}
}
