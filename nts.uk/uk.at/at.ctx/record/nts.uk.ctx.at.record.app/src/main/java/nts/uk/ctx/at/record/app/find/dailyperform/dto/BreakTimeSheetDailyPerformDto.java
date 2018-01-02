package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;

/** 日別実績の休憩時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	// @AttendanceItemLayout(layout = "D", isList = true, listMaxLength = ?)
	private List<BreakTimeSheetDto> correctedTimeSheet;

	/** 休憩回数: 休憩外出回数 */
	// @AttendanceItemLayout(layout = "E")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer breakTimes;

	public static BreakTimeSheetDailyPerformDto fromBreakTimeOfDaily(BreakTimeOfDaily domain) {
		return domain == null ? null : new BreakTimeSheetDailyPerformDto(getĐeuctionTime(domain.getToRecordTotalTime()),
						getĐeuctionTime(domain.getDeductionTotalTime()),
						domain.getWorkTime() == null ? null : domain.getWorkTime().valueAsMinutes(),
						domain.getBreakTimeSheet() == null ? new ArrayList<>() : ConvertHelper.mapTo(domain.getBreakTimeSheet(),
								(c) -> new BreakTimeSheetDto(
										c.getStartTime().getAfterRoundingTime() == null ? null : c.getStartTime().getAfterRoundingTime().valueAsMinutes(),
										c.getEndTime().getAfterRoundingTime() == null ? null : c.getEndTime().getAfterRoundingTime().valueAsMinutes(), 
										c.getBreakTime() == null ? null :c.getBreakTime().valueAsMinutes(),
										c.getBreakFrameNo().v().intValue())),
						domain.getGooutTimes() == null ? null : domain.getGooutTimes().v());
	}

	private static TotalDeductionTimeDto getĐeuctionTime(DeductionTotalTime domain) {
		return domain == null ? null : new TotalDeductionTimeDto(
				getCalcTime(domain.getExcessOfStatutoryTotalTime()),
				getCalcTime(domain.getWithinStatutoryTotalTime()),
				getCalcTime(domain.getTotalTime()));
	}

	private static CalcAttachTimeDto getCalcTime(TimeWithCalculation domain) {
		return domain == null ? null : new CalcAttachTimeDto(
				domain.getCalcTime() == null ? null : domain.getCalcTime().valueAsMinutes(),
				domain.getTime() == null ? null : domain.getTime().valueAsMinutes());
	}
}
