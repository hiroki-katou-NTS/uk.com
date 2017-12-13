package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の遅刻時間 */
@Data
public class LateTimeDto {

	/** 勤務NO */
	@AttendanceItemLayout(layout="A")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int workNo;

	/** 休暇使用時間: 日別実績の時間年休使用時間 */
	@AttendanceItemLayout(layout="B")
	private HolidayUseDto breakUse;

	/** 遅刻控除時間: 計算付き時間 */
	@AttendanceItemLayout(layout="C")
	private CalcAttachTimeDto lateDeductionTime;

	/** 遅刻時間: 計算付き時間 */
	@AttendanceItemLayout(layout="D")
	private CalcAttachTimeDto lateTime;

	/** インターバル免除時間: インターバル免除時間 */
	@AttendanceItemLayout(layout="E")
	private IntervalExemptionTimeDto intervalExemptionTime;
}
