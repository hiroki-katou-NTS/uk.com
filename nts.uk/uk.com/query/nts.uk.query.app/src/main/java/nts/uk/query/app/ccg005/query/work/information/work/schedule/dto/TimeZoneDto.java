package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeZoneDto {
	/** The start. */
	// 開始
	protected Integer start;

	/** The end. */
	// 終了
	protected Integer end;
}
