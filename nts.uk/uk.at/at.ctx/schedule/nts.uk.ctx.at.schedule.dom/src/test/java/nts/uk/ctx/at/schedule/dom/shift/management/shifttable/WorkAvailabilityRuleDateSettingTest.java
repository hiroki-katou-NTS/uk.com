package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;

public class WorkAvailabilityRuleDateSettingTest {
	
	@Test
	public void getters() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.defaultCreate();
		NtsAssert.invokeGetters(target);  
	}
	
	@Test
	public void TestGetShiftPeriodUnit() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.defaultCreate();
		
		assertThat(target.getShiftPeriodUnit()).isEqualTo(WorkAvailabilityPeriodUnit.MONTHLY);
	}
	
	@Test
	public void testIsOverDeadline_false() {
		
		/**
		 * 締め日: 15日、締切日: 10日
		 */
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 6);
		
		/**
		 *  today = 2020/9/10
		 */
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 9, 10, 0, 0, 0);
		
		/**
		 * 希望日: 2020/10/1 →　希望日の締切日: 2020/9/10
		 */
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 1));

		assertThat(isOverDeadline).isFalse();
		
	}
	
	@Test
	public void testIsOverDeadline_true() {
		
		/**
		 * 締め日: 15日、締切日: 10日
		 */
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 6);
		
		/**
		 *  today = 2020/9/11
		 */
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 9, 11, 0, 0, 0);
		
		/**
		 * 希望日: 2020/10/1 →　希望日の締切日: 2020/9/10
		 */
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 1));

		assertThat(isOverDeadline).isTrue();
	}
	
	@Test
	public void testIsOverHolidayMaxdays_false() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 3);
		
		List<WorkAvailabilityOfOneDay> expectations = Arrays.asList(
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 1), AssignmentMethod.SHIFT),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 2), AssignmentMethod.TIME_ZONE),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 3), AssignmentMethod.HOLIDAY),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 4), AssignmentMethod.HOLIDAY),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 5), AssignmentMethod.HOLIDAY)
				);
		
		boolean isOverHolidayMaxDays = target.isOverHolidayMaxDays(expectations);
		
		assertThat(isOverHolidayMaxDays).isFalse();
	}
	
	@Test
	public void testIsOverHolidayMaxdays_true() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 3);
		
		List<WorkAvailabilityOfOneDay> expectations = Arrays.asList(
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 1), AssignmentMethod.SHIFT),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 2), AssignmentMethod.TIME_ZONE),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 3), AssignmentMethod.HOLIDAY),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 4), AssignmentMethod.HOLIDAY),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 5), AssignmentMethod.HOLIDAY),
				WorkAvailabilityRuleDateSettingHelper.createExpectation(GeneralDate.ymd(2020, 10, 6), AssignmentMethod.HOLIDAY)
				);
		
		boolean isOverHolidayMaxDays = target.isOverHolidayMaxDays(expectations);
		
		assertThat(isOverHolidayMaxDays).isTrue();
	}
	
	
	/**
	 * targetDate < deadLine
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case1() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 3);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 1));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 10));
		assertThat(ruleInfo.getPeriod().start()).isEqualTo( GeneralDate.ymd(2020, 10, 16) );
		assertThat(ruleInfo.getPeriod().end()).isEqualTo( GeneralDate.ymd(2020, 11, 15) );
		
	}
	
	/**
	 * targetDate == deadline
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case3() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 3);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 10));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 10));
		assertThat(ruleInfo.getPeriod().start()).isEqualTo( GeneralDate.ymd(2020, 10, 16) );
		assertThat(ruleInfo.getPeriod().end()).isEqualTo( GeneralDate.ymd(2020, 11, 15) );
	}
	
	/**
	 *  deadline < targetDate < closureDate
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case4() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 3);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 11));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 11, 10));
		assertThat(ruleInfo.getPeriod().start()).isEqualTo( GeneralDate.ymd(2020, 11, 16) );
		assertThat(ruleInfo.getPeriod().end()).isEqualTo( GeneralDate.ymd(2020, 12, 15) );
	}
	
	/**
	 * 締め日：末日、締切日：末日
	 * 基準日の月の期間 (2020/01)　> 翌月の期間 (2020/02)　
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case5() {
		
		WorkAvailabilityRuleDateSetting target = new WorkAvailabilityRuleDateSetting(
				new OneMonth(DateInMonth.lastDay()),
				DateInMonth.lastDay(), 
				new HolidayAvailabilityMaxdays(0));
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 1, 15));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 1, 31));
		assertThat(ruleInfo.getPeriod().start()).isEqualTo( GeneralDate.ymd(2020, 2, 1) );
		assertThat(ruleInfo.getPeriod().end()).isEqualTo( GeneralDate.ymd(2020, 2, 29) );
	}
	
	/**
	 * 締め日：末日、締切日：末日
	 * 基準日の月の期間 (2020/02)　< 翌月の期間 (2020/03)　
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case6() {
		
		WorkAvailabilityRuleDateSetting target = new WorkAvailabilityRuleDateSetting(
				new OneMonth(DateInMonth.lastDay()),
				DateInMonth.lastDay(), 
				new HolidayAvailabilityMaxdays(0));
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 2, 15));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 2, 29));
		assertThat(ruleInfo.getPeriod().start()).isEqualTo( GeneralDate.ymd(2020, 3, 1) );
		assertThat(ruleInfo.getPeriod().end()).isEqualTo( GeneralDate.ymd(2020, 3, 31) );
	}
	
	
	@Test
	public void testGetPeriodWhichIncludeExpectingDate() {
		
		WorkAvailabilityRuleDateSetting target = WorkAvailabilityRuleDateSettingHelper.createWithParam(15, 10, 3);
		
		DatePeriod period = target.getPeriodWhichIncludeAvailabilityDate(GeneralDate.ymd(2020, 10, 1));
		
		assertThat(period.start()).isEqualTo( GeneralDate.ymd(2020, 9, 16));
		assertThat(period.end()).isEqualTo( GeneralDate.ymd(2020, 10, 15));
		
	} 

}
