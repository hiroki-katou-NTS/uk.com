package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;

/** 日別実績の休憩時間 */
@Data
@AllArgsConstructor
public class BreakTimeSheetDailyPerformDto {

	/** 計上用合計時間: 控除合計時間 */
	// @AttendanceItemLayout(layout = "A")
	private TotalDeductionTimeDto toRecordTotalTime;

	/** 控除用合計時間: 控除合計時間 */
	// @AttendanceItemLayout(layout = "B")
	private TotalDeductionTimeDto deductionTotalTime;

	/** 勤務間時間: 勤怠時間 */
	// @AttendanceItemLayout(layout = "C")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer duringWork;

	/** 補正後時間帯: 休憩時間帯 */
	// @AttendanceItemLayout(layout = "D", isList = true)
	private List<BreakTimeSheetDto> correctedTimeSheet;

	/** 休憩回数: 休憩外出回数 */
	// @AttendanceItemLayout(layout = "E")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer breakTimes;

	public static BreakTimeSheetDailyPerformDto fromBreakTimeOfDaily(BreakTimeOfDaily domain) {
		return domain == null ? null
				: new BreakTimeSheetDailyPerformDto(new TotalDeductionTimeDto(new CalcAttachTimeDto(
						domain.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes(),
						domain.getToRecordTotalTime().getExcessOfStatutoryTotalTime().getTime().valueAsMinutes()),
						new CalcAttachTimeDto(
								domain.getToRecordTotalTime().getWithinStatutoryTotalTime().getCalcTime()
										.valueAsMinutes(),
								domain.getToRecordTotalTime().getWithinStatutoryTotalTime().getTime().valueAsMinutes()),
						new CalcAttachTimeDto(
								domain.getToRecordTotalTime().getTotalTime().getCalcTime().valueAsMinutes(),
								domain.getToRecordTotalTime().getTotalTime().getTime().valueAsMinutes())),
						new TotalDeductionTimeDto(
								new CalcAttachTimeDto(
										domain.getDeductionTotalTime().getExcessOfStatutoryTotalTime().getCalcTime()
												.valueAsMinutes(),
										domain.getDeductionTotalTime().getExcessOfStatutoryTotalTime().getTime()
												.valueAsMinutes()),
								new CalcAttachTimeDto(
										domain.getDeductionTotalTime().getWithinStatutoryTotalTime().getCalcTime()
												.valueAsMinutes(),
										domain.getDeductionTotalTime().getWithinStatutoryTotalTime().getTime()
												.valueAsMinutes()),
								new CalcAttachTimeDto(
										domain.getDeductionTotalTime().getTotalTime().getCalcTime().valueAsMinutes(),
										domain.getDeductionTotalTime().getTotalTime().getTime().valueAsMinutes())),
						domain.getWorkTime().valueAsMinutes(),
						ConvertHelper.mapTo(domain.getBreakTimeSheet(),
								(c) -> new BreakTimeSheetDto(c.getStartTime().getAfterRoundingTime().valueAsMinutes(),
										c.getEndTime().getAfterRoundingTime().valueAsMinutes(), c.getBreakTime()
												.valueAsMinutes(),
										c.getBreakFrameNo().v().intValue())),
						domain.getGooutTimes().v());
	}
}
