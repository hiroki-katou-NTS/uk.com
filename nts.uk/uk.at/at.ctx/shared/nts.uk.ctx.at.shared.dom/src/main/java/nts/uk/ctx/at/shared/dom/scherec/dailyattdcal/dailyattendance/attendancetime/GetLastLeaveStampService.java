package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 一番遅い退勤打刻を探す
 * 検索対象：日別実績の出勤、日別実績の臨時出退勤
 */
public class GetLastLeaveStampService {

	public static Optional<TimeWithDayAttr> get(Require require, String employeeId, GeneralDate date) {
		val iod = require.getIntegrationOfDailyRecord(employeeId, date);
		if(iod.isPresent()) {
			val atStamp = iod.get().getAttendanceLeave().flatMap(leave -> leave.getLastLeaveTime());
			val tpStamp = iod.get().getTempTime().flatMap(tpLeave -> tpLeave.getLastLeaveStamp());
			return getLateStamp(atStamp, tpStamp);
		}
		return Optional.empty();
	}
	
	/**
	 * 遅い方の出退勤を返す 
	 */
	private static Optional<TimeWithDayAttr> getLateStamp(
			Optional<TimeWithDayAttr> base,
			Optional<TimeWithDayAttr> compare) {
		
		if(base.isPresent() && compare.isPresent()) {
			return base.get().greaterThan(compare.get())
					? base : compare ;
		}
		else if(!base.isPresent()) {
			return compare;
		}
		else if(!compare.isPresent()){
			return base;
		}
		return Optional.empty();
	}
	
	public interface Require{
		Optional<IntegrationOfDaily> getIntegrationOfDailyRecord(String employeeId, GeneralDate date);
	}
}
