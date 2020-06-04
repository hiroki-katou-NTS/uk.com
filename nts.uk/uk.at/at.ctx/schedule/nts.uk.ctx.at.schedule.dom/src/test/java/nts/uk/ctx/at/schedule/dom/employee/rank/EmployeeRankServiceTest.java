package nts.uk.ctx.at.schedule.dom.employee.rank;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankService.Require;
/**
 * 
 * @author phongtq
 *
 */
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
	
		NtsAssert.atomTask(
				() -> EmployeeRankService.insert(
						require, // dummy
						"000000000000000000000000000000000011", // dummy
						new RankCode("01")),// dummy
				any -> require.insert(any.get()));
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

		NtsAssert.atomTask(
				() -> EmployeeRankService.insert(
						require, // dummy
						"000000000000000000000000000000000011", // dummy
						new RankCode("01")),// dummy
				any -> require.update(any.get()));
	}
}
