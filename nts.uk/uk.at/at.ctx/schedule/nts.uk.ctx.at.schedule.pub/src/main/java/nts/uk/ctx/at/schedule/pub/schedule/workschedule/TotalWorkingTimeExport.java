package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalWorkingTimeExport {
	//実働時間
	private int actualTime;
}
