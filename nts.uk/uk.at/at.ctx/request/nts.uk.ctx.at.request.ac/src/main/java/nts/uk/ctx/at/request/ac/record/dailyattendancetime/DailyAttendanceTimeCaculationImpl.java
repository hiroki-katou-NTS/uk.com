

package nts.uk.ctx.at.request.ac.record.dailyattendancetime;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubImport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubLateLeaveExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttenTimeLateLeaveImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttenTimeParam;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class DailyAttendanceTimeCaculationImpl implements DailyAttendanceTimeCaculation {
	@Inject
	private DailyAttendanceTimePub dailyAttendanceTimePub;
	@Override
	public DailyAttendanceTimeCaculationImport getCalculation(String employeeID, GeneralDate ymd, String workTypeCode,
			String workTimeCode, Integer workStartTime, Integer workEndTime, Integer breakStartTime,
			Integer breakEndTime) {
		DailyAttendanceTimePubImport dailyAttendanceTimePubImport = new DailyAttendanceTimePubImport();
		dailyAttendanceTimePubImport.setEmployeeid(employeeID);
		dailyAttendanceTimePubImport.setYmd(ymd);
		dailyAttendanceTimePubImport.setWorkTypeCode(workTypeCode == null ? null : new WorkTypeCode(workTypeCode));
		dailyAttendanceTimePubImport.setWorkTimeCode(workTimeCode== null ? null : new WorkTimeCode(workTimeCode));
		dailyAttendanceTimePubImport.setWorkStartTime( workStartTime == null ? null : new AttendanceTime(workStartTime));
		dailyAttendanceTimePubImport.setWorkEndTime(workEndTime == null? null: new AttendanceTime( workEndTime));
		dailyAttendanceTimePubImport.setBreakStartTime( breakStartTime== null ? null : new AttendanceTime(breakStartTime));
		dailyAttendanceTimePubImport.setBreakEndTime(breakEndTime == null ? null : new AttendanceTime( breakEndTime));
		
		DailyAttendanceTimePubExport dailyAttendanceTimePubExport = dailyAttendanceTimePub.calcDailyAttendance(dailyAttendanceTimePubImport);
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = new DailyAttendanceTimeCaculationImport(convertMapOverTime(dailyAttendanceTimePubExport.getOverTime()),
				convertMapHolidayWork(dailyAttendanceTimePubExport.getHolidayWorkTime()),
				convertBonusTime(dailyAttendanceTimePubExport.getBonusPayTime()),
				convertBonusTime(dailyAttendanceTimePubExport.getSpecBonusPayTime()),
				convert(dailyAttendanceTimePubExport.getFlexTime()),
				convert(dailyAttendanceTimePubExport.getMidNightTime()));
		return dailyAttendanceTimeCaculationImport;
	}
	
	/**
	 * @param timeCal
	 * @return
	 */
	private TimeWithCalculationImport convert(TimeWithCalculation timeCal){
		return new TimeWithCalculationImport(timeCal.getTime() == null ? null : timeCal.getTime().v(), timeCal.getCalcTime() == null ? null : timeCal.getCalcTime().v());
	}
	
	/**
	 * @param overTime
	 * @return
	 */
	private Map<Integer,TimeWithCalculationImport> convertMapOverTime(Map<OverTimeFrameNo,TimeWithCalculation> overTime){
		Map<Integer,TimeWithCalculationImport> timeWithCal = new HashMap<>();
		for(Map.Entry<OverTimeFrameNo,TimeWithCalculation> entry : overTime.entrySet()){
			timeWithCal.put(entry.getKey().v(), convert(entry.getValue()));
		}
		return timeWithCal;
	}
	/**
	 * @param holidayWork
	 * @return
	 */
	private Map<Integer,TimeWithCalculationImport> convertMapHolidayWork(Map<HolidayWorkFrameNo,TimeWithCalculation> holidayWork){
		Map<Integer,TimeWithCalculationImport> timeWithCal = new HashMap<>();
		for(Map.Entry<HolidayWorkFrameNo,TimeWithCalculation> entry : holidayWork.entrySet()){
			timeWithCal.put(entry.getKey().v(), convert(entry.getValue()));
		}
		return timeWithCal;
	}
	/**
	 * @param bonusTime
	 * @return
	 */
	private Map<Integer,Integer> convertBonusTime(Map<Integer,AttendanceTime> bonusTime){
		Map<Integer,Integer> timeWithCal = new HashMap<>();
		for(Map.Entry<Integer,AttendanceTime> entry : bonusTime.entrySet()){
			timeWithCal.put(entry.getKey(), entry.getValue() == null ? null : entry.getValue().v());
		}
		return timeWithCal;
	}

	@Override
	public DailyAttenTimeLateLeaveImport calcDailyLateLeave(DailyAttenTimeParam dailyAttenTimeParam) {
		DailyAttendanceTimePubImport dailyAttendanceTimePubImport = new DailyAttendanceTimePubImport();
		dailyAttendanceTimePubImport.setEmployeeid(dailyAttenTimeParam.getEmployeeid());
		dailyAttendanceTimePubImport.setYmd(dailyAttenTimeParam.getYmd());
		dailyAttendanceTimePubImport.setWorkTypeCode(dailyAttenTimeParam.getWorkTypeCode());
		dailyAttendanceTimePubImport.setWorkTimeCode(dailyAttenTimeParam.getWorkTimeCode());
		dailyAttendanceTimePubImport.setWorkStartTime(dailyAttenTimeParam.getWorkStartTime());
		dailyAttendanceTimePubImport.setWorkEndTime(dailyAttenTimeParam.getWorkEndTime());
		dailyAttendanceTimePubImport.setBreakStartTime(dailyAttenTimeParam.getBreakStartTime());
		dailyAttendanceTimePubImport.setBreakEndTime(dailyAttenTimeParam.getBreakEndTime());
		DailyAttendanceTimePubLateLeaveExport result = dailyAttendanceTimePub.calcDailyLateLeave(dailyAttendanceTimePubImport);
		return new DailyAttenTimeLateLeaveImport(result.getLateTime(), result.getLeaveEarlyTime());
	}
}
