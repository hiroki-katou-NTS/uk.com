package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.IllegalMidnightTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 法定外深夜時間 */
public class IllegalMidnightTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 時間 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private TimeMonthWithCalculationDto time;

	/** 事前時間 */
	@AttendanceItemLayout(jpPropertyName = BEFOR_APPLICATION, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.TIME)
	private int beforeTime;
	
	public static IllegalMidnightTimeDto from(IllegalMidnightTime domain) {
		IllegalMidnightTimeDto dto = new IllegalMidnightTimeDto();
		if(domain != null) {
			dto.setBeforeTime(domain.getBeforeTime() == null ? 0 : domain.getBeforeTime().valueAsMinutes());
			dto.setTime(TimeMonthWithCalculationDto.from(domain.getTime()));
		}
		return dto;
	}

	public IllegalMidnightTime toDomain(){
		return IllegalMidnightTime.of(time == null ? new TimeMonthWithCalculation() : time.toDomain(), new AttendanceTimeMonth(beforeTime));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if(BEFOR_APPLICATION.equals(path)) {
			return Optional.of(ItemValue.builder().value(beforeTime).valueType(ValueType.TIME));
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if(TIME.equals(path)) {
			return new TimeMonthWithCalculationDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if(TIME.equals(path)) {
			return Optional.ofNullable(time);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		if(BEFOR_APPLICATION.equals(path)) {
			return PropType.VALUE;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if(BEFOR_APPLICATION.equals(path)) {
			beforeTime = value.valueOrDefault(0);
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if(TIME.equals(path)) {
			time = (TimeMonthWithCalculationDto) value;
		}
	}
}
