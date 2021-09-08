package nts.uk.ctx.at.record.app.find.dailyperform.goout.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutingTimeZoneDto implements ItemConst, AttendanceItemDataGate {

	private int no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = GO_OUT)
	private TimeStampDto outing;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BACK)
	private TimeStampDto comeBack;

	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = REASON)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int reason;

	@Override
	protected OutingTimeZoneDto clone() {
		return new OutingTimeZoneDto(no, outing == null ? null : outing.clone(), 
				comeBack == null ? null : comeBack.clone(), reason);
	}
	
	public GoingOutReason reason() {
		switch (this.reason) {
		case 0:
			return GoingOutReason.PRIVATE;
		case 1:
			return GoingOutReason.PUBLIC;
		case 2:
			return GoingOutReason.COMPENSATION;
		case 3:
		default:
			return GoingOutReason.UNION;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case GO_OUT:
			return Optional.ofNullable(outing);
		case BACK:
			return Optional.ofNullable(comeBack);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case GO_OUT:
			outing = (TimeStampDto) value;
			break;
		case BACK:
			comeBack = (TimeStampDto) value;
			break;
		default:
			break;
		}
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case GO_OUT:
		case BACK:
			return new TimeStampDto();
		default:
			return null;
		}
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		
		if (path.equals(REASON)) {
			return Optional.of(ItemValue.builder().value(reason).valueType(ValueType.ATTR));
		}
		
		return Optional.empty();
	}

	@Override
	public void set(String path, ItemValue value) {
		if (path.equals(REASON)) {
			this.reason = value.valueOrDefault(0);
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		if (path.equals(REASON)) {
			return PropType.VALUE;
		}
		return PropType.OBJECT;
	}
}
