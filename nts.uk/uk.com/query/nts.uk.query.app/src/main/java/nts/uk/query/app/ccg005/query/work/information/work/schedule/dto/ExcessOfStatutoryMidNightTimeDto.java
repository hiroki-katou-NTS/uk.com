package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExcessOfStatutoryMidNightTimeDto {
	// 時間
	private TimeDivergenceWithCalculationDto time;
	// 事前時間
	private Integer beforeApplicationTime;
}
