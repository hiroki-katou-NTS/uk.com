package nts.uk.ctx.at.record.app.find.monthly.root.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 集計欠勤日数 */
/** 集計特別休暇日数 */
@NoArgsConstructor
@AllArgsConstructor
public class CommonAggregateDaysDto {

	/** 欠勤枠NO: 欠勤枠NO */
	/** 特別休暇枠NO: 特別休暇枠NO */
	private int frameNo;

	/** 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "日数", layout = "A")
	private double days;

	/** 時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "時間", layout = "B")
	private int time;
}
