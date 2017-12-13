package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の勤務実績時間 */
@Data
public class ActualWorkTimeDailyPerformDto {

	/** 割増時間: 日別実績の割増時間 */
	@AttendanceItemLayout(layout="A")
	private PremiumTimeDailyPerformDto premiumTime;

	/** 拘束差異時間: 勤怠時間 */
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=19, type=ValueType.INTEGER)
	private Integer constraintDifferenceTime;

	/** 拘束時間: 総拘束時間 */
	@AttendanceItemLayout(layout="C")
	private ConstraintTimeDto constraintTime;

	/** 時差勤務時間: 勤怠時間 */
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private Integer timeDifferenceWorkingHours;

	/** 総労働時間: 日別実績の総労働時間 */
	@AttendanceItemLayout(layout="E")
	private TotalWorkingTimeDto totalWorkingTime;

	/** 乖離時間: 日別実績の乖離時間 */
	@AttendanceItemLayout(layout="F")
	private DivergenceTimeDailyPerformDto divTime;
}
