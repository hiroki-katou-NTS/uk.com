package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.record.dom.stamp.GoOutReason;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 日別実績の外出時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoOutTimeSheetDailyPerformDto {

	/** 時間休暇使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "休暇使用時間", needCheckIDWithMethod = "goOutReason")
	private ValicationUseDto valicationUseTime;

	/** 控除用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "控除用合計時間", needCheckIDWithMethod = "goOutReason")
	private TotalDeductionTimeDto totalTimeForDeduction;

	/** 計上用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "計上用合計時間", needCheckIDWithMethod = "goOutReason")
	private TotalDeductionTimeDto totalTimeForCalc;

	/** 控除用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "控除用コア外合計時間", needCheckIDWithMethod = "goOutReason")
	private CalcAttachTimeDto coreTotalTimeForDeduction;

	/** 計上用コア外合計時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "計上用コア外合計時間", needCheckIDWithMethod = "goOutReason")
	private CalcAttachTimeDto coreTotalTimeForCalc;

	/** 回数: 休憩外出回数 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "回数", needCheckIDWithMethod = "goOutReason")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer times;

	/** 外出理由: 外出理由 */
	@AttendanceItemLayout(layout = "G", jpPropertyName = "外出理由")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int goOutReason;

	/** 補正後時間帯: 外出時間帯 */
	@AttendanceItemLayout(layout = "H", jpPropertyName = "補正後時間帯", listMaxLength = 10, indexField = "outingFrameNo")
	private List<GoOutTimeDto> goOutTime;

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
	
	public static GoOutTimeSheetDailyPerformDto toDto(OutingTimeOfDaily domain){
		return domain == null ? null : new GoOutTimeSheetDailyPerformDto(
				toValicationUse(domain.getTimeVacationUseOfDaily()), 
				TotalDeductionTimeDto.getDeductionTime(domain.getDeductionTotalTime()), 
				TotalDeductionTimeDto.getDeductionTime(domain.getRecordTotalTime()), 
				null,  null, 
				domain.getWorkTime() == null ? null : domain.getWorkTime().v(), 
				domain.getReason().value, 
				ConvertHelper.mapTo(domain.getOutingTimeSheets(), c -> GoOutTimeDto.toDto(c)));
	}
	
	private static ValicationUseDto toValicationUse(TimevacationUseTimeOfDaily valication){
		return valication == null ? null : new ValicationUseDto(
				valication.getTimeAnnualLeaveUseTime() == null ? null : valication.getTimeAnnualLeaveUseTime().valueAsMinutes(), 
				valication.getSixtyHourExcessHolidayUseTime() == null ? null : valication.getSixtyHourExcessHolidayUseTime().valueAsMinutes(), 
				valication.getTimeSpecialHolidayUseTime() == null ? null : valication.getTimeSpecialHolidayUseTime().valueAsMinutes(),
				valication.getTimeCompensatoryLeaveUseTime() == null ? null : valication.getTimeCompensatoryLeaveUseTime().valueAsMinutes());
	}
	
	public OutingTimeOfDaily toDomain(){
		return new OutingTimeOfDaily(times == null ? null : new BreakTimeGoOutTimes(times), 
								ConvertHelper.getEnum(goOutReason, GoOutReason.class), valicationUseTime == null ? null : valicationUseTime.toDomain(), 
								totalTimeForCalc == null ? null : totalTimeForCalc.createDeductionTime(),
								totalTimeForDeduction == null ? null : totalTimeForDeduction.createDeductionTime(), 
								ConvertHelper.mapTo(goOutTime, c -> c.toDomain()));
	}
}
