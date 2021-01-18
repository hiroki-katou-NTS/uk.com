package nts.uk.ctx.at.record.app.find.monthly.root.dto;

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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.StgGoStgBackDaysOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の給与用日数 */
public class StraightDaysOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 給与出勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = STRAIGHT_GO, layout = LAYOUT_A)
	private double straightGo;

	/** 給与欠勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = STRAIGHT_BACK, layout = LAYOUT_B)
	private double straightBack;

	/** 給与欠勤日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = STRAIGHT_GO_BACK, layout = LAYOUT_C)
	private double straightGoBack;
	
	public static StraightDaysOfMonthlyDto from(StgGoStgBackDaysOfMonthly domain) {
		StraightDaysOfMonthlyDto dto = new StraightDaysOfMonthlyDto();
		if(domain != null) {
			dto.setStraightGo(domain.getStraightGo() == null ? 0 : domain.getStraightGo().v());
			dto.setStraightBack(domain.getStraightBack() == null ? 0 : domain.getStraightBack().v());
			dto.setStraightGoBack(domain.getStraightGoStraightBack() == null ? 0 : domain.getStraightGoStraightBack().v());
		}
		return dto;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case STRAIGHT_GO:
			return Optional.of(ItemValue.builder().value(straightGo).valueType(ValueType.DAYS));
		case STRAIGHT_BACK:
			return Optional.of(ItemValue.builder().value(straightBack).valueType(ValueType.DAYS));
		case STRAIGHT_GO_BACK:
			return Optional.of(ItemValue.builder().value(straightGoBack).valueType(ValueType.DAYS));
		default:
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case STRAIGHT_GO:
		case STRAIGHT_BACK:
		case STRAIGHT_GO_BACK:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case STRAIGHT_GO:
			straightGo = value.valueOrDefault(0d); break;
		case STRAIGHT_BACK:
			straightBack = value.valueOrDefault(0d); break;
		case STRAIGHT_GO_BACK:
			straightGoBack = value.valueOrDefault(0d); break;
		default:
		}
	}
}
