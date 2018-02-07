package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

/**
 * 
 * @author keisuke_hoshina
 *
 */
public interface DailyAttendanceTimePub {

	//RequestList No23
	DailyAttendanceTimePubExport calcDailyAttendance(DailyAttendanceTimePubImport imp);
}
