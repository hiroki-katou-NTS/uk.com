package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** フレックス時間 */
@Data
public class FlexTimeDto {

	/** フレックス時間: 計算付き時間(マイナス有り)*/
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto flexTime;
	/** 事前申請時間: 勤怠時間*/
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int beforeApplicationTime;
}
