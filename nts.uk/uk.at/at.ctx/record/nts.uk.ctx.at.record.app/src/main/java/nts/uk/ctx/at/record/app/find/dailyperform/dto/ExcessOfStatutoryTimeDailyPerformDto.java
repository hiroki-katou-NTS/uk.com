package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 日別実績の所定外時間 */
@Data
public class ExcessOfStatutoryTimeDailyPerformDto {

	/** 所定外深夜時間: 所定外深夜時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "所定外深夜時間")
	private ExcessOfStatutoryMidNightTimeDto excessOfStatutoryMidNightTime;

	/** 残業時間: 日別実績の残業時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "残業時間")
	private OverTimeWorkDailyPerformDto overTimeWork;

	/** 休出時間: 日別実績の休出時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "休出時間")
	private WorkHolidayTimeDailyPerformDto workHolidayTime;
}
