package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto;

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
public class LogonInfoDto implements ItemConst, AttendanceItemDataGate {

	private int no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = LOGON)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer logOn;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LOGOFF)
	@AttendanceItemValue(type = ValueType.TIME_WITH_DAY)
	private Integer logOff;
	
	@Override
	public LogonInfoDto clone() {
		return new LogonInfoDto(no, logOn, logOff);
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case LOGON:
			return Optional.of(ItemValue.builder().value(logOn).valueType(ValueType.TIME_WITH_DAY));
		case LOGOFF:
			return Optional.of(ItemValue.builder().value(logOff).valueType(ValueType.TIME_WITH_DAY));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case LOGON:
			this.logOn = value.valueOrDefault(null);
			break;
		case LOGOFF:
			this.logOff = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case LOGON:
		case LOGOFF:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
}
