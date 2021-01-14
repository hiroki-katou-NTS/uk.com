package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HolidayMidnightWorkDto {
	// 休出深夜時間
	private List<HolidayWorkMidNightTimeDto> holidayWorkMidNightTime;
}
