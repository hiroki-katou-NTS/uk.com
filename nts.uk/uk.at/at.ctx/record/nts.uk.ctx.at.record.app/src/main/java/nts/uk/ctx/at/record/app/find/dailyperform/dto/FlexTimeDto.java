package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** フレックス時間 */
@Data
public class FlexTimeDto {

	/** フレックス時間: 計算付き時間(マイナス有り) */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "フレックス時間")
	private CalcAttachTimeDto flexTime;

	/** 事前申請時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "事前申請時間")
	@AttendanceItemValue(itemId = 555, type = ValueType.INTEGER)
	private Integer beforeApplicationTime;
}
