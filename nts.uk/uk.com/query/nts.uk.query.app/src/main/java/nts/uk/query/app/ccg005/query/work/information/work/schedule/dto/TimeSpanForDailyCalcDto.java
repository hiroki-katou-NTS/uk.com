package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TimeSpanForDailyCalcDto {
	/** 計算時間帯 */
	private TimeSpanForCalcDto timeSpan;
}
