package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HolidayWorkMidNightTimeDto {
	// 時間
	private TimeDivergenceWithCalculationDto time;
	// 法定区分
	private Integer statutoryAtr;
}
