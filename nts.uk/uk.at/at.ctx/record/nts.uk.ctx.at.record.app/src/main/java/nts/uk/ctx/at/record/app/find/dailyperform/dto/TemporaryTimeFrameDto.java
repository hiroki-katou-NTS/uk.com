package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 日別実績の臨時枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryTimeFrameDto implements ItemConst {

	/** 勤務NO */
//	@AttendanceItemLayout(layout = "A")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer no;

	/** 臨時深夜時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LATE_NIGHT)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer temporaryNightTime;

	/** 臨時時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer temporaryTime;
	
	@Override
	public TemporaryTimeFrameDto clone() {
		return new TemporaryTimeFrameDto(no, temporaryNightTime, temporaryTime);
	}

}
