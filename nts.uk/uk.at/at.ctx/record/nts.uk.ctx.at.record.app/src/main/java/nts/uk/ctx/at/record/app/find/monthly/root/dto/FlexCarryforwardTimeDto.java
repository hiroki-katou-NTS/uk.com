package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
/** フレックス繰越時間 */
@NoArgsConstructor
@AllArgsConstructor
public class FlexCarryforwardTimeDto implements ItemConst {

	/** フレックス繰越勤務時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = WORKING_TIME, layout = LAYOUT_A)
	private int flexCarryforwardWorkTime;

	/** フレックス繰越時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private int flexCarryforwardTime;

	/** フレックス繰越不足時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = SHORTAGE, layout = LAYOUT_C)
	private int flexCarryforwardShortageTime;

	public FlexCarryforwardTime toDomain() {
		return FlexCarryforwardTime.of(
						new AttendanceTimeMonth(flexCarryforwardTime),
						new AttendanceTimeMonth(flexCarryforwardWorkTime),
						new AttendanceTimeMonth(flexCarryforwardShortageTime));
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
}
