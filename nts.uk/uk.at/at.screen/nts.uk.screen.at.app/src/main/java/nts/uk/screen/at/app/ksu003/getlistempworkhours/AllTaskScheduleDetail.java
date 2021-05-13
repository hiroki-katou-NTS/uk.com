package nts.uk.screen.at.app.ksu003.getlistempworkhours;

import lombok.Value;
import nts.uk.ctx.at.shared.app.query.task.TaskData;

@Value
public class AllTaskScheduleDetail {
	//作業予定詳細
	private TaskScheduleDetailDto taskScheduleDetail;
	//作業
	private TaskData task;
	
}
