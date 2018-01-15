package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 割増時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremiumTimeDto {

	/** 割増時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "割増時間")
	@AttendanceItemValue(itemId = { 426, 427, 428, 429, 430, 431, 432, 433, 434, 435 }, type = ValueType.INTEGER)
	private Integer premitumTime;

	/** 割増時間NO: 割増時間NO */
	private Integer premiumTimeNo;

}
