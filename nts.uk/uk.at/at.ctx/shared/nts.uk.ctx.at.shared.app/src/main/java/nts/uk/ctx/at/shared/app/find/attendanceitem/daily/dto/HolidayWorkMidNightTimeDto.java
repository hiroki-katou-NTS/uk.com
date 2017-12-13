package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 休出深夜時間 */
@Data
public class HolidayWorkMidNightTimeDto {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto time;
	
	/** 法定区分: 休日出勤の法定区分*/
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int holidayWorkOfPrescribedAtr;
}
