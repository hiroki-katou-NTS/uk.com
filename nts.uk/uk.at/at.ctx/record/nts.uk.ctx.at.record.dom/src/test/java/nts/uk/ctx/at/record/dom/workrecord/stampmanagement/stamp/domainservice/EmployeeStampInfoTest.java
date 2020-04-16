package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author tutk
 *
 */
public class EmployeeStampInfoTest {

	@Test
	public void getters() {
		EmployeeStampInfo employeeStampInfo = DomainServiceHeplper.getEmployeeStampInfoDefault();
		NtsAssert.invokeGetters(employeeStampInfo);
	}
	
	@Test
	public void testEmployeeStampInfo() {
		String employeeId = "employee"; //dummy
		GeneralDate date = GeneralDate.today();//dummy
		List<StampInfoDisp> listStampInfoDisp = Arrays
				.asList(DomainServiceHeplper.getStampInfoDispDefault()); //dummy
		EmployeeStampInfo employeeStampInfo = new EmployeeStampInfo(employeeId, date, listStampInfoDisp);
		NtsAssert.invokeGetters(employeeStampInfo);
	}

}
