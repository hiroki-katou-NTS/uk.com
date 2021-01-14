package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HolidayWorkFrameTimeDto {
	// 休出枠時間No
	private Integer holidayFrameNo;
	// 休出時間
	private TimeDivergenceWithCalculationDto holidayWorkTime;
	// 振替時間
	private TimeDivergenceWithCalculationDto transferTime;
	// 事前申請時間
	private Integer beforeApplicationTime;
}
