package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HolidayWorkFrameTimeSheetDto {
	// 休出枠No
	private Integer holidayWorkTimeSheetNo;
	// 時間帯
	private TimeSpanForCalcDto timeSheet;
}
