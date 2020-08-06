package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;


import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService.Require;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class DateAndTimePeriodTest {

	@Injectable
	private Require require;
	
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
	
	@Test
	public void test_WorkingConditionItem_null() {
		DateAndTimePeriod dateAndTimePeriod = DomainServiceHeplper.getDateAndTimePeriodDefault();
		
		new Expectations() {
			{
				require.findWorkConditionByEmployee("DUMMY",
						GeneralDate.today());
			}
		};
		NtsAssert.businessException("Msg_430", () -> dateAndTimePeriod.calOneDayRange(require,"DUMMY"));
	}
	
	@Test
	public void test_workTimeCode_null() {
		DateAndTimePeriod dateAndTimePeriod = DomainServiceHeplper.getDateAndTimePeriodDefault();
		
		new Expectations() {
			{
				require.findWorkConditionByEmployee("DUMMY",
						GeneralDate.today());
//				result = new WorkingConditionItem(memento);
			}
		};
//		NtsAssert.businessException("Msg_430", () -> dateAndTimePeriod.calOneDayRange(require,"DUMMY"));
	}

}
