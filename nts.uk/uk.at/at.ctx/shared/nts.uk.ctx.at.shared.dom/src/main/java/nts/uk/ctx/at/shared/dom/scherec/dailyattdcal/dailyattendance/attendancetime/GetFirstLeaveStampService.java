package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 一番早い出勤打刻を探す
 * 検索対象：日別実績の出勤、日別実績の臨時出退勤
 */
public class GetFirstLeaveStampService {

	public static Optional<TimeWithDayAttr> get(Require require, String employeeId, GeneralDate date) {
		val iod = require.getIntegrationOfDailyRecord(employeeId, date);
		if(iod.isPresent()) {
			val atStamp = iod.get().getAttendanceLeave().flatMap(atLeave -> atLeave.getFirstAttendanceTime());
			val tpStamp = iod.get().getTempTime().flatMap(tpLeave -> tpLeave.getFirstAttendanceStamp());
			return getEarlyStamp(atStamp, tpStamp);
		}
		return Optional.empty();
	}
	
	/**
	 * 早い方の出退勤を返す 
	 */
	private static Optional<TimeWithDayAttr> getEarlyStamp(
			Optional<TimeWithDayAttr> targetStamp1,
			Optional<TimeWithDayAttr> targetStamp2) {
		
		if(targetStamp1.isPresent() && targetStamp2.isPresent()) {
			return targetStamp1.get().lessThan(targetStamp2.get())
					? targetStamp1 : targetStamp2 ;
		}
		else if(!targetStamp1.isPresent()) {
			return targetStamp2;
		}
		else if(!targetStamp2.isPresent()){
			return targetStamp1;
		}
		return Optional.empty();
	}
	
	public interface Require{
		Optional<IntegrationOfDaily> getIntegrationOfDailyRecord(String employeeId, GeneralDate date);
	}
}
