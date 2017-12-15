package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の遅刻時間 */
@Data
public class LateTimeDto {

	/** 勤務NO/勤務回数 */
//	@AttendanceItemLayout(layout="A")
//	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int workNo;

	/** 遅刻時間: 計算付き時間 */
	@AttendanceItemLayout(layout="A")
	private CalcAttachTimeDto lateTime;

	/** 遅刻控除時間: 計算付き時間 */
	@AttendanceItemLayout(layout="B")
	private CalcAttachTimeDto lateDeductionTime;

	/** 休暇使用時間/休憩使用時間: 日別実績の時間年休使用時間 */
	@AttendanceItemLayout(layout="C")
	private HolidayUseDto breakUse;

	/** インターバル免除時間/インターバル時間: インターバル免除時間 */
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer intervalExemptionTime;
}
