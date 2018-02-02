package nts.uk.ctx.at.record.pub.scheduletime;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * RequestList No91 
 * Export class
 * 勤務予定の勤怠時間
 * @author keisuke_hoshina
 *
 */
public class ScheduleTimePubExport {
	
	//社員ID
	String employeeid;
	
	//年月日
	GeneralDate ymd;
	
	//総労働時間
	AttendanceTime totalWorkTime;
	
	//所定移管
	AttendanceTime preTime;
	
	//実働時間
	AttendanceTime actualWorkTime;
	
	//平日時間
	AttendanceTime weekDayTime;
	
	//休憩時間
	AttendanceTime breakTime;
	
	//育児介護時間
	AttendanceTime childCareTime;
	
	//人件費時間
	AttendanceTime personalExpenceTime;
}