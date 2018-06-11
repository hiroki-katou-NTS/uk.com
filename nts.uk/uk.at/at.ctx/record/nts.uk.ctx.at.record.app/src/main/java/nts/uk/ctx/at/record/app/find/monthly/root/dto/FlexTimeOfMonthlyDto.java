package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexShortDeductTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のフレックス時間 */
public class FlexTimeOfMonthlyDto {

	/** フレックス繰越時間: フレックス繰越時間 */
	@AttendanceItemLayout(jpPropertyName = "フレックス繰越時間", layout = "A")
	private FlexCarryforwardTimeDto carryforwardTime;

	/** フレックス時間: フレックス時間 */
	@AttendanceItemLayout(jpPropertyName = "フレックス時間", layout = "A")
	private FlexTimeMDto flexTime;

	/** フレックス超過時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "フレックス超過時間", layout = "A")
	private int excessTime;

	/** フレックス不足控除時間: フレックス不足控除時間 */
	@AttendanceItemLayout(jpPropertyName = "フレックス不足控除時間", layout = "A")
	private FlexShortDeductTimeDto shortDeductTime;

	/** フレックス不足時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "フレックス不足時間", layout = "A")
	private int shortageTime;

	/** 時間外超過のフレックス時間: 時間外超過のフレックス時間 */
	@AttendanceItemLayout(jpPropertyName = "時間外超過のフレックス時間", layout = "A")
	private FlexTimeOfExcessOutsideTimeDto excessOutsideTime;

	public FlexTimeOfMonthly toDomain() {
		return FlexTimeOfMonthly.of(flexTime == null ? new FlexTime() : flexTime.toDomain(),
				new AttendanceTimeMonth(excessTime),
				new AttendanceTimeMonth(shortageTime),
				carryforwardTime == null ? new FlexCarryforwardTime() : carryforwardTime.toDomain(),
				excessOutsideTime == null ? new FlexTimeOfExcessOutsideTime() : excessOutsideTime.toDmain(),
				shortDeductTime == null ? new FlexShortDeductTime() : shortDeductTime.toDomain());
	}

	public static FlexTimeOfMonthlyDto from(FlexTimeOfMonthly domain) {
		FlexTimeOfMonthlyDto dto = new FlexTimeOfMonthlyDto();
		if(domain != null) {
			dto.setCarryforwardTime(FlexCarryforwardTimeDto.from(domain.getFlexCarryforwardTime()));
			dto.setFlexTime(FlexTimeMDto.from(domain.getFlexTime()));
			dto.setExcessTime(domain.getFlexExcessTime() == null ? null : domain.getFlexExcessTime().valueAsMinutes());
			dto.setShortDeductTime(FlexShortDeductTimeDto.from(domain.getFlexShortDeductTime()));
			dto.setShortageTime(domain.getFlexShortageTime() == null ? null : domain.getFlexShortageTime().valueAsMinutes());
			dto.setExcessOutsideTime(FlexTimeOfExcessOutsideTimeDto.from(domain.getFlexTimeOfExcessOutsideTime()));
		}
		return dto;
	}
}
