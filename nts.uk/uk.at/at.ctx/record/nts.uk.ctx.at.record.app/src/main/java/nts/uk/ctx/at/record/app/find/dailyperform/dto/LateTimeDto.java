package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

/** 日別実績の遅刻時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LateTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 遅刻時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto lateTime;

	/** 遅刻控除時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION)
	private CalcAttachTimeDto lateDeductionTime;

	/** 休暇使用時間/休憩使用時間: 日別実績の時間年休使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = HOLIDAY + USAGE)
	private ValicationUseDto breakUse;

	/** インターバル免除時間/インターバル時間: インターバル免除時間 */
	// @AttendanceItemLayout(layout = "D")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer intervalExemptionTime;

	/** 勤務NO/勤務回数 */
	// @AttendanceItemLayout(layout = "E")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private int no;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case TIME:
		case (DEDUCTION):
			return new CalcAttachTimeDto();
		case (HOLIDAY + USAGE):
			return new ValicationUseDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (TIME):
			return Optional.ofNullable(lateTime);
		case (DEDUCTION):
			return Optional.ofNullable(lateDeductionTime);
		case (HOLIDAY + USAGE):
			return Optional.ofNullable(breakUse);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (TIME):
			lateTime = (CalcAttachTimeDto) value;
			break;
		case (DEDUCTION):
			lateDeductionTime = (CalcAttachTimeDto) value;
			break;
		case (HOLIDAY + USAGE):
			breakUse = (ValicationUseDto) value;
		default:
		}
	}
	
	@Override
	public LateTimeDto clone() {
		return new LateTimeDto(lateTime == null ? null : lateTime.clone(), lateDeductionTime == null ? null : lateDeductionTime.clone(),
				breakUse == null ? null : breakUse.clone(), intervalExemptionTime, no);
	}
}
