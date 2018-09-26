package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 控除合計時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutingTotalTimeDto implements ItemConst {

	/** 所定外合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = EXCESS_STATUTORY)
	private CalcAttachTimeDto excessOfStatutoryTotalTime;

	/** 所定内合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = WITHIN_STATUTORY)
	private WithinOutingTotalTimeDto withinStatutoryTotalTime;

	/** 合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = TOTAL)
	private CalcAttachTimeDto totalTime;

	public static OutingTotalTimeDto from(OutingTotalTime domain) {
		return domain == null ? null : new OutingTotalTimeDto(
						CalcAttachTimeDto.toTimeWithCal(domain.getExcessTotalTime()),
						WithinOutingTotalTimeDto.from(domain.getWithinTotalTime()),
						CalcAttachTimeDto.toTimeWithCal(domain.getTotalTime()));
	}
	
	@Override
	public OutingTotalTimeDto clone() {
		return new OutingTotalTimeDto(
						excessOfStatutoryTotalTime == null ? null : excessOfStatutoryTotalTime.clone(),
						withinStatutoryTotalTime == null ? null : withinStatutoryTotalTime.clone(),
						totalTime == null ? null : totalTime.clone());
	}
	
	public OutingTotalTime createDeductionTime() {
		return OutingTotalTime.of(
					totalTime == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) : totalTime.createTimeWithCalc(),
					withinStatutoryTotalTime == null ? WithinOutingTotalTimeDto.createEmpty() : withinStatutoryTotalTime.toDomain(),
					excessOfStatutoryTotalTime == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) : excessOfStatutoryTotalTime.createTimeWithCalc());
	}
	
	public static OutingTotalTime createEmpty() {
		return OutingTotalTime.of(TimeWithCalculation.sameTime(AttendanceTime.ZERO),
									WithinOutingTotalTimeDto.createEmpty(),
									TimeWithCalculation.sameTime(AttendanceTime.ZERO));
	}
}
