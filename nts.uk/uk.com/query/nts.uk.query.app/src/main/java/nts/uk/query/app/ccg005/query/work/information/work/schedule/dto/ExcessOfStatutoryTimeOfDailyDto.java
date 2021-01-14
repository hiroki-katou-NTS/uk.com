package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExcessOfStatutoryTimeOfDailyDto {
	// 所定外深夜時間
	private ExcessOfStatutoryMidNightTimeDto excessOfStatutoryMidNightTime;
	// 残業時間
	private OverTimeOfDailyDto overTimeWork;
	// 休出時間
	private HolidayWorkTimeOfDailyDto workHolidayTime;
}
