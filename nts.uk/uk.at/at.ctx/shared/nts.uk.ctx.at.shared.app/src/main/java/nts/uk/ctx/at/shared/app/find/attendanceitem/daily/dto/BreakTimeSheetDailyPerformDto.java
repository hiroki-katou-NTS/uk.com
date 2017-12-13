package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の休憩時間 */
@Data
public class BreakTimeSheetDailyPerformDto {

	/** 計上用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "A")
	private TotalDeductionTimeDto toRecordTotalTime;

	/** 控除用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "B")
	private TotalDeductionTimeDto deductionTotalTime;

	/** 勤務間時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private int duringWork;

	/** 補正後時間帯: 休憩時間帯 */
	@AttendanceItemLayout(layout = "D", isList = true)
	private List<BreakTimeSheetDto> correctedTimeSheet;

	/** 休憩回数: 休憩外出回数 */
	@AttendanceItemLayout(layout = "E")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private int breakTimes;
}
