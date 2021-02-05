package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.importparam.ChildCareTimeZoneImport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.importparam.OutingTimeZoneImport;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

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
	
	/*
	//勤務開始時刻
	AttendanceTime workStartTime;
	
	//勤務終了時刻
	AttendanceTime workEndTime;
	
	*/
	/**
	 * 時間帯リスト
	 */
	List<TimeZone> lstTimeZone = Collections.emptyList();
	//休憩開始時刻
	List<AttendanceTime> breakStartTime = Collections.emptyList();
	
	//休憩終了時刻
	List<AttendanceTime> breakEndTime = Collections.emptyList();
	
	// add params (redmine #113606)
	//外出時間帯(List)(time đi ra ngoai)
	List<OutingTimeZoneImport> outingTimeSheets = Collections.emptyList();
	//育児時間帯(List)(time chăm soc trẻ)
	List<ChildCareTimeZoneImport> shortWorkingTimeSheets = Collections.emptyList();
	
	
}
