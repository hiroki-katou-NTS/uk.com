package nts.uk.ctx.at.record.pub.dailyprocess.scheduletime;

/**
 * Publisher
 * @author keisuke_hoshina
 *
 */
public interface ScheduleTimePub {
	//RequestList No.91
	ScheduleTimePubExport CalculationScheduleTime(ScheduleTimePubImport impTime);
}
