package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.TimeVacationHelper;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.GettingTimeVacactionService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
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
import nts.uk.shr.com.time.TimeWithDayAttr;
@RunWith(JMockit.class)
public class IntegrationOfDailyTest {
	
	@Injectable 
	TimeLeavingOfDailyAttd timeLeaving;
	
	@Injectable 
	AttendanceTimeOfDailyAttendance attendanceTime;
	
	@Injectable
	OutingTimeOfDailyAttd outingTime;
	
	@Test
	public void testGetTimeVacation_empty() {
		
		new MockUp<GettingTimeVacactionService>() {
			
			@Mock
			public Map<TimezoneToUseHourlyHoliday, TimeVacation> get(Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
					Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
					Optional<OutingTimeOfDailyAttd> outingTime) {
				
				return Collections.emptyMap();
			}
			
		};
		
		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
				Optional.of(timeLeaving), 
				Optional.of(attendanceTime),
				Optional.of(outingTime));
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	
	@Test
	public void testGetTimeVacation_success(
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime1,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime3,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime4) {
		
		Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacations = new HashMap<TimezoneToUseHourlyHoliday, TimeVacation>() {
			private static final long serialVersionUID = 1L;
			{
				put(TimezoneToUseHourlyHoliday.WORK_NO1_AFTER,
						TimeVacationHelper.createTimeVacation(
								new TimeWithDayAttr(100), new TimeWithDayAttr(200),// start1, end1
								timePaidUseTime1));
				
				put(TimezoneToUseHourlyHoliday.WORK_NO2_AFTER,
						TimeVacationHelper.createTimeVacation(
								new TimeWithDayAttr(200), new TimeWithDayAttr(300),// start1, end1
								timePaidUseTime2));
				
				put(TimezoneToUseHourlyHoliday.GOINGOUT_PRIVATE,
						TimeVacationHelper.createTimeVacations(
								new TimeWithDayAttr(400), new TimeWithDayAttr(500),// start1, end1
								new TimeWithDayAttr(600), new TimeWithDayAttr(700),// start2, end2
								timePaidUseTime3));
				
				put(TimezoneToUseHourlyHoliday.GOINGOUT_UNION,
						TimeVacationHelper.createTimeVacation(
								new TimeWithDayAttr(700), new TimeWithDayAttr(800),// start1, end1
								timePaidUseTime4));
			}
		};
		
		new MockUp<GettingTimeVacactionService>() {
			
			@Mock
			public Map<TimezoneToUseHourlyHoliday, TimeVacation> get(Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
					Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
					Optional<OutingTimeOfDailyAttd> outingTime) {

				return timeVacations;
			}
			
		};
		
		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
				Optional.of(timeLeaving), 
				Optional.of(attendanceTime),
				Optional.of(outingTime));
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();
		
		// Assert
		assertThat(result).hasSize(4);
		
		// value1
		TimeVacation value1 = result.get(TimezoneToUseHourlyHoliday.WORK_NO1_AFTER);
		assertThat(value1.getUseTime()).isEqualTo(timePaidUseTime1);
		assertThat(value1.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 100, 200 ));
		
		// value2
		TimeVacation value2 = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_AFTER);
		assertThat(value2.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value2.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 200, 300 ));
		
		// value3
		TimeVacation value3 = result.get(TimezoneToUseHourlyHoliday.GOINGOUT_PRIVATE);
		assertThat(value3.getUseTime()).isEqualTo(timePaidUseTime3);
		assertThat(value3.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 400, 500 )
							, tuple ( 600, 700 ));
		
		// value4
		TimeVacation value4 = result.get(TimezoneToUseHourlyHoliday.GOINGOUT_UNION);
		assertThat(value4.getUseTime()).isEqualTo(timePaidUseTime4);
		assertThat(value4.getTimeList())
			.extracting(
				e -> e.start(),
				e -> e.end())
			.containsExactly( tuple ( 700, 800 ));		
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
