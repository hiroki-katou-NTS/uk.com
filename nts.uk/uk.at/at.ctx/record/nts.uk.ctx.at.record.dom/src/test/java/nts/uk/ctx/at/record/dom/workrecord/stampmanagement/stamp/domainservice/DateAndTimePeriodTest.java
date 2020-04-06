package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author tutk
 *
 */
public class DateAndTimePeriodTest {

	@Test
	public void getters() {
		DateAndTimePeriod dateAndTimePeriod = DomainServiceHeplper.getDateAndTimePeriodDefault();
		NtsAssert.invokeGetters(dateAndTimePeriod);
	}
	
	@Test
	public void testDateAndTimePeriod() {
		GeneralDateTime statDateTime = GeneralDateTime.now().addDays(1);
		GeneralDateTime endDateTime = GeneralDateTime.now().addDays(12);
		DateAndTimePeriod dateAndTimePeriod = new DateAndTimePeriod(statDateTime, endDateTime);
		NtsAssert.invokeGetters(dateAndTimePeriod);
	}

}
