package nts.uk.ctx.at.record.app.find.dailyperform.remark.dto;

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
/** 時間帯 */
@AllArgsConstructor
@NoArgsConstructor
public class RemarkDto implements ItemConst, AttendanceItemDataGate {

	@AttendanceItemValue(type = ValueType.TEXT)
	@AttendanceItemLayout(jpPropertyName = REMARK, layout = LAYOUT_A)
	private String remark;
	
	private int no;

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (REMARK.equals(path)) {
			return Optional.of(ItemValue.builder().value(remark).valueType(ValueType.TEXT));
		}
		return Optional.empty();
	}

	@Override
	public void set(String path, ItemValue value) {
		if (REMARK.equals(path)) {
			this.remark = value.valueOrDefault(null);
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (REMARK.equals(path)) {
			return PropType.VALUE;
		}
		return PropType.OBJECT;
	}
}
