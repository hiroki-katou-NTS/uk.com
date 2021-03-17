package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto;

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

/** 特定日区分 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificDateAttrDto implements ItemConst, AttendanceItemDataGate {

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ATTRIBUTE)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int specificDate;

	private int no;
	
	@Override
	public SpecificDateAttrDto clone() {
		return new SpecificDateAttrDto(specificDate, no);
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (ATTRIBUTE.equals(path)) {
			return Optional.of(ItemValue.builder().value(specificDate).valueType(ValueType.ATTR));
		}
		return Optional.empty();
	}

	@Override
	public void set(String path, ItemValue value) {
		if (ATTRIBUTE.equals(path)) {
			this.specificDate = value.valueOrDefault(0);
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (ATTRIBUTE.equals(path)) {
			return PropType.VALUE;
		}
		return PropType.OBJECT;
	}
	
	
}
