package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;

@Getter
/** 日別勤怠の応援作業時間帯 */
public class OuenWorkTimeSheetOfDailyAttendance implements DomainObject {

	/** 応援勤務枠No: 応援勤務枠No */
	private int workNo;

	/** 作業内容: 作業内容 */
	private WorkContent workContent;
	
	/** 時間帯: 時間帯別勤怠の時間帯 */
	private TimeSheetOfAttendanceEachOuenSheet timeSheet;

	private OuenWorkTimeSheetOfDailyAttendance(int workNo, WorkContent workContent, 
			TimeSheetOfAttendanceEachOuenSheet timeSheet) {
		super();
		this.workNo = workNo;
		this.workContent = workContent;
		this.timeSheet = timeSheet;
	}
	
	public static OuenWorkTimeSheetOfDailyAttendance create(int workNo, WorkContent workContent, 
			TimeSheetOfAttendanceEachOuenSheet timeSheet) {
		
		return new OuenWorkTimeSheetOfDailyAttendance(workNo, workContent, timeSheet);
	}
}
