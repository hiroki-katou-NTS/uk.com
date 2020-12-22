package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;

public class WorkAvailabilityRuleWeekSettingTest {
	
	@Test
	public void getters() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper.defaultCreate();
		NtsAssert.invokeGetters(target);  
	}
	
	@Test
	public void TestGetShiftPeriodUnit() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper.defaultCreate();
		
		assertThat(target.getShiftPeriodUnit()).isEqualTo(WorkAvailabilityPeriodUnit.WEEKLY);
	}
	
	@Test
	public void testIsOverDeadline_ONE_WEEK_AGO_false() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		// today = 2020/10/8 THU
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 8, 0, 0, 0);
		
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 12));

		assertThat(isOverDeadline).isFalse();
		
	}
	
	@Test
	public void testIsOverDeadline_ONE_WEEK_AGO_true() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		// today = 2020/10/9 FRI
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 9, 0, 0, 0);
		
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 12));

		assertThat(isOverDeadline).isTrue();
	}
	
	@Test
	public void testIsOverDeadline_TWO_WEEK_AGO_false() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.TWO_WEEK_AGO, DayOfWeek.THURSDAY);
		
		// today = 2020/10/1 THU
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 1, 0, 0, 0);
		
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 12));

		assertThat(isOverDeadline).isFalse();
		
	}
	
	@Test
	public void testIsOverDeadline_TWO_WEEK_AGO_true() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.TWO_WEEK_AGO, DayOfWeek.THURSDAY);
		
		// today = 2020/10/2 FRI
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 2, 0, 0, 0);
		
		boolean isOverDeadline = target.isOverDeadline(GeneralDate.ymd(2020, 10, 12));

		assertThat(isOverDeadline).isTrue();
	}
	
	@Test
	public void testIsOverHolidayMaxdays() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper.defaultCreate();
		
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
	
	/**
	 * ONE_WEEK_AGO
	 * targetDate == firstDayOfWeek
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case1() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 4));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 8));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 11) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 17) );
		
	}
	
	/**
	 * ONE_WEEK_AGO
	 * targetDate == deadline
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case2() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 8));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 8));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 11) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 17) );
		
	}
	
	/**
	 * ONE_WEEK_AGO
	 * deadline < targetDate == firstDayOfWeek
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case3() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 9));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 15));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 18) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 24) );
		
	}
	
	/**
	 * TWO_WEEK_AGO
	 * targetDate == firstDayOfWeek
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case4() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.TWO_WEEK_AGO, DayOfWeek.THURSDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 4));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 8));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 18) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 24) );
		
	}
	
	/**
	 * TWO_WEEK_AGO
	 * targetDate == deadline
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case5() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.TWO_WEEK_AGO, DayOfWeek.THURSDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 8));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 8));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 18) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 24) );
	}
	
	/**
	 * TWO_WEEK_AGO
	 * deadline < targetDate < firstDayOfWeek
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_case6() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.TWO_WEEK_AGO, DayOfWeek.THURSDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 9));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 15));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 25) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 31) );
	}
	
	/**
	 * special: firstDayOfWeek = expectDeadLine.dayOfWeek
	 * ONE_WEEK_AGO
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_special_1() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.SUNDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 1));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 4));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 11) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 17) );
	}
	
	/**
	 * special: firstDayOfWeek = expectDeadLine.dayOfWeek
	 * TWO_WEEK_AGO
	 */
	@Test
	public void testGetcorrespondingDeadlineAndPeriod_special_2() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.TWO_WEEK_AGO, DayOfWeek.SUNDAY);
		
		DeadlineAndPeriodOfWorkAvailability ruleInfo = target.getCorrespondingDeadlineAndPeriod(GeneralDate.ymd(2020, 10, 1));
		
		assertThat(ruleInfo.getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 4));
		assertThat( ruleInfo.getPeriod().start() ).isEqualTo( GeneralDate.ymd(2020, 10, 18) );
		assertThat( ruleInfo.getPeriod().end() ).isEqualTo( GeneralDate.ymd(2020, 10, 24) );
	}
	
	@Test
	public void testGetPeriodWhichIncludeExpectingDate() {
		
		WorkAvailabilityRuleWeekSetting target = WorkAvailabilityRuleWeekSettingHelper
				.createWithParam(DayOfWeek.SUNDAY, DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		DatePeriod period = target.getPeriodWhichIncludeAvailabilityDate(GeneralDate.ymd(2020, 10, 15));
		
		assertThat( period.start() ).isEqualTo( GeneralDate.ymd(2020, 10, 11) );
		assertThat( period.end() ).isEqualTo( GeneralDate.ymd(2020, 10, 17) );
	} 

}
