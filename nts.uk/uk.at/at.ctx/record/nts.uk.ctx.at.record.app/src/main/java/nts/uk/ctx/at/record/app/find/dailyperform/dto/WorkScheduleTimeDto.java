package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 勤務予定時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleTimeDto implements ItemConst {

	/** 合計時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = PLAN)
	@AttendanceItemValue(type = ValueType.TIME)
	private int total;
	
	/** 所定外時間: 勤怠時間 */
	private int excessOfStatutoryTime; 
	
	/** 所定内時間: 勤怠時間 */
	private int withinStatutoryTime;
	
	@Override
	public WorkScheduleTimeDto clone() {
		return new WorkScheduleTimeDto(total, excessOfStatutoryTime, withinStatutoryTime);
	}
}
