package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ValueType;

/** 日別実績の外出時間 */
@Data
public class GoOutTimeSheetDailyPerformDto {

	/** 時間休暇使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "休暇使用時間", needCheckIDWithMethod = "goOutReason", methodForEnumValues = "goOutReasons")
	private ValicationUseDto valicationUseTime;

	/** 控除用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "控除用合計時間", needCheckIDWithMethod = "goOutReason", methodForEnumValues = "goOutReasons")
	private TotalDeductionTimeDto totalTimeForDeduction;

	/** 計上用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "計上用合計時間", needCheckIDWithMethod = "goOutReason", methodForEnumValues = "goOutReasons")
	private TotalDeductionTimeDto totalTimeForCalc;

	/** 控除用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "控除用コア外合計時間", needCheckIDWithMethod = "goOutReason", methodForEnumValues = "goOutReasons")
	private CalcAttachTimeDto coreTotalTimeForDeduction;

	/** 計上用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "計上用コア外合計時間", needCheckIDWithMethod = "goOutReason", methodForEnumValues = "goOutReasons")
	private CalcAttachTimeDto coreTotalTimeForCalc;

	/** 回数: 休憩外出回数 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "回数", needCheckIDWithMethod = "goOutReason", methodForEnumValues = "goOutReasons")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer times;

	/** 外出理由: 外出理由 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "外出理由")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int goOutReason;

	/** 補正後時間帯: 外出時間帯 */
	@AttendanceItemLayout(layout = "H", jpPropertyName = "補正後時間帯", listMaxLength = 10, indexField = "outingFrameNo")
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
	
	public void goOutReason(String text){
		if(text.contains("私用")){
			this.goOutReason = 0;
		} else if (text.contains("公用")){
			this.goOutReason = 1;
		} else if (text.contains("有償")){
			this.goOutReason = 2;
		} else if (text.contains("組合")){
			this.goOutReason = 2;
		}
	}
	
	public List<String> goOutReasons(){
		return Arrays.asList("私用", "公用", "有償", "組合");
	}
}
