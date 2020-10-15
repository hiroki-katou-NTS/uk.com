package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTime;

@Getter
/** 時間帯別勤怠の時間 */
public class OuenAttendanceTimeEachTimeSheet implements DomainObject {

	/** 総労働時間: 勤怠時間 */
	private AttendanceTime totalTime;
	
	/** 休憩時間: 勤怠時間 */
	private AttendanceTime breakTime;
	
	/** 所定内時間: 勤怠時間 */
	private AttendanceTime withinTime;
	
	/** 割増時間: 割増時間 */
	private List<PremiumTime> premiumTime;
	
	/** 医療時間: 時間帯別勤怠の医療時間 */
	private List<MedicalCareTimeEachTimeSheet> medicalTime;

	private OuenAttendanceTimeEachTimeSheet(AttendanceTime totalTime, AttendanceTime breakTime,
			AttendanceTime withinTime, List<MedicalCareTimeEachTimeSheet> medicalTime,
			List<PremiumTime> premiumTime) {
		super();
		this.totalTime = totalTime;
		this.breakTime = breakTime;
		this.withinTime = withinTime;
		this.medicalTime = medicalTime;
		this.premiumTime = premiumTime;
	}
	
	public static OuenAttendanceTimeEachTimeSheet create(AttendanceTime totalTime, 
			AttendanceTime breakTime, AttendanceTime withinTime, 
			List<MedicalCareTimeEachTimeSheet> medicalTime, List<PremiumTime> premiumTime) {
		
		return new OuenAttendanceTimeEachTimeSheet(totalTime, breakTime, withinTime, medicalTime, premiumTime);
	}
}
