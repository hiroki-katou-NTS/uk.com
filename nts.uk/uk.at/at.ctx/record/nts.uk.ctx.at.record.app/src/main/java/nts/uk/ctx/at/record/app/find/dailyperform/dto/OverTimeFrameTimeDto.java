package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 残業枠時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeFrameTimeDto {

	/** 振替時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "振替時間")
	private CalcAttachTimeDto transferTime;

	/** 残業時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "残業時間")
	private CalcAttachTimeDto overtime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "事前申請時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer beforeApplicationTime;

	/** 指示時間: 勤怠時間 */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer orderTime;

	/** 残業枠NO: 残業枠NO */
	// @AttendanceItemLayout(layout = "E")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer overtimeFrameNo;
}
