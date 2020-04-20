package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author phongtq
 *
 */
public class EmployeeRankTest {

	@Test
	public void getter() {
		NtsAssert.invokeGetters(EmployeeRankHelper.Employee.DUMMY);
	}
}
