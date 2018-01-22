package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ValueType;

/** 日別実績の臨時枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryTimeFrameDto {

	/** 勤務NO */
//	@AttendanceItemLayout(layout = "A")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer workNo;

	/** 臨時深夜時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "臨時深夜時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer temporaryNightTime;

	/** 臨時時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "臨時時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer temporaryTime;

}
