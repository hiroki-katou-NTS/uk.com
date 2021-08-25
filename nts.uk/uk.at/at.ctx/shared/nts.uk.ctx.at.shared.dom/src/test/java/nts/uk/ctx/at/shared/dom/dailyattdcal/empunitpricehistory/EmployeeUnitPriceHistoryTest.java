/**
 * 
 */
package nts.uk.ctx.at.shared.dom.dailyattdcal.empunitpricehistory;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 * @author laitv
 *
 */
public class EmployeeUnitPriceHistoryTest {
	
	@Test
	public void getters() {
		EmployeeUnitPriceHistory empUnitPriceHis = EmployeeUnitPriceHistoryHelper.getEmployeeUnitPriceHistoryDefault();
		NtsAssert.invokeGetters(empUnitPriceHis);
	}

}
