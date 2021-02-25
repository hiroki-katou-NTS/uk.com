package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

/** 控除合計時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutingTotalTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 所定外合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = EXCESS_STATUTORY)
	private CalcAttachTimeDto excessOfStatutoryTotalTime;

	/** 所定内合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = WITHIN_STATUTORY)
	private WithinOutingTotalTimeDto withinStatutoryTotalTime;

	/** 合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = TOTAL)
	private CalcAttachTimeDto totalTime;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case EXCESS_STATUTORY:
		case (TOTAL):
			return new CalcAttachTimeDto();
		case (WITHIN_STATUTORY):
			return new WithinOutingTotalTimeDto();
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
			withinStatutoryTotalTime = (WithinOutingTotalTimeDto) value;
			break;
		case (TOTAL):
			totalTime = (CalcAttachTimeDto) value;
		default:
		}
	}

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
