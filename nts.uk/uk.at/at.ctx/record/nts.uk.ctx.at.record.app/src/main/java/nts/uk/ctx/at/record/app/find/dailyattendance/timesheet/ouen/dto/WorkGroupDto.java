/**
 * 
 */
package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

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

/**
 * @author laitv
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/** 作業グループ */
public class WorkGroupDto  implements ItemConst, AttendanceItemDataGate{
	
	/** 作業CD1 */
	@AttendanceItemLayout(layout = LAYOUT_G, jpPropertyName = WORKCODE1)
	@AttendanceItemValue()
	private String workCD1;
	
	/** 作業CD2 */
	@AttendanceItemLayout(layout = LAYOUT_H, jpPropertyName = WORKCODE2)
	@AttendanceItemValue()
	private String workCD2;
	
	/** 作業CD3 */
	@AttendanceItemLayout(layout = LAYOUT_I, jpPropertyName = WORKCODE3)
	@AttendanceItemValue()
	private String workCD3;
	
	/** 作業CD4 */
	@AttendanceItemLayout(layout = LAYOUT_J, jpPropertyName = WORKCODE4)
	@AttendanceItemValue()
	private String workCD4;
	
	/** 作業CD5 */
	@AttendanceItemLayout(layout = LAYOUT_K, jpPropertyName = WORKCODE5)
	@AttendanceItemValue()
	private String workCD5;

	@Override
	protected WorkGroupDto clone() {
		return new WorkGroupDto(workCD1, workCD2, workCD3, workCD4, workCD5);
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORKCODE1:
			return Optional.of(ItemValue.builder().value(workCD1).valueType(ValueType.ATTR));
		case WORKCODE2:
			return Optional.of(ItemValue.builder().value(workCD2).valueType(ValueType.ATTR));
		case WORKCODE3:
			return Optional.of(ItemValue.builder().value(workCD3).valueType(ValueType.ATTR));
		case WORKCODE4:
			return Optional.of(ItemValue.builder().value(workCD4).valueType(ValueType.ATTR));
		case WORKCODE5:
			return Optional.of(ItemValue.builder().value(workCD5).valueType(ValueType.ATTR));
		default:
			return Optional.empty();
		}
	}
	
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORKCODE1:
			this.workCD1 = value.valueOrDefault(null);
			break;
		case WORKCODE2:
			this.workCD2 = value.valueOrDefault(null);
			break;
		case WORKCODE3:
			this.workCD3 = value.valueOrDefault(null);
			break;
		case WORKCODE4:
			this.workCD4 = value.valueOrDefault(null);
			break;
		case WORKCODE5:
			this.workCD5 = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORKCODE1:
		case WORKCODE2:
		case WORKCODE3:
		case WORKCODE4:
		case WORKCODE5:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}
	
}
