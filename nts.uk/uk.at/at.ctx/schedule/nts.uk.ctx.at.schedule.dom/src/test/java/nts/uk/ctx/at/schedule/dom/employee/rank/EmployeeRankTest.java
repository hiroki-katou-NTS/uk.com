package nts.uk.ctx.at.schedule.dom.employee.rank;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author phongtq
 *
 */
@RunWith(JMockit.class)
public class EmployeeRankTest {

	@Test
	public void getter() {
		NtsAssert.invokeGetters(EmployeeRankHelper.Employee.DUMMY);
	}
}
