package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の外出時間 */
@Data
public class GoOutTimeSheetDailyPerformDto {

	/** 時間休暇使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout="A")
	private ValicationUseDto valicationUseTime;
	
	/** 控除用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout="B")
	private TotalDeductionTimeDto totalTimeForDeduction;
	
	/** 計上用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout="C")
	private TotalDeductionTimeDto totalTimeForCalc;
	
	/** 控除用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout="D")
	private CalcAttachTimeDto coreTotalTimeForDeduction;
	
	/** 計上用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout="E")
	private CalcAttachTimeDto coreTotalTimeForCalc;
	
	/** 回数: 休憩外出回数 */
	@AttendanceItemLayout(layout="F")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int times;
	
	/** 外出理由: 外出理由 */
	@AttendanceItemLayout(layout="G")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int goOutReason;
	
	/** 補正後時間帯: 外出時間帯 */
	@AttendanceItemLayout(layout="H", isList=true)
	private List<GoOutTimeDto> afterCorrectedTimeSheet;
}
