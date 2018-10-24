package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

/**
 * 
 * @author keisuke_hoshina
 *
 */
public interface DailyAttendanceTimePub {

	//RequestList No.23
	public DailyAttendanceTimePubExport calcDailyAttendance(DailyAttendanceTimePubImport imp);
	
	//RequestList No.13
	public DailyAttendanceTimePubLateLeaveExport calcDailyLateLeave(DailyAttendanceTimePubImport imp);
	
}
