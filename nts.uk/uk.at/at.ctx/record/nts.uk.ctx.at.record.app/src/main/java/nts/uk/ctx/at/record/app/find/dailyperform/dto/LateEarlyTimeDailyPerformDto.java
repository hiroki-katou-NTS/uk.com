package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

/** 日別実績の早退時間 */
/** 日別実績の遅刻時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LateEarlyTimeDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 早退時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto time;

	/** 早退控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION)
	private CalcAttachTimeDto deductionTime;

	/** 休暇使用時間/休憩使用時間: 日別実績の時間休暇使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = HOLIDAY + USAGE)
	private ValicationUseDto valicationUseTime;

	/** インターバル免除時間/インターバル時間: インターバル免除時間 */
	// @AttendanceItemLayout(layout = "D")
	// @AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer intervalExemptionTime;

	/** 勤務NO/勤務回数: 勤務NO */
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
			return Optional.ofNullable(time);
		case (DEDUCTION):
			return Optional.ofNullable(deductionTime);
		case (HOLIDAY + USAGE):
			return Optional.ofNullable(valicationUseTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (TIME):
			time = (CalcAttachTimeDto) value;
			break;
		case (DEDUCTION):
			deductionTime = (CalcAttachTimeDto) value;
			break;
		case (HOLIDAY + USAGE):
			valicationUseTime = (ValicationUseDto) value;
		default:
		}
	}
	
	@Override
	public LateEarlyTimeDailyPerformDto clone() {
		return new LateEarlyTimeDailyPerformDto(time == null ? null : time.clone(),
						deductionTime == null ? null : deductionTime.clone(),
						valicationUseTime == null ? null : valicationUseTime.clone(), intervalExemptionTime, no);
	}
}
