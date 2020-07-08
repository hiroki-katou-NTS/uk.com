package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.referstoinformation.EmployeeVeinInformation;

/**
 * 
 * @author chungnt
 *
 */

public class EmployeeVeinInformationTest {

	@Test
	public void getters() {
		EmployeeVeinInformation employeeVeinInformation = ReferstoinformationHelper.getEmployeeVeinInformationDefault();
		NtsAssert.invokeGetters(employeeVeinInformation);
	}
 
}
