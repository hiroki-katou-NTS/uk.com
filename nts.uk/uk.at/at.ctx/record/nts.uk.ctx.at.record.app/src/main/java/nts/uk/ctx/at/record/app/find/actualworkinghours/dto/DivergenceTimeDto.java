package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 乖離時間 */
@Data
public class DivergenceTimeDto {

	/** 控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer deductionTime;

	/** 控除後乖離時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer divergenceTimeAfterDeduction;

	/** 乖離時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer divergenceTime;

	/** 乖離理由コード: 乖離理由コード */
	@AttendanceItemLayout(layout = "D")
	@AttendanceItemValue(itemId = -1)
	private String divergenceReasonCode;

	/** 乖離理由: 乖離理由 */
	@AttendanceItemLayout(layout = "E")
	@AttendanceItemValue(itemId = -1)
	private String divergenceReason;

	/** 乖離時間NO: 乖離時間NO */
	@AttendanceItemLayout(layout = "F")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer divergenceTimeNo;
}
