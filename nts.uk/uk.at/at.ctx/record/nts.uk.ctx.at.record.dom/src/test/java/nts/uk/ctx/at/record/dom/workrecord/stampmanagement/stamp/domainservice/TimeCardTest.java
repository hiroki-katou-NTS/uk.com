package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
/**
 * 
 * @author tutk
 *
 */
public class TimeCardTest {

	@Test
	public void getters() {
		TimeCard timeCard = DomainServiceHeplper.getTimeCardDefault();
		NtsAssert.invokeGetters(timeCard);
	}
	
	@Test
	public void testTimeCard() {
		String employeeID = "employeeID";//dummy
		List<AttendanceOneDay> listAttendanceOneDay = Arrays.asList(
				DomainServiceHeplper.getAttendanceOneDayDefault());
		TimeCard timeCard =new TimeCard(employeeID, listAttendanceOneDay);
		NtsAssert.invokeGetters(timeCard);
	}

}
