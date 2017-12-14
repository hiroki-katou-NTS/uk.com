package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 割増時間 */
@Data
public class PremiumTimeDto {

	/** 割増時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer premitumTime;
	
	/** 割増時間NO: 割増時間NO */
	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1)
	private String premiumTimeNo;

}
