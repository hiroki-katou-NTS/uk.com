package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexShortDeductTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のフレックス時間 */
public class FlexTimeOfMonthlyDto implements ItemConst {

	/** フレックス繰越時間: フレックス繰越時間 */
	@AttendanceItemLayout(jpPropertyName = CARRY_FORWARD, layout = LAYOUT_A)
	private FlexCarryforwardTimeDto carryforwardTime;

	/** フレックス時間: フレックス時間 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private FlexTimeMDto flexTime;

	/** フレックス超過時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = EXCESS + TIME, layout = LAYOUT_C)
	private int excessTime;

	/** フレックス不足控除時間: フレックス不足控除時間 */
	@AttendanceItemLayout(jpPropertyName = SHORTAGE + DEDUCTION, layout = LAYOUT_D)
	private FlexShortDeductTimeDto shortDeductTime;

	/** フレックス不足時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = SHORTAGE, layout = LAYOUT_E)
	private int shortageTime;

	/** 時間外超過のフレックス時間: 時間外超過のフレックス時間 */
	@AttendanceItemLayout(jpPropertyName = EXCESS, layout = LAYOUT_F)
	private FlexTimeOfExcessOutsideTimeDto excessOutsideTime;

	public FlexTimeOfMonthly toDomain() {
		return FlexTimeOfMonthly.of(flexTime == null ? new FlexTime() : flexTime.toDomain(),
				new AttendanceTimeMonth(excessTime),
				new AttendanceTimeMonth(shortageTime),
				carryforwardTime == null ? new FlexCarryforwardTime() : carryforwardTime.toDomain(),
				excessOutsideTime == null ? new FlexTimeOfExcessOutsideTime() : excessOutsideTime.toDmain(),
				shortDeductTime == null ? new FlexShortDeductTime() : shortDeductTime.toDomain(),
				new AttendanceTimeMonthWithMinus(0));
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
