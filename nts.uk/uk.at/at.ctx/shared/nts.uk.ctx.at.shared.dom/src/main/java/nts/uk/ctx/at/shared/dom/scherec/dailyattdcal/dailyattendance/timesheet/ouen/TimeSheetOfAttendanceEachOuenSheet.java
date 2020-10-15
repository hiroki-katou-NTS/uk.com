package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;

@Getter
/** 時間帯別勤怠の時間帯 */
public class TimeSheetOfAttendanceEachOuenSheet implements DomainObject {

	/** 勤務枠No: 勤務NO */
	private WorkNo workNo;
	
	/** 開始: 勤務時刻情報 */
	private Optional<WorkTimeInformation> start;
	
	/** 終了: 勤務時刻情報 */
	private Optional<WorkTimeInformation> end;

	private TimeSheetOfAttendanceEachOuenSheet(WorkNo workNo, Optional<WorkTimeInformation> start,
			Optional<WorkTimeInformation> end) {
		super();
		this.workNo = workNo;
		this.start = start;
		this.end = end;
	}
	
	public static TimeSheetOfAttendanceEachOuenSheet create(WorkNo workNo, 
			Optional<WorkTimeInformation> start, Optional<WorkTimeInformation> end) {
		
		return new TimeSheetOfAttendanceEachOuenSheet(workNo, start, end);
	}
}
