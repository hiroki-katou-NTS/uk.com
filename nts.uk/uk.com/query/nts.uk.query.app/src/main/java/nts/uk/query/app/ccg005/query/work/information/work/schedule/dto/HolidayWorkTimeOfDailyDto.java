package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HolidayWorkTimeOfDailyDto {
	// 休出枠時間帯
	private List<HolidayWorkFrameTimeSheetDto> holidayWorkFrameTimeSheet;
	// 休出枠時間
	private List<HolidayWorkFrameTimeDto> holidayWorkFrameTime;
	// 休出深夜
	private HolidayMidnightWorkDto holidayMidNightWork;
	// 休出拘束時間
	private Integer holidayTimeSpentAtWork;
}
