package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 遅刻早退の自動計算設定 */
public class AutoCalOfLeaveEarlySettingDto {

	/** 早退: 早退 */
	/** @see nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveEarlyAttr */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "早退")
	@AttendanceItemValue(type = ValueType.INTEGER, itemId = 639)
	private int leaveEarly;

	/** 遅刻: 早退 */
	/** @see nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveEarlyAttr */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "遅刻")
	@AttendanceItemValue(type = ValueType.INTEGER, itemId = 638)
	private int leaveLate;
}
