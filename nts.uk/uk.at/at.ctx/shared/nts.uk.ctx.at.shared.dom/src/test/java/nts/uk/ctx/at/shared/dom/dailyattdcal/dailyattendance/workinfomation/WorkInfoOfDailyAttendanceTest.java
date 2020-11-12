package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance.Require;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class WorkInfoOfDailyAttendanceTest {
	@Injectable
	private Require require;

	@Test
	public void getters() {
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = WorkInfoOfDailyAttendanceHelper.getWorkInfoOfDailyAttendanceDefault();
		NtsAssert.invokeGetters(workInfoOfDailyAttendance);
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == optional.empty
	 */
	@Test
	public void testGetWorkStyle() {
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = WorkInfoOfDailyAttendanceHelper.getWorkInfoOfDailyAttendanceDefault();
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.empty();
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.isPresent()).isFalse();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.ONE_DAY_REST
	 */
	@Test
	public void testGetWorkStyle_1() {
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = WorkInfoOfDailyAttendanceHelper.getWorkInfoOfDailyAttendanceDefault();
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.ONE_DAY_REST);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.ONE_DAY_REST)).isTrue();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.MORNING_WORK
	 */
	@Test
	public void testGetWorkStyle_2() {
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = WorkInfoOfDailyAttendanceHelper.getWorkInfoOfDailyAttendanceDefault();
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.MORNING_WORK);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.MORNING_WORK)).isTrue();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.AFTERNOON_WORK
	 */
	@Test
	public void testGetWorkStyle_3() {
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = WorkInfoOfDailyAttendanceHelper.getWorkInfoOfDailyAttendanceDefault();
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.AFTERNOON_WORK);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.AFTERNOON_WORK)).isTrue();
	}
	
	/**
	 * 勤務実績の勤務情報.出勤・休日系の判定(require) == WorkStyle.ONE_DAY_WORK
	 */
	@Test
	public void testGetWorkStyle_4() {
		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = WorkInfoOfDailyAttendanceHelper.getWorkInfoOfDailyAttendanceDefault();
		new MockUp<WorkInformation>() {
			@Mock
			public Optional<WorkStyle> getWorkStyle(WorkInformation.Require require) {
				return Optional.of(WorkStyle.ONE_DAY_WORK);
			}
		};
		Optional<WorkStyle> workstyle = workInfoOfDailyAttendance.getWorkStyle(require);
		assertThat(workstyle.get().equals(WorkStyle.ONE_DAY_WORK)).isTrue();
	}
	
	@Test
	public void testCreate_scheduleTimeSheets_empty() {
		
		// Arrange
		WorkInformation scheduleInfo = new WorkInformation(new WorkTypeCode("a01"), new WorkTimeCode("b01"));
		WorkInformation recordInfo = new WorkInformation(new WorkTypeCode("a01"), new WorkTimeCode("b01"));
		
		new Expectations(scheduleInfo) { {
			scheduleInfo.getWorkInfoAndTimeZone(require);
			result = Optional.of(WorkInfoOfDailyAttendanceHelper.createWorkInfoAndTimeZone(Collections.emptyList()));
		}};
		
		// Action
		WorkInfoOfDailyAttendance target = WorkInfoOfDailyAttendance.create(require, recordInfo, scheduleInfo, 
				CalculationState.No_Calculated, NotUseAttribute.Not_use, NotUseAttribute.Not_use, DayOfWeek.MONDAY);
		
		// Assert
		assertThat(target.getRecordInfo()).isEqualTo(recordInfo);
		assertThat(target.getScheduleInfo()).isEqualTo(scheduleInfo);
		assertThat(target.getCalculationState()).isEqualTo(CalculationState.No_Calculated);
		assertThat(target.getBackStraightAtr()).isEqualTo(NotUseAttribute.Not_use);
		assertThat(target.getGoStraightAtr()).isEqualTo(NotUseAttribute.Not_use);
		assertThat(target.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
		assertThat(target.getScheduleTimeSheets()).isEmpty(); // emptyList
	}
	
	@Test
	public void testCreate_scheduleTimeSheets_not_empty() {
		
		// Arrange
		WorkInformation scheduleInfo = new WorkInformation(new WorkTypeCode("a01"), new WorkTimeCode("b01"));
		WorkInformation recordInfo = new WorkInformation(new WorkTypeCode("a01"), new WorkTimeCode("b01"));
		
		List<TimeZone> listTimeZone = Arrays.asList(
				new TimeZone(new TimeWithDayAttr(10), new TimeWithDayAttr(20)),
				new TimeZone(new TimeWithDayAttr(30), new TimeWithDayAttr(40))
				);
		
		new Expectations(scheduleInfo) {{
			scheduleInfo.getWorkInfoAndTimeZone(require);
			result = Optional.of(WorkInfoOfDailyAttendanceHelper.createWorkInfoAndTimeZone(listTimeZone));
		}};
		
		// Action
		WorkInfoOfDailyAttendance target = WorkInfoOfDailyAttendance.create(require, recordInfo, scheduleInfo, 
				CalculationState.No_Calculated, NotUseAttribute.Not_use, NotUseAttribute.Not_use, DayOfWeek.MONDAY);
		
		// Assert
		assertThat(target.getRecordInfo()).isEqualTo(recordInfo);
		assertThat(target.getScheduleInfo()).isEqualTo(scheduleInfo);
		assertThat(target.getCalculationState()).isEqualTo(CalculationState.No_Calculated);
		assertThat(target.getBackStraightAtr()).isEqualTo(NotUseAttribute.Not_use);
		assertThat(target.getGoStraightAtr()).isEqualTo(NotUseAttribute.Not_use);
		assertThat(target.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
		assertThat(target.getScheduleTimeSheets())
			.extracting(
				e -> e.getWorkNo().v(),
				e -> e.getAttendance().v(),
				e -> e.getLeaveWork().v())
			.containsExactly(
				tuple(1, 10, 20),
				tuple(2, 30, 40));
	}

}
