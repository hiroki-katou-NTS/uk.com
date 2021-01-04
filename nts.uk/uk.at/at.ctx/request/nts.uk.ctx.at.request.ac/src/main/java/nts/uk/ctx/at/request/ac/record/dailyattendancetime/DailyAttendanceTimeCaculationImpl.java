

package nts.uk.ctx.at.request.ac.record.dailyattendancetime;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class DailyAttendanceTimeCaculationImpl implements DailyAttendanceTimeCaculation {
	@Inject
	private DailyAttendanceTimePub dailyAttendanceTimePub;
	@Override
	public DailyAttendanceTimeCaculationImport getCalculation(
			String employeeID,
			GeneralDate ymd,
			String workTypeCode,
			String workTimeCode,
			List<TimeZone> lstTimeZone,
			List<Integer> breakStartTimes,
			List<Integer> breakEndTime) {
		DailyAttendanceTimePubImport dailyAttendanceTimePubImport = new DailyAttendanceTimePubImport();
		dailyAttendanceTimePubImport.setEmployeeid(employeeID);
		dailyAttendanceTimePubImport.setYmd(ymd);
		dailyAttendanceTimePubImport.setWorkTypeCode(workTypeCode == null ? null : new WorkTypeCode(workTypeCode));
		dailyAttendanceTimePubImport.setWorkTimeCode(workTimeCode== null ? null : new WorkTimeCode(workTimeCode));
		dailyAttendanceTimePubImport.setLstTimeZone(lstTimeZone);
		dailyAttendanceTimePubImport.setBreakStartTime(getTimes(breakStartTimes));
		dailyAttendanceTimePubImport.setBreakEndTime(getTimes(breakEndTime));
		//1日分の勤怠時間を仮計算
		DailyAttendanceTimePubExport dailyAttendanceTimePubExport = dailyAttendanceTimePub.calcDailyAttendance(dailyAttendanceTimePubImport);
		
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = new DailyAttendanceTimeCaculationImport(
				convertMapOverTime(dailyAttendanceTimePubExport.getOverTime()),
				convertMapHolidayWork(dailyAttendanceTimePubExport.getHolidayWorkTime()),
				convertBonusTime(dailyAttendanceTimePubExport.getBonusPayTime()),
				convertBonusTime(dailyAttendanceTimePubExport.getSpecBonusPayTime()),
				convert(dailyAttendanceTimePubExport.getFlexTime()),
				convert(dailyAttendanceTimePubExport.getMidNightTime()),
				dailyAttendanceTimePubExport.getTimeOutSideMidnight(),
				dailyAttendanceTimePubExport.getCalOvertimeMidnight(),
				getCalHolidayMidnight(dailyAttendanceTimePubExport.getCalHolidayMidnight()));
		return dailyAttendanceTimeCaculationImport;
	}
	
    public Map<Integer, Integer> getCalHolidayMidnight(Map<StaturoryAtrOfHolidayWork,AttendanceTime> maps) {
    	Map<Integer, Integer> results = new HashMap<Integer, Integer>();
    	for(Map.Entry<StaturoryAtrOfHolidayWork,AttendanceTime> entry : maps.entrySet()){
    		results.put(entry.getKey().ordinal(), entry.getValue().v());
		}
    	return results;
    }
	
	
	private List<AttendanceTime> getTimes(List<Integer> inputTimes) {
		List<AttendanceTime> startTimes = !CollectionUtil.isEmpty(inputTimes)
				? inputTimes.stream().map(x -> new AttendanceTime(x)).collect(Collectors.toList())
				: Collections.emptyList();
		return startTimes;
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
		TimeZone timeZone = new TimeZone(new TimeWithDayAttr(dailyAttenTimeParam.getWorkStartTime().valueAsMinutes()),new TimeWithDayAttr(dailyAttenTimeParam.getWorkEndTime().valueAsMinutes()));
		dailyAttendanceTimePubImport.getLstTimeZone().add(timeZone);
		dailyAttendanceTimePubImport.setBreakStartTime(dailyAttenTimeParam.getBreakStartTime() == null ? Collections.emptyList() : Arrays.asList(dailyAttenTimeParam.getBreakStartTime()));
		dailyAttendanceTimePubImport.setBreakEndTime(dailyAttenTimeParam.getBreakEndTime() == null ? Collections.emptyList() : Arrays.asList(dailyAttenTimeParam.getBreakEndTime()));
		DailyAttendanceTimePubLateLeaveExport result = dailyAttendanceTimePub.calcDailyLateLeave(dailyAttendanceTimePubImport);
		return new DailyAttenTimeLateLeaveImport(result.getLateTime(), result.getLeaveEarlyTime());
	}
}
