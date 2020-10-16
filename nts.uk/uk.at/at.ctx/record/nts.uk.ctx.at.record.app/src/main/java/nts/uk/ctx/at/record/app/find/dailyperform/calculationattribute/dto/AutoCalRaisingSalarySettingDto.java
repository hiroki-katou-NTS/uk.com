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
/** 加給の自動計算設定 */
public class AutoCalRaisingSalarySettingDto implements ItemConst, AttendanceItemDataGate {

	/** 加給: 加給計算区分 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = RAISING_SALARY)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int salaryCalSetting;

	/** 特定加給計算区分: 特定加給計算区分 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = SPECIFIC)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int specificSalaryCalSetting;

	@Override
	protected AutoCalRaisingSalarySettingDto clone() {
		return new AutoCalRaisingSalarySettingDto(salaryCalSetting, specificSalaryCalSetting);
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case RAISING_SALARY:
			return Optional.of(ItemValue.builder().value(salaryCalSetting).valueType(ValueType.ATTR));
		case SPECIFIC:
			return Optional.of(ItemValue.builder().value(specificSalaryCalSetting).valueType(ValueType.ATTR));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case RAISING_SALARY:
			this.salaryCalSetting = value.valueOrDefault(0);
			break;
		case SPECIFIC:
			this.specificSalaryCalSetting = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case SPECIFIC:
		case RAISING_SALARY:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}
}
