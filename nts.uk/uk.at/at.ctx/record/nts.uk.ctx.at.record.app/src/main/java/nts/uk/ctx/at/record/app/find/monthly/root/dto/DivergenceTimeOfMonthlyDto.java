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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.divergencetime.AggregateDivergenceTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.divergencetime.DivergenceAtrOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の乖離時間 -> 集計乖離時間 */
public class DivergenceTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 控除後乖離時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = DEDUCTION + AFTER, layout = LAYOUT_A)
	private int divergenceTimeAfterDeduction;

	/** 控除時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = DEDUCTION, layout = LAYOUT_B)
	private int deductionTime;

	/** 乖離フラグ: 月別実績の乖離フラグ */
	@AttendanceItemValue(type = ValueType.ATTR)
	@AttendanceItemLayout(jpPropertyName = ATTRIBUTE, layout = LAYOUT_C)
	private int divergenceAtr;

	/** 乖離時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = DIVERGENCE, layout = LAYOUT_D)
	private int divergenceTime;

	/** 乖離時間NO: 乖離時間NO */
	private int no;

	public static DivergenceTimeOfMonthlyDto from(AggregateDivergenceTime domain) {
		DivergenceTimeOfMonthlyDto dto = new DivergenceTimeOfMonthlyDto();
		if(domain != null) {
			dto.setDeductionTime(domain.getDeductionTime() == null ? 0 : domain.getDeductionTime().valueAsMinutes());
			dto.setDivergenceAtr(domain.getDivergenceAtr() == null ? 0 : domain.getDivergenceAtr().value);
			dto.setDivergenceTime(domain.getDivergenceTime() == null ? 0 : domain.getDivergenceTime().valueAsMinutes());
			dto.setDivergenceTimeAfterDeduction(domain.getDivergenceTimeAfterDeduction() == null 
					? 0 : domain.getDivergenceTimeAfterDeduction().valueAsMinutes());
			dto.setNo(domain.getDivergenceTimeNo());
		}
		return dto;
	}
	public AggregateDivergenceTime toDomain() {
		return AggregateDivergenceTime.of(no, toAttendanceTimeMonth(divergenceTime), 
				toAttendanceTimeMonth(deductionTime), toAttendanceTimeMonth(divergenceTimeAfterDeduction),
				divergenceAtr());
	}

	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return new AttendanceTimeMonth(time);
	}
	
	public DivergenceAtrOfMonthly divergenceAtr(){
		switch (divergenceAtr) {
		case 0:
			return DivergenceAtrOfMonthly.NORMAL;
		case 1:
			return DivergenceAtrOfMonthly.ALARM;
		default:
			return DivergenceAtrOfMonthly.ERROR;
		}
	}
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (DEDUCTION + AFTER):
			return Optional.of(ItemValue.builder().value(divergenceTimeAfterDeduction).valueType(ValueType.TIME));
		case (DEDUCTION):
			return Optional.of(ItemValue.builder().value(deductionTime).valueType(ValueType.TIME));
		case (ATTRIBUTE):
			return Optional.of(ItemValue.builder().value(divergenceAtr).valueType(ValueType.ATTR));
		case (DIVERGENCE):
			return Optional.of(ItemValue.builder().value(divergenceTime).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (DEDUCTION + AFTER):
		case (DEDUCTION):
		case (ATTRIBUTE):
		case (DIVERGENCE):
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (DEDUCTION + AFTER):
			divergenceTimeAfterDeduction = value.valueOrDefault(0); break;
		case (DEDUCTION):
			deductionTime = value.valueOrDefault(0); break;
		case (ATTRIBUTE):
			divergenceAtr = value.valueOrDefault(0); break;
		case (DIVERGENCE):
			divergenceTime = value.valueOrDefault(0); break;
		default:
		}
	}


}
