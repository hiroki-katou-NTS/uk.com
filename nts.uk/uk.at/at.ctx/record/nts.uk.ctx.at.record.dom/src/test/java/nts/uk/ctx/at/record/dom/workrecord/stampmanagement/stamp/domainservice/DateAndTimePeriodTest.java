package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService.Require;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDayScheduleSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class DateAndTimePeriodTest implements SingleDayScheduleGetMemento{

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
		NtsAssert.businessException("Msg_430", 
				() -> dateAndTimePeriod.calOneDayRange(require,"DUMMY"));
	}
	
	@Test
	public void test_workTimeCode_NotNull() {
		DateAndTimePeriod dateAndTimePeriod = DomainServiceHeplper.getDateAndTimePeriodDefault();
		SingleDaySchedule single = new SingleDaySchedule("workTypeCode", null, Optional.of("workTimeCode"));
		PersonalWorkCategory perWorkCat = new PersonalWorkCategory(single);
		WorkingConditionItem item = new WorkingConditionItem("", null, null, 
					perWorkCat, NotUseAtr.USE, NotUseAtr.USE, 
					"employeeId", NotUseAtr.USE, null, null, 
					null, null, 1, null, null);
		
		new Expectations() {
			{
				require.findWorkConditionByEmployee("DUMMY",
						GeneralDate.today());
				result = Optional.of(item);
			}
		};
		
		NtsAssert.businessException("Msg_1142", 
				() -> dateAndTimePeriod.calOneDayRange(require, "DUMMY"));
	}

	@Test
	public void test_workTimeCode_Null() {
		DateAndTimePeriod dateAndTimePeriod = DomainServiceHeplper.getDateAndTimePeriodDefault();
		SingleDaySchedule single = new SingleDaySchedule(this);
		PersonalWorkCategory perWorkCat = new PersonalWorkCategory(single);
		WorkingConditionItem item = new WorkingConditionItem("", null, null, 
					perWorkCat, NotUseAtr.USE, NotUseAtr.USE, 
					"employeeId", NotUseAtr.USE, null, null, 
					null, null, 1, null, null);
		
		new Expectations() {
			{
				require.findWorkConditionByEmployee("DUMMY",
						GeneralDate.today());
				result = Optional.of(item);
			}
		};
		
		NtsAssert.businessException("Msg_1142", 
				() -> dateAndTimePeriod.calOneDayRange(require, "DUMMY"));
	}
	

	@Test
	public void test_optPredetemineTimeSetting() {
		DateAndTimePeriod dateAndTimePeriod = DomainServiceHeplper.getDateAndTimePeriodDefault();
		Optional<String> workTimeCode = Optional.of("workTimeCode");
		SingleDaySchedule single = new SingleDaySchedule("workTypeCode", null, workTimeCode);
		PersonalWorkCategory perWorkCat = new PersonalWorkCategory(single);
		WorkingConditionItem item = new WorkingConditionItem("", null, null, 
					perWorkCat, NotUseAtr.USE, NotUseAtr.USE, 
					"employeeId", NotUseAtr.USE, null, null, 
					null, null, 1, null, null);
		
		Optional<PredetemineTimeSetting> pre = Optional.of(new PredetemineTimeSetting());
		pre.get().setStartDateClock(GeneralDateTime.now().clockHourMinute().v() + 1);
		
		new Expectations() {
			{
				require.findWorkConditionByEmployee("DUMMY", GeneralDate.today());
				result = Optional.of(item);
				
				require.findByWorkTimeCode(workTimeCode.get());
				result = pre;
			}
		};
		DateAndTimePeriod testResult = dateAndTimePeriod.calOneDayRange(require, "DUMMY");
		DateAndTimePeriod resultValue = new DateAndTimePeriod(GeneralDateTime.now(), GeneralDateTime.now());
		assertThat(testResult.getStatDateTime().minutes()).isEqualTo(resultValue.getStatDateTime().minutes() + 1);
		assertThat(testResult.getEndDateTime().minutes()).isEqualTo(resultValue.getEndDateTime().minutes() + 1);
	}

	@Test
	public void test_optPredetemineTimeSettingSmall() {
		DateAndTimePeriod dateAndTimePeriod = DomainServiceHeplper.getDateAndTimePeriodDefault();
		Optional<String> workTimeCode = Optional.of("workTimeCode");
		SingleDaySchedule single = new SingleDaySchedule("workTypeCode", null, workTimeCode);
		PersonalWorkCategory perWorkCat = new PersonalWorkCategory(single);
		WorkingConditionItem item = new WorkingConditionItem("", null, null, 
					perWorkCat, NotUseAtr.USE, NotUseAtr.USE, 
					"employeeId", NotUseAtr.USE, null, null, 
					null, null, 1, null, null);
		
		Optional<PredetemineTimeSetting> pre = Optional.of(new PredetemineTimeSetting());
		pre.get().setStartDateClock(GeneralDateTime.now().clockHourMinute().v());
		
		new Expectations() {
			{
				require.findWorkConditionByEmployee("DUMMY", GeneralDate.today());
				result = Optional.of(item);
				
				require.findByWorkTimeCode(workTimeCode.get());
				result = pre;
			}
		};
		DateAndTimePeriod testResult = dateAndTimePeriod.calOneDayRange(require, "DUMMY");
		DateAndTimePeriod resultValue = new DateAndTimePeriod(GeneralDateTime.now(), GeneralDateTime.now());
		assertThat(testResult.getStatDateTime().minutes()).isEqualTo(resultValue.getStatDateTime().minutes());
		assertThat(testResult.getEndDateTime().minutes()).isEqualTo(resultValue.getEndDateTime().minutes());
	}

	@Override
	public Optional<WorkTypeCode> getWorkTypeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TimeZone> getWorkingHours() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkTimeCode> getWorkTimeCode() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
