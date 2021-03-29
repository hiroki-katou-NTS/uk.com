package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.TimeMonthWithCalculationDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly.Late;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly.LeaveEarly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 早退 + 遅刻 */
public class CommonTimeCountDto implements ItemConst, AttendanceItemDataGate {

	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.COUNT)
	/** 回数: 勤怠月間回数 */
	private int times;

	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	/** 時間: 計算付き月間時間 */
	private TimeMonthWithCalculationDto time;
	
	public static CommonTimeCountDto from(LeaveEarly domain) {
		CommonTimeCountDto dto = new CommonTimeCountDto();
		if(domain != null) {
			dto.setTime(TimeMonthWithCalculationDto.from(domain.getTime()));
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
		}
		return dto;
	}
	
	public static CommonTimeCountDto from(Late domain) {
		CommonTimeCountDto dto = new CommonTimeCountDto();
		if(domain != null) {
			dto.setTime(TimeMonthWithCalculationDto.from(domain.getTime()));
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
		}
		return dto;
	}
	
	public Late toLate() {
		return Late.of(new AttendanceTimesMonth(times),
						time == null ? new TimeMonthWithCalculation() : time.toDomain());
	}
	
	public LeaveEarly toLeaveEarly() {
		return LeaveEarly.of(new AttendanceTimesMonth(times),
							time == null ? new TimeMonthWithCalculation() : time.toDomain());
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if(COUNT.equals(path)) {
			return Optional.of(ItemValue.builder().value(times).valueType(ValueType.COUNT));
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
		if(COUNT.equals(path)) {
			return PropType.VALUE;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if(COUNT.equals(path)) {
			times = value.valueOrDefault(0);
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if(TIME.equals(path)) {
			time = (TimeMonthWithCalculationDto) value;
		}
	}
}
