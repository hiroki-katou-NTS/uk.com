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

@Data
/** フレックス繰越時間 */
@NoArgsConstructor
@AllArgsConstructor
public class FlexCarryforwardTimeDto implements ItemConst {

	/** フレックス繰越勤務時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WORKING_TIME, layout = LAYOUT_A)
	private int flexCarryforwardWorkTime;

	/** フレックス繰越時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private int flexCarryforwardTime;

	/** フレックス繰越不足時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = SHORTAGE, layout = LAYOUT_C)
	private int flexCarryforwardShortageTime;

	public FlexCarryforwardTime toDomain() {
		return FlexCarryforwardTime.of(
						new AttendanceTimeMonthWithMinus(flexCarryforwardTime),
						new AttendanceTimeMonth(flexCarryforwardWorkTime),
						new AttendanceTimeMonth(flexCarryforwardShortageTime),
						new AttendanceTimeMonth(0));
	}
	
	public static FlexCarryforwardTimeDto from(FlexCarryforwardTime domain) {
		FlexCarryforwardTimeDto dto = new FlexCarryforwardTimeDto();
		if(domain != null) {
			dto.setFlexCarryforwardShortageTime(from(domain.getFlexCarryforwardShortageTime()));
			dto.setFlexCarryforwardTime(from(domain.getFlexCarryforwardTime()));
			dto.setFlexCarryforwardWorkTime(from(domain.getFlexCarryforwardWorkTime()));
		}
		return dto;
	}

	private static Integer from(AttendanceTimeMonth domain) {
		return domain == null ? 0 : domain.valueAsMinutes();
	}

	private static Integer from(AttendanceTimeMonthWithMinus domain) {
		return domain == null ? 0 : domain.valueAsMinutes();
	}
}
