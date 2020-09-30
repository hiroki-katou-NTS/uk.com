package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;

public class ShiftTableDateSettingTest {
	
	@Test
	public void getters() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.defaultCreate();
		NtsAssert.invokeGetters(target);  
	}
	
	@Test
	public void TestGetShiftPeriodUnit() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.defaultCreate();
		
		assertThat(target.getShiftPeriodUnit()).isEqualTo(ShiftPeriodUnit.MONTHLY);
	}
	
	@Test
	public void testIsOverDeadline_false() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 6);
		
		// today = 2020/9/10
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 9, 10, 0, 0, 0);
		
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 1));

		assertThat(isOverDeadline).isFalse();
		
	}
	
	@Test
	public void testIsOverDeadline_true() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 6);
		
		// today = 2020/9/11
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 9, 11, 0, 0, 0);
		
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 1));

		assertThat(isOverDeadline).isTrue();
	}
	
	@Test
	public void testIsOverHolidayMaxdays_false() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 3);
		
		List<WorkExpectationOfOneDay> expectations = Arrays.asList(
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 1), AssignmentMethod.SHIFT),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 2), AssignmentMethod.TIME_ZONE),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 3), AssignmentMethod.HOLIDAY),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 4), AssignmentMethod.HOLIDAY),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 5), AssignmentMethod.HOLIDAY)
				);
		
		boolean isOverHolidayMaxDays = target.isOverHolidayMaxDays(expectations);
		
		assertThat(isOverHolidayMaxDays).isFalse();
	}
	
	@Test
	public void testIsOverHolidayMaxdays_true() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 3);
		
		List<WorkExpectationOfOneDay> expectations = Arrays.asList(
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 1), AssignmentMethod.SHIFT),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 2), AssignmentMethod.TIME_ZONE),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 3), AssignmentMethod.HOLIDAY),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 4), AssignmentMethod.HOLIDAY),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 5), AssignmentMethod.HOLIDAY),
				ShiftTableDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 6), AssignmentMethod.HOLIDAY)
				);
		
		boolean isOverHolidayMaxDays = target.isOverHolidayMaxDays(expectations);
		
		assertThat(isOverHolidayMaxDays).isTrue();
	}
	
	
	/**
	 * targetDate == closureDate
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case1() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 3);
		
		ShiftTableRuleInfo ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 9, 15));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 10));
		assertThat(ruleInfo.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
		
	}
	
	/**
	 * targetDate == deadline
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case3() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 3);
		
		ShiftTableRuleInfo ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 10));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 10));
		assertThat(ruleInfo.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
	}
	
	/**
	 *  deadline < targetDate < closureDate
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case4() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 3);
		
		ShiftTableRuleInfo ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 11));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 11, 10));
		assertThat(ruleInfo.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 11, 16), GeneralDate.ymd(2020, 12, 15)));
	}
	
	@Test
	public void testGetPeriodWhichIncludeExpectingDate() {
		
		ShiftTableDateSetting target = ShiftTableDateSettingHelper.createWithParam(15, 10, 3);
		
		DatePeriod period = target.getPeriodWhichIncludeExpectingDate(GeneralDate.ymd(2020, 10, 1));
		
		assertThat(period).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 9, 16), GeneralDate.ymd(2020, 10, 15)));
	} 

}
