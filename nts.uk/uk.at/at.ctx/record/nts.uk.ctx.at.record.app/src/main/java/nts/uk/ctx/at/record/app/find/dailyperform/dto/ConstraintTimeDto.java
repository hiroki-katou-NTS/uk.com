package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 総拘束時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintTimeDto implements ItemConst {

	/** 総拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TOTAL)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer totalConstraintTime;

	/** 深夜拘束時間 : 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LATE_NIGHT)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer lateNightConstraintTime;
	
	@Override
	public ConstraintTimeDto clone() {
		return new ConstraintTimeDto(totalConstraintTime, lateNightConstraintTime);
	}
}
