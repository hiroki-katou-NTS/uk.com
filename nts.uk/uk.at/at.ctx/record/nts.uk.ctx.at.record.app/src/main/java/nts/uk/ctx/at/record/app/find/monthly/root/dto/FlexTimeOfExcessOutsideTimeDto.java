package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.ExcessFlexAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 時間外超過のフレックス時間 */
public class FlexTimeOfExcessOutsideTimeDto implements ItemConst {

	/** 原則時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = PRINCIPLE, layout = LAYOUT_A)
	private int principleTime;

	/** 超過フレ区分: 超過フレ区分 */
	@AttendanceItemValue(type = ValueType.ATTR)
	@AttendanceItemLayout(jpPropertyName = EXCESS + ATTRIBUTE, layout = LAYOUT_B)
	private int excessFlexAtr;

	/** 便宜上時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = CONVENIENCE, layout = LAYOUT_C)
	private int forConvenienceTime;

	public FlexTimeOfExcessOutsideTime toDmain() {
		return FlexTimeOfExcessOutsideTime.of(
									excessFlexAtr == ExcessFlexAtr.FOR_CONVENIENCE.value 
													? ExcessFlexAtr.FOR_CONVENIENCE : ExcessFlexAtr.PRINCIPLE,
									new AttendanceTimeMonth(principleTime),
									new AttendanceTimeMonth(forConvenienceTime));
	}
	
	public static FlexTimeOfExcessOutsideTimeDto from(FlexTimeOfExcessOutsideTime domain) {
		FlexTimeOfExcessOutsideTimeDto dto = new FlexTimeOfExcessOutsideTimeDto();
		if(domain != null) {
			dto.setExcessFlexAtr(domain.getExcessFlexAtr() == null ? 0 : domain.getExcessFlexAtr().value);
			dto.setForConvenienceTime(domain.getForConvenienceTime() == null ? 0 : domain.getForConvenienceTime().valueAsMinutes());
			dto.setPrincipleTime(domain.getPrincipleTime() == null ? 0 : domain.getPrincipleTime().valueAsMinutes());
		}
		return dto;
	}
}
