package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlexTimeDto {
	// フレックス時間
	private TimeDivergenceWithCalculationMinusExistDto flexTime;
	// 申請時間
	private Integer beforeApplicationTime;
}
