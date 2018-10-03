package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * RequestList No23
 * input class
 * @author keisuke_hoshina
 *
 */
@Getter
@Setter
public class DailyAttendanceTimePubImport {
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
	List<AttendanceTime> breakStartTime;
	
	//休憩終了時刻
	List<AttendanceTime> breakEndTime;
}
