package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 割増時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremiumTimeDto implements ItemConst {

	/** 割増時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = PREMIUM)
	@AttendanceItemValue(type = ValueType.TIME)
	private int premitumTime;

	/** 割増時間NO: 割増時間NO */
	private int no;

	/** 割増時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = AMOUNT)
	@AttendanceItemValue(type = ValueType.AMOUNT_NUM)
	private int premitumAmount;

	@Override
	public PremiumTimeDto clone() {
		return new PremiumTimeDto(premitumTime, no, premitumAmount);
	}
}
