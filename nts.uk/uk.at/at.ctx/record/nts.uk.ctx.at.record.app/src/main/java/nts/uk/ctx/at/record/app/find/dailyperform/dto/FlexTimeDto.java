package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;

/** フレックス時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexTimeDto {

	/** フレックス時間: 計算付き時間(マイナス有り) */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "フレックス時間")
	private CalcAttachTimeDto flexTime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "事前申請時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer beforeApplicationTime;
}
