package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

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

/** 勤務情報 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkInfoDto implements ItemConst, AttendanceItemDataGate {

	/** 勤務種類コード */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = WORK_TYPE)
	@AttendanceItemValue
	private String workTypeCode;

	/** 就業時間帯コード */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = WORK_TIME)
	@AttendanceItemValue
	private String workTimeCode;

	@Override
	protected WorkInfoDto clone() {
		return new WorkInfoDto(workTypeCode, workTimeCode);
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORK_TYPE:
			return Optional.of(ItemValue.builder().value(workTypeCode).valueType(ValueType.CODE));
		case WORK_TIME:
			return Optional.of(ItemValue.builder().value(workTimeCode).valueType(ValueType.CODE));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORK_TYPE:
			this.workTypeCode = value.valueOrDefault(null);
			break;
		case WORK_TIME:
			this.workTimeCode = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORK_TYPE:
		case WORK_TIME:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}
}
