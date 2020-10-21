package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.GoOutReason;

public class AttendanceTimeOfDailyAttendanceTest {
	@Test
	public void getters() {
		AttendanceTimeOfDailyAttendance data = AttendanceTimeOfDailyAttendanceHelper.attTimeDailyDummy;
		NtsAssert.invokeGetters(data);
	}
	
	/**
	 * 遅刻時間を取得する = empty
	 * 遅刻時間リスト = empty
	 */
	
	@Test
	public void getLateTime_empty() {	
		val attTimeDailyDummy = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createLateTime_Empty(), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		assertThat(attTimeDailyDummy.getLateTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 遅刻時間を取得する = empty
	 * 勤務時間 = null
	 */
	@Test
	public void getLateTime_empty_1() {	
		val attTimeDailyDummy = AttendanceTimeOfDailyAttendanceHelper.create_actualWorkingTimeOfDaily_empty();
		assertThat(attTimeDailyDummy.getLateTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 遅刻時間を取得する = empty
	 * 総労働時間 = null
	 */
	
	@Test
	public void getLateTime_empty_2() {	
		val attTimeDailyDummy = AttendanceTimeOfDailyAttendanceHelper.createLateTime_totalTime_empty();
		assertThat(attTimeDailyDummy.getLateTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 遅刻時間を取得する not empty
	 */
	
	@Test
	public void getLateTime_not_empty() {	
		val attTimeDailyDummy = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createLateTime(), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		List<LateTimeOfDaily> actual = attTimeDailyDummy.getLateTimeOfDaily();
		
		assertThat(actual.get(0).getLateTime().getTime()).isEqualTo(new AttendanceTime(120));
		assertThat(actual.get(0).getLateTime().getCalcTime()).isEqualTo(new AttendanceTime(120));

		assertThat(actual.get(0).getLateDeductionTime().getTime()).isEqualTo(new AttendanceTime(120));
		assertThat(actual.get(0).getLateDeductionTime().getCalcTime()).isEqualTo(new AttendanceTime(120));
		assertThat(actual.get(0).getWorkNo()).isEqualTo(new WorkNo(0));
		
		val timePaidUseTime = actual.get(0).getTimePaidUseTime();
		assertThat(timePaidUseTime.getTimeAnnualLeaveUseTime()).isEqualTo(new AttendanceTime(480));
		assertThat(timePaidUseTime.getTimeCompensatoryLeaveUseTime()).isEqualTo(new AttendanceTime(480));
		assertThat(timePaidUseTime.getSixtyHourExcessHolidayUseTime()).isEqualTo(new AttendanceTime(480));
		assertThat(timePaidUseTime.getTimeSpecialHolidayUseTime()).isEqualTo(new AttendanceTime(480));
		
		assertThat(actual.get(0).getExemptionTime().getExemptionTime()).isEqualTo(new AttendanceTime(0));
	}
	
	/**
	 * 早退時間を取得する = empty
	 * 勤務時間 = null
	 */
	@Test
	public void getEarlyTime_empty_0() {	
		val attTimeDailyDummy = AttendanceTimeOfDailyAttendanceHelper.create_actualWorkingTimeOfDaily_empty();
		assertThat(attTimeDailyDummy.getLeaveEarlyTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 早退時間を取得する = empty
	 * 総労働時間 = empty
	 */
	@Test
	public void getEarlyTime_empty_1() {	
		val attTimeDailyDummy = AttendanceTimeOfDailyAttendanceHelper.create_totalTime_empty();
		assertThat(attTimeDailyDummy.getLeaveEarlyTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 早退時間を取得する = empty
	 * 早退時間リスト = empty
	 */
	@Test
	public void getEarlyTime_empty_2() {	
		val attTimeDailyDummy = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createEarlyTime_Empty(), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		
		assertThat(attTimeDailyDummy.getLeaveEarlyTimeOfDaily()).isEmpty();
	}
	
	
	/**
	 * 早退時間を取得する not empty
	 */
	
	@Test
	public void getEarlyTime_not_empty() {
		val attTimeDailyDummy = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createEarlyTime(), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		List<LeaveEarlyTimeOfDaily> actual = attTimeDailyDummy.getLeaveEarlyTimeOfDaily();
		
		assertThat(actual.get(0).getLeaveEarlyTime().getTime()).isEqualTo(new AttendanceTime(120));
		assertThat(actual.get(0).getLeaveEarlyTime().getCalcTime()).isEqualTo(new AttendanceTime(120));

		assertThat(actual.get(0).getLeaveEarlyDeductionTime().getTime()).isEqualTo(new AttendanceTime(120));
		assertThat(actual.get(0).getLeaveEarlyDeductionTime().getCalcTime()).isEqualTo(new AttendanceTime(120));
		assertThat(actual.get(0).getWorkNo()).isEqualTo(new WorkNo(0));
		
		val timePaidUseTime = actual.get(0).getTimePaidUseTime();
		assertThat(timePaidUseTime.getTimeAnnualLeaveUseTime()).isEqualTo(new AttendanceTime(480));
		assertThat(timePaidUseTime.getTimeCompensatoryLeaveUseTime()).isEqualTo(new AttendanceTime(480));
		assertThat(timePaidUseTime.getSixtyHourExcessHolidayUseTime()).isEqualTo(new AttendanceTime(480));
		assertThat(timePaidUseTime.getTimeSpecialHolidayUseTime()).isEqualTo(new AttendanceTime(480));
		
		assertThat(actual.get(0).getIntervalTime().getExemptionTime()).isEqualTo(new AttendanceTime(0));
		
	}
	
	/**
	 * 外出時間を取得する = empty
	 * 勤務時間 = null
	 */
	@Test
	public void getOutingTime_empty_0() {	
		val attTimeDailyDummy = AttendanceTimeOfDailyAttendanceHelper.create_actualWorkingTimeOfDaily_empty();
		assertThat(attTimeDailyDummy.getOutingTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 外出時間を取得する = empty
	 * 総労働時間 = empty
	 */
	@Test
	public void getOutingTime_empty_1() {	
		val attTimeDailyDummy = AttendanceTimeOfDailyAttendanceHelper.create_totalTime_empty();
		assertThat(attTimeDailyDummy.getOutingTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 外出時間を取得する = empty
	 * 外出時間リスト = empty
	 */
	@Test
	public void getOutingTime_empty_2() {	
		val attTimeDailyDummy = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createOutingTime_Empty(), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		assertThat(attTimeDailyDummy.getOutingTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 早退時間を取得する not empty
	 */
	
	@Test
	public void getOutingTime_not_empty() {
		val attTimeDailyDummy = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createOutingTime_not_empty(), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		List<OutingTimeOfDaily> actual = attTimeDailyDummy.getOutingTimeOfDaily();
		
		assertThat(actual.get(0).getWorkTime()).isEqualTo(new BreakTimeGoOutTimes(120));
		assertThat(actual.get(0).getReason()).isEqualTo(GoOutReason.UNION);
		
	}
	
}
