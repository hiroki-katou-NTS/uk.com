package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class DailyAttenTimeParam {
	//社員ID
	String employeeid;
	
	//年月日
	GeneralDate ymd;
	
	//勤務種類コード
	WorkTypeCode workTypeCode;
	
	//就業時間帯コード
	WorkTimeCode workTimeCode;
	
	//勤務開始時刻
	AttendanceTime workStartTime;
	
	//勤務終了時刻
	AttendanceTime workEndTime;
	
	//休憩開始時刻
	AttendanceTime breakStartTime;
	
	//休憩終了時刻
	AttendanceTime breakEndTime;
}
