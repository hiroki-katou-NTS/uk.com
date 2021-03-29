package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

/** 控除合計時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalDeductionTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 所定外合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = EXCESS_STATUTORY)
	private CalcAttachTimeDto excessOfStatutoryTotalTime;

	/** 所定内合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = WITHIN_STATUTORY)
	private CalcAttachTimeDto withinStatutoryTotalTime;

	/** 合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = TOTAL)
	private CalcAttachTimeDto totalTime;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case EXCESS_STATUTORY:
		case (WITHIN_STATUTORY):
		case (TOTAL):
			return new CalcAttachTimeDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (EXCESS_STATUTORY):
			return Optional.ofNullable(excessOfStatutoryTotalTime);
		case (WITHIN_STATUTORY):
			return Optional.ofNullable(withinStatutoryTotalTime);
		case (TOTAL):
			return Optional.ofNullable(totalTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (EXCESS_STATUTORY):
			excessOfStatutoryTotalTime = (CalcAttachTimeDto) value;
			break;
		case (WITHIN_STATUTORY):
			withinStatutoryTotalTime = (CalcAttachTimeDto) value;
			break;
		case (TOTAL):
			totalTime = (CalcAttachTimeDto) value;
		default:
		}
	}

	public static TotalDeductionTimeDto getDeductionTime(DeductionTotalTime domain) {
		return domain == null ? null : new TotalDeductionTimeDto(
						CalcAttachTimeDto.toTimeWithCal(domain.getExcessOfStatutoryTotalTime()),
						CalcAttachTimeDto.toTimeWithCal(domain.getWithinStatutoryTotalTime()),
						CalcAttachTimeDto.toTimeWithCal(domain.getTotalTime()));
	}

	@Override
	public TotalDeductionTimeDto clone() {
		return new TotalDeductionTimeDto(excessOfStatutoryTotalTime == null ? null : excessOfStatutoryTotalTime.clone(),
										withinStatutoryTotalTime == null ? null : withinStatutoryTotalTime.clone(),
										totalTime == null ? null : totalTime.clone());
	}
	
	public DeductionTotalTime createDeductionTime() {
		return DeductionTotalTime.of(
					totalTime == null ? null : totalTime.createTimeWithCalc(),
					withinStatutoryTotalTime == null ? null : withinStatutoryTotalTime.createTimeWithCalc(),
					excessOfStatutoryTotalTime == null ? null : excessOfStatutoryTotalTime.createTimeWithCalc());
	}
}
