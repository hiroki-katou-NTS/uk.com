package nts.uk.ctx.at.record.app.find.monthly.root.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

@Data
/** 集計欠勤日数 */
/** 集計特別休暇日数 */
@NoArgsConstructor
@AllArgsConstructor
public class CommonAggregateDaysDto implements ItemConst {

	/** 欠勤枠NO: 欠勤枠NO */
	/** 特別休暇枠NO: 特別休暇枠NO */
	private int no;

	/** 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double days;

	/** 時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private int time;
}
