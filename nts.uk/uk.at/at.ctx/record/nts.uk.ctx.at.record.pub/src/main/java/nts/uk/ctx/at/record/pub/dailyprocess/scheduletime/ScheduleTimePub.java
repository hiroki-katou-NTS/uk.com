package nts.uk.ctx.at.record.pub.dailyprocess.scheduletime;

import java.util.List;

/**
 * Publisher
 * @author keisuke_hoshina
 *
 */
public interface ScheduleTimePub {
	//RequestList No.91
	ScheduleTimePubExport calculationScheduleTime(ScheduleTimePubImport impTime);
	
	//RequestList No.91 
	//For multi people
	List<ScheduleTimePubExport> calclationScheduleTimeForMultiPeople(List<ScheduleTimePubImport> impList);
}
