package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/**
 * 所定内外出合計時間
 * @author keisuke_hoshina
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithinOutingTotalTimeDto implements ItemConst {

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TOTAL)
	CalcAttachTimeDto totalTime;
	
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = WITHIN_STATUTORY)
	CalcAttachTimeDto withinCoreTime;
	
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = EXCESS_STATUTORY)
	CalcAttachTimeDto excessCoreTime;
	
	public static WithinOutingTotalTimeDto from(WithinOutingTotalTime domain) {
		return domain == null ? null : new WithinOutingTotalTimeDto(
						CalcAttachTimeDto.toTimeWithCal(domain.getTotalTime()),
						CalcAttachTimeDto.toTimeWithCal(domain.getWithinCoreTime()),
						CalcAttachTimeDto.toTimeWithCal(domain.getExcessCoreTime()));
	}
	
	public WithinOutingTotalTime toDomain() {
		return WithinOutingTotalTime.of(
					totalTime == null ? null : totalTime.createTimeWithCalc(),
					withinCoreTime == null ? null : withinCoreTime.createTimeWithCalc(),
					excessCoreTime == null ? null : excessCoreTime.createTimeWithCalc());
	}
}
