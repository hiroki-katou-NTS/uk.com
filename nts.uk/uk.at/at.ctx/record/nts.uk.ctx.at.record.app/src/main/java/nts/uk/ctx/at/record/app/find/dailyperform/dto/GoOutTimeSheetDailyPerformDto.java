package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の外出時間 */
@Data
public class GoOutTimeSheetDailyPerformDto {

	/** 時間休暇使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "時間休暇使用時間")
	private ValicationUseDto valicationUseTime;

	/** 控除用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "控除用合計時間")
	private TotalDeductionTimeDto totalTimeForDeduction;

	/** 計上用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "計上用合計時間", needCheckIDWithMethod = "goOutReason")
	private TotalDeductionTimeDto totalTimeForCalc;

	/** 控除用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "控除用コア外合計時間")
	private CalcAttachTimeDto coreTotalTimeForDeduction;

	/** 計上用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "控除用合計時間")
	private CalcAttachTimeDto coreTotalTimeForCalc;

	/** 回数: 休憩外出回数 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "回数")
	@AttendanceItemValue(itemId = { 462, 475, 482, 489 }, type = ValueType.INTEGER)
	private Integer times;

	/** 外出理由: 外出理由 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "外出理由")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private int goOutReason;

	/** 補正後時間帯: 外出時間帯 */
	@AttendanceItemLayout(layout = "H", isList = true, jpPropertyName = "補正後時間帯", listMaxLength = 10, setFieldWithIndex = "outingFrameNo")
	private List<GoOutTimeDto> afterCorrectedTimeSheet;

	public String goOutReason() {
		switch (this.goOutReason) {
		case 0:
			return "私用";
		case 1:
			return "公用";
		case 2:
			return "有償";
		case 3:
			return "組合";
		default:
			return "";
		}
	}
}
