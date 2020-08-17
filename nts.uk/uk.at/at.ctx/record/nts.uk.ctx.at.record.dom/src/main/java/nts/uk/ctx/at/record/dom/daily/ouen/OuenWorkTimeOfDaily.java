package nts.uk.ctx.at.record.dom.daily.ouen;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;

@Getter
/** 日別実績の応援作業別勤怠時間 */
public class OuenWorkTimeOfDaily extends AggregateRoot {

	/** 社員ID: 社員ID */
	private String empId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** 応援時間: 日別勤怠の応援作業時間 */
	private OuenWorkTimeOfDailyAttendance ouenTime;

	private OuenWorkTimeOfDaily(String empId, GeneralDate ymd, OuenWorkTimeOfDailyAttendance ouenTime) {
		super();
		this.empId = empId;
		this.ymd = ymd;
		this.ouenTime = ouenTime;
	}
	
	public static OuenWorkTimeOfDaily create(String empId, GeneralDate ymd,
			OuenWorkTimeOfDailyAttendance ouenTime) {

		return new OuenWorkTimeOfDaily(empId, ymd, ouenTime);
	}
}
