package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * Unit Test: 日別勤怠の勤怠時間 
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)	
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
		//遅刻時間リスト = empty
		List<LateTimeOfDaily> lateTimeOfDaily = Collections.emptyList();
		val actualWorkingTimeDaily = ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createLateTime(lateTimeOfDaily), 0, 0, 0, 0);
		val attTimeDaily = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, actualWorkingTimeDaily
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		assertThat(attTimeDaily.getLateTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 遅刻時間を取得する not empty
	 */
	@Test
	public void getLateTime_not_empty() {	
		val lateTime = new LateTimeOfDaily(TimeWithCalculation.sameTime(new AttendanceTime(120)), 
				  TimeWithCalculation.sameTime(new AttendanceTime(120)), 
				  new WorkNo(0), 
				  new TimevacationUseTimeOfDaily(
						  new AttendanceTime(480)
						  , new AttendanceTime(480)
						  , new AttendanceTime(480)
						  , new AttendanceTime(480)
						  , Optional.of(new SpecialHdFrameNo(1))
						  , new AttendanceTime(480)
						  , new AttendanceTime(480))
				  , IntervalExemptionTime.defaultValue());
		
		val attTimeDaily = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createLateTime(Arrays.asList(lateTime)), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		
		assertThat(attTimeDaily.getLateTimeOfDaily())
		.extracting(
				  d -> d.getLateTime().getTime()
				, d -> d.getLateTime().getCalcTime()
				, d -> d.getLateDeductionTime().getTime()
				, d -> d.getLateDeductionTime().getCalcTime()
				, d -> d.getTimePaidUseTime().getTimeAnnualLeaveUseTime()
				, d -> d.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime()
				, d -> d.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime()
				, d -> d.getTimePaidUseTime().getTimeSpecialHolidayUseTime()
				, d -> d.getTimePaidUseTime().getSpecialHolidayFrameNo()
				, d -> d.getTimePaidUseTime().getTimeChildCareHolidayUseTime()
				, d -> d.getTimePaidUseTime().getTimeCareHolidayUseTime()
				, d -> d.getExemptionTime().getExemptionTime()
				,d -> d.getWorkNo())
		.containsExactly(
				Tuple.tuple(lateTime.getLateTime().getTime() 
					  , lateTime.getLateTime().getCalcTime()
					  , lateTime.getLateDeductionTime().getTime()
					  , lateTime.getLateDeductionTime().getCalcTime()
					  , lateTime.getTimePaidUseTime().getTimeAnnualLeaveUseTime()
					  , lateTime.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime()
					  , lateTime.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime()
					  , lateTime.getTimePaidUseTime().getTimeSpecialHolidayUseTime()
					  , lateTime.getTimePaidUseTime().getSpecialHolidayFrameNo()
					  , lateTime.getTimePaidUseTime().getTimeChildCareHolidayUseTime()
					  , lateTime.getTimePaidUseTime().getTimeCareHolidayUseTime()
					  , lateTime.getExemptionTime().getExemptionTime()
					  , lateTime.getWorkNo())
				);
	}
	
	/**
	 * 早退時間を取得する = empty
	 * 早退時間リスト = empty
	 */
	@Test
	public void getEarlyTime_empty() {
		//早退時間リスト = empty
		List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = Collections.emptyList();
		val attTimeDaily = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createEarlyTime(leaveEarlyTimeOfDaily), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		
		assertThat(attTimeDaily.getLeaveEarlyTimeOfDaily()).isEmpty();
	}
	
	
	/**
	 * 早退時間を取得する not empty
	 */
	
	@Test
	public void getEarlyTime_not_empty() {
		val leaveEarlyTime = new LeaveEarlyTimeOfDaily(
				TimeWithCalculation.sameTime(new AttendanceTime(120))
				, TimeWithCalculation.sameTime(new AttendanceTime(120))
				, new WorkNo(0)
				, new TimevacationUseTimeOfDaily(
						new AttendanceTime(480)
						, new AttendanceTime(480)
						, new AttendanceTime(480)
						, new AttendanceTime(480)
						, Optional.of(new SpecialHdFrameNo(1))
						, new AttendanceTime(480)
						, new AttendanceTime(480))
				, IntervalExemptionTime.defaultValue());
		
		val attTimeDaily = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createEarlyTime(Arrays.asList(leaveEarlyTime)), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));		
		
		assertThat(attTimeDaily.getLeaveEarlyTimeOfDaily())
		.extracting(
					  d -> d.getLeaveEarlyTime().getTime()
					, d -> d.getLeaveEarlyTime().getCalcTime()
					, d -> d.getLeaveEarlyDeductionTime().getTime()
					, d -> d.getLeaveEarlyDeductionTime().getCalcTime()
					, d -> d.getTimePaidUseTime().getTimeAnnualLeaveUseTime()
					, d -> d.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime()
					, d -> d.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime()
					, d -> d.getTimePaidUseTime().getTimeSpecialHolidayUseTime()
					, d -> d.getTimePaidUseTime().getSpecialHolidayFrameNo()
					, d -> d.getTimePaidUseTime().getTimeChildCareHolidayUseTime()
					, d -> d.getTimePaidUseTime().getTimeCareHolidayUseTime()
					, d -> d.getIntervalTime().getExemptionTime()
					, d -> d.getWorkNo())
		.containsExactly(
				Tuple.tuple(
						leaveEarlyTime.getLeaveEarlyTime().getTime() 
					  , leaveEarlyTime.getLeaveEarlyTime().getCalcTime()
					  , leaveEarlyTime.getLeaveEarlyDeductionTime().getTime()
					  , leaveEarlyTime.getLeaveEarlyDeductionTime().getCalcTime()
					  , leaveEarlyTime.getTimePaidUseTime().getTimeAnnualLeaveUseTime()
					  , leaveEarlyTime.getTimePaidUseTime().getTimeCompensatoryLeaveUseTime()
					  , leaveEarlyTime.getTimePaidUseTime().getSixtyHourExcessHolidayUseTime()
					  , leaveEarlyTime.getTimePaidUseTime().getTimeSpecialHolidayUseTime()
					  , leaveEarlyTime.getTimePaidUseTime().getSpecialHolidayFrameNo()
					  , leaveEarlyTime.getTimePaidUseTime().getTimeChildCareHolidayUseTime()
					  , leaveEarlyTime.getTimePaidUseTime().getTimeCareHolidayUseTime()
					  , leaveEarlyTime.getIntervalTime().getExemptionTime()
					  , leaveEarlyTime.getWorkNo())
				);
	}
	
	/**
	 * 外出時間を取得する = empty
	 * 外出時間リスト = empty
	 */
	@Test
	public void getOutingTime_empty() {
		List<OutingTimeOfDaily> outingTimes = Collections.emptyList();
		val attTimeDaily = new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createOutingTime(outingTimes), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		assertThat(attTimeDaily.getOutingTimeOfDaily()).isEmpty();
	}
	
	/**
	 * 外出時間を取得する not empty
	 */
	
	@Test
	public void getOutingTime_not_empty() {
		val reasonTimeChange = new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, EngravingMethod.WEB_STAMP_INPUT);
		val workTimeInformation = new WorkTimeInformation(reasonTimeChange, new TimeWithDayAttr(510));
		
		val actualStamp = new WorkStamp(workTimeInformation, Optional.of(new WorkLocationCD("code")));
		val stamp = new WorkStamp(workTimeInformation, Optional.of(new WorkLocationCD("code")));
		
		val goOut = new TimeActualStamp(actualStamp, stamp, 10);
		val comeBack = new TimeActualStamp(actualStamp, stamp, 10);
		val outTime = new OutingTimeOfDaily(
				  new BreakTimeGoOutTimes(120)
				, GoingOutReason.UNION
				, new TimevacationUseTimeOfDaily(
						new AttendanceTime(480)
						, new AttendanceTime(480)
						, new AttendanceTime(480)
						, new AttendanceTime(480)
						, Optional.of(new SpecialHdFrameNo(1))
						, new AttendanceTime(480)
						, new AttendanceTime(480))
				, OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(120))
						, WithinOutingTotalTime.sameTime(TimeWithCalculation.sameTime(new AttendanceTime(120)))
						, TimeWithCalculation.sameTime(new AttendanceTime(120)))
				, OutingTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(120))
						, WithinOutingTotalTime.sameTime(TimeWithCalculation.sameTime(new AttendanceTime(120)))
						, TimeWithCalculation.sameTime(new AttendanceTime(120)))
				, Arrays.asList(
						new OutingTimeSheet(
						  new OutingFrameNo(1)
						, Optional.of(goOut)
						, new AttendanceTime(1000)
						, new AttendanceTime(1100)
						, GoingOutReason.UNION
						, Optional.of(comeBack)
						))
				);
		
		val attTimeDaily= new AttendanceTimeOfDailyAttendance(WorkScheduleTimeOfDaily.defaultValue()
				, ActualWorkingTimeOfDaily.of(AttendanceTimeOfDailyAttendanceHelper.createOutingTime(Arrays.asList(outTime)), 0, 0, 0, 0)
				, new StayingTimeOfDaily()
				, new AttendanceTimeOfExistMinus(1200)
				, new AttendanceTimeOfExistMinus(3600));
		List<OutingTimeOfDaily> actual = attTimeDaily.getOutingTimeOfDaily();
		
		assertThat(actual)
		.extracting(
					  d -> d.getWorkTime()
					, d -> d.getReason()
					, d -> d.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime()
					, d -> d.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime()
					, d -> d.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime()
					, d -> d.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime()
					, d -> d.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo()
					, d -> d.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime()
					, d -> d.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime()
					, d -> d.getRecordTotalTime().getTotalTime()
					, d -> d.getRecordTotalTime().getWithinTotalTime()
					, d -> d.getRecordTotalTime().getExcessTotalTime()
					, d -> d.getDeductionTotalTime().getTotalTime()
					, d -> d.getDeductionTotalTime().getWithinTotalTime()
					, d -> d.getDeductionTotalTime().getExcessTotalTime()
					)
		.containsExactly(
			Tuple.tuple(
					outTime.getWorkTime()
					, outTime.getReason()
					, outTime.getTimeVacationUseOfDaily().getTimeAnnualLeaveUseTime()
					, outTime.getTimeVacationUseOfDaily().getSixtyHourExcessHolidayUseTime()
					, outTime.getTimeVacationUseOfDaily().getTimeCompensatoryLeaveUseTime()
					, outTime.getTimeVacationUseOfDaily().getTimeSpecialHolidayUseTime()
					, outTime.getTimeVacationUseOfDaily().getSpecialHolidayFrameNo()
					, outTime.getTimeVacationUseOfDaily().getTimeChildCareHolidayUseTime()
					, outTime.getTimeVacationUseOfDaily().getTimeCareHolidayUseTime()
					, outTime.getRecordTotalTime().getTotalTime()
					, outTime.getRecordTotalTime().getWithinTotalTime()
					, outTime.getRecordTotalTime().getExcessTotalTime()
					, outTime.getDeductionTotalTime().getTotalTime()
					, outTime.getDeductionTotalTime().getWithinTotalTime()
					, outTime.getDeductionTotalTime().getExcessTotalTime())
					);
	}	

}
