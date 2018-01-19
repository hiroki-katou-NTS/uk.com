package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;

/** 割増時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremiumTimeDto {

	/** 割増時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "割増時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer premitumTime;

	/** 割増時間NO: 割増時間NO */
	private Integer premiumTimeNo;

}
