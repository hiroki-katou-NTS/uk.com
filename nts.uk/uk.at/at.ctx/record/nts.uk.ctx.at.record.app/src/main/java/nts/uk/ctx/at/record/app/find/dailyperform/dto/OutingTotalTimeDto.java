package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

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
	
	public OutingTotalTime createDeductionTime() {
		return OutingTotalTime.of(
					totalTime == null ? null : totalTime.createTimeWithCalc(),
					withinStatutoryTotalTime == null ? null : withinStatutoryTotalTime.toDomain(),
					excessOfStatutoryTotalTime == null ? null : excessOfStatutoryTotalTime.createTimeWithCalc());
	}
}
