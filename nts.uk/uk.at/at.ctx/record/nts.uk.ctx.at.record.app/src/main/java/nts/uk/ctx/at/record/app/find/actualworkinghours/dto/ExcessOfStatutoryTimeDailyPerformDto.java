package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 日別実績の所定外時間 */
@Data
public class ExcessOfStatutoryTimeDailyPerformDto {

	/** 残業時間: 日別実績の残業時間 */
	@AttendanceItemLayout(layout="A")
	private OverTimeWorkDailyPerformDto overTimeWork;
	
	/** 休出時間: 日別実績の休出時間 */
	@AttendanceItemLayout(layout="B")
	private WorkHolidayTimeDailyPerformDto workHolidayTime;
	
	/** 所定外深夜時間: 所定外深夜時間 */
	@AttendanceItemLayout(layout="C")
	private ExcessOfStatutoryMidNightTimeDto excessOfStatutoryMidNightTime;
}
