package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class ReadStatusManagementEmployeeTest {

	@Test
	public void getters() {
		ReadStatusManagementEmployee readStatusManagementEmployee = new ReadStatusManagementEmployee(RogerFlag.ALREADY_READ, "DuMMY");
		NtsAssert.invokeGetters(readStatusManagementEmployee);
	}
	
	@Test
	public void test_C1(){
		ReadStatusManagementEmployee readStatusManagementEmployee = new ReadStatusManagementEmployee("DUMMY");
		NtsAssert.invokeGetters(readStatusManagementEmployee);
	}

}
