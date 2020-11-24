package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WorkScheduleTest {
	
	@Injectable
	WorkSchedule.Require require;
	
	@Injectable 
	TimeLeavingOfDailyAttd timeLeaving;
	
	@Injectable 
	AttendanceTimeOfDailyAttendance attendanceTime;
	
	@Injectable
	OutingTimeOfDailyAttd outingTime;
	
	@Injectable
	static WorkInfoOfDailyAttendance workInfo;
	
	@Mocked
	static AffiliationInforOfDailyAttd affInfo;
	
	@Injectable
	static TimevacationUseTimeOfDaily timeVacationUseOfDaily;
	
	@Injectable
	static BreakTimeGoOutTimes workTime;
	
	@Injectable
	static OutingTotalTime recordTotalTime;
	
	@Injectable
	static OutingTotalTime deductionTotalTime;
	
	@Test
	public void getters() {
		WorkSchedule data = new WorkSchedule("employeeID",
				GeneralDate.today(), ConfirmedATR.CONFIRMED, null, null, new ArrayList<>(),
				new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
		NtsAssert.invokeGetters(data);
	}
	
	@Test
	public void testCreate_throwException(@Injectable WorkInformation workInformation) {
		
		new Expectations() {{
			
			workInformation.checkNormalCondition(require);
			result = false;
			
		}};
		
		NtsAssert.businessException("xxx", 
				() -> WorkSchedule.create(require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation));
		
	}
	
	@Test
	public void testCreate(
			@Injectable WorkInformation workInformation,
			@Mocked AffiliationInforOfDailyAttd affInfo,
			@Mocked WorkInfoOfDailyAttendance workInfo,
			@Mocked TimeLeavingOfDailyAttd timeLeaving
			) {
		
		new Expectations() {{
			
			workInformation.checkNormalCondition(require);
			result = true;
			
		}};
		
		WorkSchedule result = WorkSchedule.create(require, "empId", GeneralDate.ymd(2020, 11, 1), workInformation);
		
		assertThat( result.getEmployeeID() ).isEqualTo( "empId" );
		assertThat ( result.getYmd() ).isEqualTo( GeneralDate.ymd(2020, 11, 1) );
		assertThat ( result.getConfirmedATR() ).isEqualTo( ConfirmedATR.UNSETTLED );
		assertThat ( result.getLstBreakTime() ).isEmpty();
		assertThat ( result.getLstEditState() ).isEmpty();
		assertThat ( result.getOptAttendanceTime() ).isEmpty();
		assertThat ( result.getOptSortTimeWork() ).isEmpty();
		
		// TODO affInfo, workInfo, timeLeavingをどうやってテストすればいいなのまだ微妙
		assertThat ( result.getAffInfo() ).isEqualTo( affInfo );
		assertThat ( result.getWorkInfo() ).isEqualTo( workInfo );
		assertThat ( result.getOptTimeLeaving().get() ).isEqualTo( timeLeaving );
		
	}
	
	@Test
	public void testConfirm() {
		
		WorkSchedule target = Helper.createWithConfirmAtr(ConfirmedATR.UNSETTLED);
		target.confirm();
		
		assertThat(target.getConfirmedATR()).isEqualTo(ConfirmedATR.CONFIRMED);
	}
	
	@Test
	public void testRemoveConfirm() {
		
		WorkSchedule target = Helper.createWithConfirmAtr(ConfirmedATR.CONFIRMED);
		target.removeConfirm();
		
		assertThat(target.getConfirmedATR()).isEqualTo(ConfirmedATR.UNSETTLED);
	}
	
	@Test
	public void testRemoveHandCorrections() {
		
		List<EditStateOfDailyAttd> editStateList = new ArrayList<>( Arrays.asList(
				new EditStateOfDailyAttd(1, EditStateSetting.HAND_CORRECTION_MYSELF),
				new EditStateOfDailyAttd(2, EditStateSetting.HAND_CORRECTION_OTHER),
				new EditStateOfDailyAttd(3, EditStateSetting.REFLECT_APPLICATION),
				new EditStateOfDailyAttd(4, EditStateSetting.IMPRINT),
				new EditStateOfDailyAttd(5, EditStateSetting.HAND_CORRECTION_MYSELF),
				new EditStateOfDailyAttd(6, EditStateSetting.HAND_CORRECTION_OTHER),
				new EditStateOfDailyAttd(7, EditStateSetting.REFLECT_APPLICATION),
				new EditStateOfDailyAttd(8, EditStateSetting.IMPRINT)));
		
		WorkSchedule target = Helper.createWithEditStateList(editStateList);
		target.removeHandCorrections();
		
		assertThat(target.getLstEditState())
			.extracting( 
					e -> e.getAttendanceItemId(), 
					e -> e.getEditStateSetting())
			.containsExactly( 
					tuple(3, EditStateSetting.REFLECT_APPLICATION),
					tuple(4, EditStateSetting.IMPRINT),
					tuple(7, EditStateSetting.REFLECT_APPLICATION),
					tuple(8, EditStateSetting.IMPRINT)
					);
	}
	
	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 出退勤(timeLeaving) is mocked 
	 */
	@Test
	public void testGetTimeVacation_empty_case1() {
		
		// Arrange
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤 mocked 
				Optional.empty(), // 勤怠時間 empty
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		
	}
	
	/** 
	 * 勤怠時間(attendanceTime) is mocked
	 * 出退勤 (timeLeaving) is empty
	 * 外出時間帯(outingTime) empty
	 */
	@Test
	public void testGetTimeVacation_empty_case2() {
		
		// Arrange
		WorkSchedule target = Helper.createWithParams(
				Optional.empty(), // 出退勤 empty 
				Optional.of(attendanceTime), // 勤怠時間 mocked
				Optional.empty()); // 外出時間帯 empty
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> outingTimes = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		assertThat(outingTimes).isEmpty();
		
	}
	
	/**
	 * 勤怠時間 (attendanceTime) is empty
	 * 外出時間帯(outingTime) is mocked
	 */
	@Test
	public void testGetTimeVacation_empty_case3() {
		
		// Arrange
		WorkSchedule target = Helper.createWithParams(
				Optional.empty(), // 出退勤 mocked 
				Optional.empty(), // 勤怠時間 empty
				Optional.of(outingTime)); // 外出時間帯
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
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
		WorkSchedule target = Helper.createWithParams(
				Optional.empty(), // 出退勤 empty 
				Optional.empty(), // 勤怠時間 empty
				Optional.empty()); // 外出時間帯 empty
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> outtingTimes = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		assertThat(outtingTimes).isEmpty();
		
	}
	
	/**
	 * 遅刻時間を取得する getLateTimeOfDaily == empty List
	 * 早退時間を取得	getLeaveEarlyTimeOfDaily == empty list
	 * 外出時間を取得する getOutingTimeOfDaily == emptyList
	 */
	@Test
	public void testGetTimeVacation_empty_case6() {
		
		// Arrange
		
		WorkSchedule target = Helper.createWithParams(
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
		Map<TimezoneToUseHourlyHoliday, TimeVacation> lateTimes = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> earlyTimes = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		Map<TimezoneToUseHourlyHoliday, TimeVacation> outingTime = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(lateTimes).isEmpty();
		assertThat(earlyTimes).isEmpty();
		assertThat(outingTime).isEmpty();
		
	}
	
	/**
	 * result has one item
	 */
	@Test
	public void testGetLateTimes_successfully_single(
			@Injectable LateTimeOfDaily lateTime1,
			@Injectable LateTimeOfDaily lateTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLateTimeOfDaily();
			result = Arrays.asList( lateTime1, lateTime2 );
			
			lateTime1.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1);
			lateTime2.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2);
			
			timeLeaving.getStartTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1));
			// result = empty
			
			timeLeaving.getStartTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(100),
									new TimeWithDayAttr(200)));
			
			lateTime2.getTimePaidUseTime();
			// result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		
		// Assert
		assertThat(result).hasSize(1);
		TimeVacation value = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_BEFORE);
		assertThat(value.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value.getTimeList())
			.extracting(
				e -> e.startValue(),
				e -> e.endValue())
			.containsExactly( tuple ( 100, 200 ));
		
	}
	
	/**
	 * result has two item
	 */
	@Test
	public void testGetLateTimes_successfully_multi(
			@Injectable LateTimeOfDaily lateTime1,
			@Injectable LateTimeOfDaily lateTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime1,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLateTimeOfDaily();
			result = Arrays.asList( lateTime1, lateTime2 );
			
			lateTime1.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1);
			lateTime2.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2);
			
			timeLeaving.getStartTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1));
			result = Optional.of(new TimeSpanForCalc(
					new TimeWithDayAttr(100),
					new TimeWithDayAttr(200)));
			
			timeLeaving.getStartTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(300),
									new TimeWithDayAttr(400)));

			lateTime1.getTimePaidUseTime();
			result = timePaidUseTime1;
			
			lateTime2.getTimePaidUseTime();
			result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getLateTimes");
		
		// Assert
		assertThat(result).hasSize(2);
		
			// value1
		TimeVacation value1 = result.get(TimezoneToUseHourlyHoliday.WORK_NO1_BEFORE);
		assertThat(value1.getUseTime()).isEqualTo(timePaidUseTime1);
		assertThat(value1.getTimeList())
			.extracting(
				e -> e.startValue(),
				e -> e.endValue())
			.containsExactly( tuple ( 100, 200 ));
		
			// value2
		TimeVacation value2 = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_BEFORE);
		assertThat(value2.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value2.getTimeList())
			.extracting(
				e -> e.startValue(),
				e -> e.endValue())
			.containsExactly( tuple ( 300, 400 ));
		
	}
	
	@Test
	public void testGetEarlyTimes_successfully_single(
			@Injectable LeaveEarlyTimeOfDaily earlyTime1,
			@Injectable LeaveEarlyTimeOfDaily earlyTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLeaveEarlyTimeOfDaily();
			result = Arrays.asList( earlyTime1, earlyTime2 );
			
			earlyTime1.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1);
			earlyTime2.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2);
			
			timeLeaving.getEndTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1));
			// result = empty
			timeLeaving.getEndTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(100),
									new TimeWithDayAttr(200)));
			
			earlyTime2.getTimePaidUseTime();
			// result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		
		// Assert
		assertThat(result).hasSize(1);
		TimeVacation value = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_AFTER);
		assertThat(value.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value.getTimeList())
			.extracting(
				e -> e.startValue(),
				e -> e.endValue())
			.containsExactly( tuple ( 100, 200 ));
		
	}
	
	@Test
	public void testGetEarlyTimes_successfully_multi(
			@Injectable LeaveEarlyTimeOfDaily earlyTime1,
			@Injectable LeaveEarlyTimeOfDaily earlyTime2,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime1,
			@Injectable TimevacationUseTimeOfDaily timePaidUseTime2) {
		
		new Expectations() {{
			
			attendanceTime.getLeaveEarlyTimeOfDaily();
			result = Arrays.asList( earlyTime1, earlyTime2 );
			
			earlyTime1.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1);
			earlyTime2.getWorkNo();
			result = new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2);
			
			timeLeaving.getEndTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(1));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(100),
									new TimeWithDayAttr(200)));
			
			timeLeaving.getEndTimeVacations(new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo(2));
			result = Optional.of(new TimeSpanForCalc(
									new TimeWithDayAttr(300),
									new TimeWithDayAttr(400)));
			
			earlyTime1.getTimePaidUseTime();
			result = timePaidUseTime1;
			
			earlyTime2.getTimePaidUseTime();
			result = timePaidUseTime2;
		}};
		
		WorkSchedule target = Helper.createWithParams(
				Optional.of(timeLeaving), // 出退勤
				Optional.of(attendanceTime), // 勤怠時間
				Optional.empty());
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getEarlyTimes");
		
		// Assert
		assertThat(result).hasSize(2);
		TimeVacation value1 = result.get(TimezoneToUseHourlyHoliday.WORK_NO1_AFTER);
		assertThat(value1.getUseTime()).isEqualTo(timePaidUseTime1);
		assertThat(value1.getTimeList())
			.extracting(
				e -> e.startValue(),
				e -> e.endValue())
			.containsExactly( tuple ( 100, 200 ));
		
		TimeVacation value2 = result.get(TimezoneToUseHourlyHoliday.WORK_NO2_AFTER);
		assertThat(value2.getUseTime()).isEqualTo(timePaidUseTime2);
		assertThat(value2.getTimeList())
			.extracting(
				e -> e.startValue(),
				e -> e.endValue())
			.containsExactly( tuple ( 300, 400 ));
		
	}
	
	
	/**
	 * 外出時間を取得する getOutingTimeOfDaily 
	 * 「私用, 組合」と合ってない
	 */
	@Test
	public void getOutingTimes_empty() {
		
		// Arrange
		
		WorkSchedule target = Helper.createWithParams(
										Optional.empty(),
										Optional.of(attendanceTime),
										Optional.of(outingTime));
		
		new Expectations() {{
			
			attendanceTime.getOutingTimeOfDaily();
			result = Arrays.asList(
					Helper.createOutingTimeOfDailyWithReason(GoingOutReason.COMPENSATION),
					Helper.createOutingTimeOfDailyWithReason(GoingOutReason.PUBLIC)
					);
			
		}};
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	
	@Test
	public void getOutingTimes_successfully(
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily1,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily2,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily3,
			@Injectable TimevacationUseTimeOfDaily timevacationUseTimeOfDaily4
			) {
		
		// Arrange
		
		WorkSchedule target = Helper.createWithParams(
										Optional.empty(),
										Optional.of(attendanceTime), // 勤怠時間
										Optional.of(outingTime)); // 外出時間帯
		
		new Expectations() {{
			
			attendanceTime.getOutingTimeOfDaily();
			result = Arrays.asList(
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.PRIVATE, timevacationUseTimeOfDaily1),
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.PUBLIC, timevacationUseTimeOfDaily2),
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.COMPENSATION, timevacationUseTimeOfDaily3),
					Helper.createOutingTimeOfDailyWithParams(GoingOutReason.UNION, timevacationUseTimeOfDaily4));
			
			outingTime.getTimeZoneByGoOutReason((GoingOutReason) any);
			returns(
					Arrays.asList(
							new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200)),
							new TimeSpanForCalc(new TimeWithDayAttr(300), new TimeWithDayAttr(400))),
					Arrays.asList(new TimeSpanForCalc(new TimeWithDayAttr(500), new TimeWithDayAttr(600)))
					);
		}};
		
		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = NtsAssert.Invoke.privateMethod(target, "getOutingTimes");
		
		// Assert
		assertThat(result).hasSize(2);
		TimeVacation value1 = result.get(TimezoneToUseHourlyHoliday.GOINGOUT_PRIVATE);
		TimeVacation value2 = result.get(TimezoneToUseHourlyHoliday.GOINGOUT_UNION);
		
		assertThat(value1.getUseTime()).isEqualTo(timevacationUseTimeOfDaily1);
		assertThat(value1.getTimeList())
			.extracting(
				d -> d.getStart().v(),
				d -> d.getEnd().v())
			.containsExactly(
					tuple(100, 200),
					tuple(300, 400));
		
		assertThat(value2.getUseTime()).isEqualTo(timevacationUseTimeOfDaily4);
		assertThat(value2.getTimeList())
			.extracting(
				d -> d.getStart().v(),
				d -> d.getEnd().v())
			.containsExactly(
				tuple(500, 600));
		
	}
	
	static class Helper {
		
		/**
		 * @param optTimeLeaving 出退勤
		 * @param optAttendanceTime 勤怠時間
		 * @param outingTime 外出時間帯
		 * @return
		 */
		static WorkSchedule createWithParams(
				Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
				Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
				Optional<OutingTimeOfDailyAttd> outingTime
				) {
			
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					ConfirmedATR.UNSETTLED,
					workInfo,
					affInfo, 
					Collections.emptyList(),
					Collections.emptyList(),
					optTimeLeaving, // parameter
					optAttendanceTime, // parameter
					Optional.empty(),
					outingTime); // parameter
		}
		
		static WorkSchedule createWithConfirmAtr(ConfirmedATR confirmAtr) {
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					confirmAtr,
					workInfo,
					affInfo, 
					Collections.emptyList(),
					Collections.emptyList(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty());
		}
		
		static WorkSchedule createWithEditStateList(List<EditStateOfDailyAttd> editStateList) {
			return new WorkSchedule(
					"employeeID",
					GeneralDate.today(),
					ConfirmedATR.UNSETTLED,
					workInfo,
					affInfo, 
					Collections.emptyList(),
					editStateList,
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty());
		}
		
		static OutingTimeOfDaily createOutingTimeOfDailyWithReason(GoingOutReason reason) {
			return new OutingTimeOfDaily(
					workTime,
					reason,
					timeVacationUseOfDaily,
					recordTotalTime,
					deductionTotalTime,
					Collections.emptyList());
			
		}
		
		static OutingTimeOfDaily createOutingTimeOfDailyWithParams(GoingOutReason reason, TimevacationUseTimeOfDaily timeVacationUseOfDaily) {
			return new OutingTimeOfDaily(
					workTime,
					reason,
					timeVacationUseOfDaily,
					recordTotalTime,
					deductionTotalTime,
					Collections.emptyList());
			
		}
		
	}
	
}
