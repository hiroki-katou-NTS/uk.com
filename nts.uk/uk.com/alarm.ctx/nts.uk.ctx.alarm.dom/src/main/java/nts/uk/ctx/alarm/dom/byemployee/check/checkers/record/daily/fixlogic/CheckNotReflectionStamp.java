package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily.fixlogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily.FixedLogicDailyByEmployee.RequireCheck;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.reflectcalcategory.ReflectStampCalCategory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 未反映打刻の打刻データがあるかチェック
 */
public class CheckNotReflectionStamp {
	
	public static Iterable<AlarmRecordByEmployee> check(Require require, String employeeId, DatePeriod period){
		return IteratorUtil.iterableFlatten(period.datesBetween(), date -> {
				val optIod = require.getIntegrationOfDaily(employeeId, (GeneralDate)date)
						.map(attendanceLeave -> attendanceLeave.getAttendanceLeave());

				List<AlarmRecordByEmployee> errors = new ArrayList<>();
				
				if(optIod.isPresent()) {
					if(optIod.get().isPresent()) {
						optIod.get().get().getTimeLeavingWorks().forEach(leavingWork ->{
							if(leavingWork.getAttendanceStamp().map(r -> r.isNotReflect()).orElse(false)) {
								errors.add(createError(employeeId, date));
							}
							if(leavingWork.getLeaveStamp().map(r -> r.isNotReflect()).orElse(false)) {
								errors.add(createError(employeeId, date));
							}					
						});
					}
				}
				return () -> errors.iterator();
			});
	}
	
	private static AlarmRecordByEmployee createError(String employeeId, GeneralDate date) {
		return new AlarmRecordByEmployee(
				employeeId, 
				new DateInfo(date), 
				AlarmListCategoryByEmployee.RECORD_DAILY, 
				"ああああ", 
				"ああああ", 
				"ああああ", 
				new AlarmListAlarmMessage("ああああ"));
	}
	
	
	public interface Require{
		Optional<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, GeneralDate date);
	}
}
