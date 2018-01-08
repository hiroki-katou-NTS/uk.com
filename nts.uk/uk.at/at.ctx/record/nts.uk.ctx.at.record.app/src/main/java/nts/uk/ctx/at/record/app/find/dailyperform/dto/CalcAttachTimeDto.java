package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 計算付き時間 and 計算付き時間(マイナス有り) */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcAttachTimeDto {

	/** 計算時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName="計算時間")
	@AttendanceItemValue(type = ValueType.INTEGER, getIdFromUtil = true)
	private Integer calcTime;

	/** 時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName="時間")
	@AttendanceItemValue(type = ValueType.INTEGER, getIdFromUtil = true)
	private Integer time;
}
