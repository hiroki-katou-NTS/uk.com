package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OverTimeFrameTimeSheetDto {
	// 時間帯
	private TimeSpanForDailyCalcDto timeSpan;

	// 残業枠No
	private Integer frameNo;
}
