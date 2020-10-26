package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

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
	
	@Override
	public WithinOutingTotalTimeDto clone() {
		return new WithinOutingTotalTimeDto(
							totalTime == null ? null : totalTime.clone(),
							withinCoreTime == null ? null : withinCoreTime.clone(),
							excessCoreTime == null ? null : excessCoreTime.clone());
	}
	
	public WithinOutingTotalTime toDomain() {
		return WithinOutingTotalTime.of(
					totalTime == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) : totalTime.createTimeWithCalc(),
					withinCoreTime == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) : withinCoreTime.createTimeWithCalc(),
					excessCoreTime == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) : excessCoreTime.createTimeWithCalc());
	}
	
	public static WithinOutingTotalTime createEmpty() {
		return WithinOutingTotalTime.of(TimeWithCalculation.sameTime(AttendanceTime.ZERO),
										TimeWithCalculation.sameTime(AttendanceTime.ZERO),
										TimeWithCalculation.sameTime(AttendanceTime.ZERO));
	}
}
