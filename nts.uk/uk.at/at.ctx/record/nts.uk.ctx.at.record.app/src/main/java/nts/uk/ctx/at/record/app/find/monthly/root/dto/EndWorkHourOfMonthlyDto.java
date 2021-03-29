package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.EndClockOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の終業時刻 */
public class EndWorkHourOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A)
	@AttendanceItemValue(type = ValueType.COUNT)
	/** 回数: 勤怠月間回数 */
	private int times;

	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.CLOCK)
	/** 合計時刻: 勤怠月間時間 */
	private int totalHours;

	@AttendanceItemLayout(jpPropertyName = AVERAGE, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.CLOCK)
	/** 平均時刻: 勤怠月間時間 */
	private int averageHours;
	
	public static EndWorkHourOfMonthlyDto from(EndClockOfMonthly domain){
		if(domain != null){
			return new EndWorkHourOfMonthlyDto(domain.getTimes() == null ? 0 : domain.getTimes().v(), 
												domain.getTotalClock() == null ? 0 : domain.getTotalClock().valueAsMinutes(), 
												domain.getAverageClock() == null ? 0 : domain.getAverageClock().valueAsMinutes());
		}
		return null;
	}
	
	public EndClockOfMonthly toDomain(){
		return EndClockOfMonthly.of(new AttendanceTimesMonth(times),
									new AttendanceTimeMonth(totalHours),
									new AttendanceTimeMonth(averageHours));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(times).valueType(ValueType.COUNT));
		case TOTAL:
			return Optional.of(ItemValue.builder().value(totalHours).valueType(ValueType.CLOCK));
		case AVERAGE:
			return Optional.of(ItemValue.builder().value(averageHours).valueType(ValueType.CLOCK));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case COUNT:
		case TOTAL:
		case AVERAGE:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case COUNT:
			times = value.valueOrDefault(0); break;
		case TOTAL:
			totalHours = value.valueOrDefault(0); break;
		case AVERAGE:
			averageHours = value.valueOrDefault(0); break;
		default:
		}
	}

	
}
