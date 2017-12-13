package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/**  残業枠時間帯 */
@Data
public class OverTimeFrameTimeSheetDto {

	/**  時間帯: 計算用時間帯 */
	@AttendanceItemLayout(layout="A")
	private TimeSpanForCalcDto timeSheet;
	/**  残業枠NO: 残業枠NO */
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int overtimeFrameNo;
}
