package nts.uk.ctx.at.record.pub.scheduletime;

/**
 * Publisher
 * @author keisuke_hoshina
 *
 */
public interface ScheduleTimePub {
	//RequestList No.91
	ScheduleTimePubExport calculationScheduleTime(ScheduleTimePubImport impTime);
}
