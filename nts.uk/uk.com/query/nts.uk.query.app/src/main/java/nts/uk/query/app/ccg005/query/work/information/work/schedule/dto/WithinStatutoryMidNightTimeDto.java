package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithinStatutoryMidNightTimeDto {
	// 時間
	private TimeDivergenceWithCalculationDto time;
}
