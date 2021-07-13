package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public class IntegrationOfDailyTest {
	@Injectable 
	TimeLeavingOfDailyAttd timeLeaving;
	
	@Injectable 
	AttendanceTimeOfDailyAttendance attendanceTime;
	
	@Injectable
	OutingTimeOfDailyAttd outingTime;
	
	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 出退勤(timeLeaving) is mocked 
	 */
	@Test
	public void testGetTimeVacation_empty_case1() {
		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤 mocked 
				Optional.empty(), // 勤怠時間 empty
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	
	/** 
	 * 勤怠時間(attendanceTime) is mocked
	 * 出退勤 (timeLeaving) is empty
	 * 外出時間帯(outingTime) empty
	 */
	@Test
	public void testGetTimeVacation_empty_case2() {
		
		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
				Optional.empty(), // 出退勤 empty 
				Optional.of(attendanceTime), // 勤怠時間 mocked
				Optional.empty()); // 外出時間帯 empty
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).isEmpty();		
	}
	
	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 外出時間帯(outingTime) is mocked
	 */
	@Test
	public void testGetTimeVacation_empty_case3() {
		
		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
				Optional.empty(), // 出退勤 mocked 
				Optional.empty(), // 勤怠時間 empty
				Optional.of(outingTime)); // 外出時間帯
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).isEmpty();
		
	}

	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 出退勤 (timeLeaving) is empty
	 * 外出時間帯(outingTime) empty
	 */
	@Test
	public void testGetTimeVacation_empty_case5() {
		
		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
				Optional.empty(), // 出退勤 empty 
				Optional.empty(), // 勤怠時間 empty
				Optional.empty()); // 外出時間帯 empty
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).isEmpty();
	}
	
	/**
	 * 遅刻時間を取得する getLateTimeOfDaily == empty List
	 * 早退時間を取得	getLeaveEarlyTimeOfDaily == empty list
	 * 外出時間を取得する getOutingTimeOfDaily == emptyList
	 */
	@Test
	public void testGetTimeVacation_empty_case6() {
		
		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
										Optional.of(timeLeaving), 
										Optional.of(attendanceTime),
										Optional.of(outingTime));
		
		new Expectations() {{
			
			attendanceTime.getLateTimeOfDaily();
			// result = empty
			
			attendanceTime.getLeaveEarlyTimeOfDaily();
			// result = empty
			
			attendanceTime.getOutingTimeOfDaily();
			// result = empty
			
		}};
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).isEmpty();
	}
	
	public static class Helper{
		
		private static WorkInfoOfDailyAttendance defaultWorkInfo = new WorkInfoOfDailyAttendance(
				new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001")), 
				CalculationState.No_Calculated, 
				NotUseAttribute.Not_use, 
				NotUseAttribute.Not_use, 
				DayOfWeek.MONDAY, 
				Collections.emptyList(), 
				Optional.empty());
		
		private static AffiliationInforOfDailyAttd defaultAffInfo = new AffiliationInforOfDailyAttd(
				new EmploymentCode("EmpCode-001"),
				"JobTitle-Id-001", 
				"Wpl-Id-001", 
				new ClassificationCode("class-001"), 
				Optional.empty(), 
				Optional.empty());
		
		public static IntegrationOfDaily createWithParams(
				Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
				Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
				Optional<OutingTimeOfDailyAttd> outingTime) {
			return new IntegrationOfDaily(
					  "sid"
					, GeneralDate.today()
					, defaultWorkInfo
					, CalAttrOfDailyAttd.createAllCalculate()
					, defaultAffInfo
					, Optional.empty()
					, Collections.emptyList()
					, outingTime
					, new BreakTimeOfDailyAttd()
					, optAttendanceTime
					, optTimeLeaving
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty());
		}
	}
}
