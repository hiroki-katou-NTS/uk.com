package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActualWorkingTimeOfDailyExport {
	// 総労働時間
	private TotalWorkingTimeExport totalWorkingTime;
}
