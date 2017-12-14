package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 休出深夜 */
@Data
public class HolidayMidnightWorkDto {

	/** 休出深夜時間: 休出深夜時間 */
	@AttendanceItemLayout(layout="A")
	private HolidayWorkMidNightTimeDto holidayWorkMidNightTime;
}
