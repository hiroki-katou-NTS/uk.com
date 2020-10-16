package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 自動計算設定 */
public class AutoCalculationSettingDto implements ItemConst, AttendanceItemDataGate {

	/** 計算区分: 時間外の自動計算区分 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = CALC_ATTR)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int calculationAttr;

	/** 上限の設定: 時間外の上限設定 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = UPPER_LIMIT)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int upperLimitSetting;

	@Override
	protected AutoCalculationSettingDto clone() {
		return new AutoCalculationSettingDto(calculationAttr, upperLimitSetting);
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case CALC_ATTR:
			return Optional.of(ItemValue.builder().value(calculationAttr).valueType(ValueType.ATTR));
		case UPPER_LIMIT:
			return Optional.of(ItemValue.builder().value(upperLimitSetting).valueType(ValueType.ATTR));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case CALC_ATTR:
			this.calculationAttr = value.valueOrDefault(0);
			break;
		case UPPER_LIMIT:
			this.upperLimitSetting = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case CALC_ATTR:
		case UPPER_LIMIT:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}
	
}
