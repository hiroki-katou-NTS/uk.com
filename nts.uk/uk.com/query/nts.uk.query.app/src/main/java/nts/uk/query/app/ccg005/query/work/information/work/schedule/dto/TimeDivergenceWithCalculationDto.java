package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TimeDivergenceWithCalculationDto {
	//時間
	private Integer time;
	//計算時間
	private Integer calcTime;
	//乖離時間
	private Integer divergenceTime;
}
