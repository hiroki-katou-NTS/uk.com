package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon.AggrPCLogonDivergence;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 集計PCログオン乖離 */
public class TotalPcLogonTime implements ItemConst, AttendanceItemDataGate {

	/** 合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private int totalTime;

	/** 合計日数: 勤怠月間日数 + 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B)
	private double totalDays;

	/** 平均時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = AVERAGE , layout = LAYOUT_C)
	private int averageTime;
	
	public static TotalPcLogonTime from(AggrPCLogonDivergence domain){
		if(domain != null){
			return new TotalPcLogonTime(
						domain.getTotalTime() == null ? 0 : domain.getTotalTime().valueAsMinutes(), 
						domain.getDays() == null ? 0 : domain.getDays().v(), 
						domain.getAverageTime() == null ? 0 : domain.getAverageTime().valueAsMinutes());
		}
		return null;
	}
	
	public AggrPCLogonDivergence toDomain(){
		return AggrPCLogonDivergence.of(new AttendanceDaysMonth(totalDays), 
									new AttendanceTimeMonth(totalTime), 
									new AttendanceTimeMonth(averageTime));
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case TOTAL:
			return Optional.of(ItemValue.builder().value(totalTime).valueType(ValueType.TIME));
		case DAYS:
			return Optional.of(ItemValue.builder().value(totalDays).valueType(ValueType.DAYS));
		case AVERAGE:
			return Optional.of(ItemValue.builder().value(averageTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case TOTAL:
		case DAYS:
		case AVERAGE:
			return PropType.VALUE;
		default:
			return  PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case TOTAL:
			totalTime = value.valueOrDefault(0); break;
		case DAYS:
			totalDays = value.valueOrDefault(0d); break;
		case AVERAGE:
			averageTime = value.valueOrDefault(0); break;
		default:
		}
	}
}
