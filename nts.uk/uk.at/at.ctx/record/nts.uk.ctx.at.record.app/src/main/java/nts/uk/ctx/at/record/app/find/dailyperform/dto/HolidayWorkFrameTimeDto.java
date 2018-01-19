package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;

/** 休出枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkFrameTimeDto {

	/** 休出時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "休出時間")
	private CalcAttachTimeDto holidayWorkTime;

	/** 振替時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "振替時間")
	private CalcAttachTimeDto transferTime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "事前申請時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer beforeApplicationTime;

	/** 休出枠NO: 休出枠NO */
	// @AttendanceItemLayout(layout = "D")
	// @AttendanceItemValue( type = ValueType.INTEGER)
	private Integer holidayFrameNo;
}
