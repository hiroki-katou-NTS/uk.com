package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 遅刻早退の自動計算設定 */
public class AutoCalOfLeaveEarlySettingDto implements ItemConst {

	/** 早退: 早退 */
	/** @see nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveEarlyAttr */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = LEAVE_EARLY)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int leaveEarly;

	/** 遅刻: 早退 */
	/** @see nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveEarlyAttr */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LATE)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int leaveLate;

	@Override
	protected AutoCalOfLeaveEarlySettingDto clone() {
		return new AutoCalOfLeaveEarlySettingDto(leaveEarly, leaveLate);
	}
	
}
