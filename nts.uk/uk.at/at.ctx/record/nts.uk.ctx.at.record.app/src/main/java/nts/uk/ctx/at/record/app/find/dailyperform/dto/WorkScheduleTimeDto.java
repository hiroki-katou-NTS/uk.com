package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 勤務予定時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleTimeDto {

	/** 合計時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "予定時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer total;
	
	/** 所定外時間: 勤怠時間 */
	private Integer excessOfStatutoryTime; 
	
	/** 所定内時間: 勤怠時間 */
	private Integer withinStatutoryTime;
}
