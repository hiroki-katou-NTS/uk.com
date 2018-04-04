package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 集計欠勤日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateAbsenceDaysDto {

	/** 欠勤枠NO: 欠勤枠NO */
	private int absenceFrameNo;

	/** 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "日数", layout = "A")
	private Double days;
}
