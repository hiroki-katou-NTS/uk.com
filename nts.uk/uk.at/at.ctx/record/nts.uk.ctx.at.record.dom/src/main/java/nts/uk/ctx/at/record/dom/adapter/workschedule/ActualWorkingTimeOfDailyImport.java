package nts.uk.ctx.at.record.dom.adapter.workschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActualWorkingTimeOfDailyImport {
	/** 総労働時間 */
	private TotalWorkingTimeImport totalWorkingTime;
}
