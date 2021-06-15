package nts.uk.ctx.at.record.dom.adapter.workschedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalWorkingTimeImport {
	/** 総労働時間  */
	private int actualTime;
	
	/** 勤務回数 */
	private int workTimes;
}
