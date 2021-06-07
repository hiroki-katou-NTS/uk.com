/**
 * 
 */
package nts.uk.ctx.at.record.app.find.dailyattendance.timesheet.ouen.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

/**
 * @author laitv
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/** 応援別勤務の勤務先 */
public class WorkplaceOfWorkEachOuenDto implements ItemConst, AttendanceItemDataGate {
	
	/** 職場: 職場ID */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = WORKPLACEID)
	private String workplaceId;
	
	/** 場所: 勤務場所コード */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = WORKLOCATIONCD)
	private String workLocationCD;
	
	@Override
	protected WorkplaceOfWorkEachOuenDto clone() {
		return new WorkplaceOfWorkEachOuenDto(workplaceId, workLocationCD);
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORKPLACEID:
			return Optional.of(ItemValue.builder().value(workplaceId).valueType(ValueType.CODE));
		case WORKLOCATIONCD:
			return Optional.of(ItemValue.builder().value(workLocationCD).valueType(ValueType.CODE));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORKPLACEID:
			this.workplaceId = value.valueOrDefault(null);
			break;
		case WORKLOCATIONCD:
			this.workLocationCD = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORKPLACEID:
		case WORKLOCATIONCD:
			return PropType.VALUE;
		default:
			break;
		}
		return PropType.OBJECT;
	}

}
