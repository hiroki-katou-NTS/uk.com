package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.ExcessFlexAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeCurrentMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfExcessOutsideTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 時間外超過のフレックス時間 */
public class FlexTimeOfExcessOutsideTimeDto implements ItemConst, AttendanceItemDataGate {

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
	
	private FlexCurrentMonthDto currentMonth;

	public FlexTimeOfExcessOutsideTime toDmain() {
		return FlexTimeOfExcessOutsideTime.of(
									excessFlexAtr == ExcessFlexAtr.FOR_CONVENIENCE.value 
													? ExcessFlexAtr.FOR_CONVENIENCE : ExcessFlexAtr.PRINCIPLE,
									new AttendanceTimeMonth(principleTime),
									new AttendanceTimeMonth(forConvenienceTime),
									currentMonth == null ? new FlexTimeCurrentMonth() : currentMonth.toDomain());
	}
	
	public static FlexTimeOfExcessOutsideTimeDto from(FlexTimeOfExcessOutsideTime domain) {
		FlexTimeOfExcessOutsideTimeDto dto = new FlexTimeOfExcessOutsideTimeDto();
		if(domain != null) {
			dto.setExcessFlexAtr(domain.getExcessFlexAtr() == null ? 0 : domain.getExcessFlexAtr().value);
			dto.setForConvenienceTime(domain.getForConvenienceTime() == null ? 0 : domain.getForConvenienceTime().valueAsMinutes());
			dto.setPrincipleTime(domain.getPrincipleTime() == null ? 0 : domain.getPrincipleTime().valueAsMinutes());
			dto.setCurrentMonth(FlexCurrentMonthDto.from(domain.getFlexTimeCurrentMonth()));
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case PRINCIPLE:
			return Optional.of(ItemValue.builder().value(principleTime).valueType(ValueType.TIME));
		case (EXCESS + ATTRIBUTE):
			return Optional.of(ItemValue.builder().value(excessFlexAtr).valueType(ValueType.ATTR));
		case CONVENIENCE:
			return Optional.of(ItemValue.builder().value(forConvenienceTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case PRINCIPLE:
		case (EXCESS + ATTRIBUTE):
		case CONVENIENCE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case PRINCIPLE:
			principleTime = value.valueOrDefault(0);
			break;
		case (EXCESS + ATTRIBUTE):
			excessFlexAtr = value.valueOrDefault(0);
			break;
		case CONVENIENCE:
			forConvenienceTime = value.valueOrDefault(0);
			break;
		default:
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case CUR_MONTH:
			return new FlexCurrentMonthDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case CUR_MONTH:
			return Optional.ofNullable(currentMonth);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case CUR_MONTH:
			currentMonth = (FlexCurrentMonthDto) value;
			break;
		default:
		}
	}
}
