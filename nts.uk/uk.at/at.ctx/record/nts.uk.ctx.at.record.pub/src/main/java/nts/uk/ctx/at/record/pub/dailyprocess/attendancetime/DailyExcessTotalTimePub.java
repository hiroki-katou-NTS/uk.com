package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

/**
 * 
 * @author keisuke_hoshina
 *
 */
public interface DailyExcessTotalTimePub {
	//RequestList No 193
	DailyExcessTotalTimePubExport getExcessTotalTime(DailyExcessTotalTimePubImport imp);
}
