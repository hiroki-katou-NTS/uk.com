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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の加給時間 + 集計加給時間 */
public class BonusPayTimeOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 加給枠NO: 加給時間項目NO */
	private int no;

	/** 加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private int bonus;
	
	/** 休出加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_B)
	private int holWorkBonus;
	
	/** 休出特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK + SPECIFIC, layout = LAYOUT_C)
	private int holWorkSpecBonus;
	
	/** 特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = SPECIFIC, layout = LAYOUT_D)
	private int specBonus;

	/** 所定内加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WITHIN_STATUTORY, layout = LAYOUT_E)
	private int within;
	
	/** 所定内特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = WITHIN_STATUTORY + SPECIFIC, layout = LAYOUT_F)
	private int withinSpecific;
	
	/** 所定外加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = EXCESS_STATUTORY, layout = LAYOUT_G)
	private int excess;
	
	/** 所定外特定加給時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = EXCESS_STATUTORY + SPECIFIC, layout = LAYOUT_H)
	private int excessSpecific;
	
	public static BonusPayTimeOfMonthlyDto from(AggregateBonusPayTime domain) {
		BonusPayTimeOfMonthlyDto dto = new BonusPayTimeOfMonthlyDto();
		if(domain != null) {
			dto.setBonus(domain.getBonusPayTime() == null ? 0 : domain.getBonusPayTime().valueAsMinutes());
			dto.setNo(domain.getBonusPayFrameNo());
			dto.setHolWorkBonus(domain.getHolidayWorkBonusPayTime() == null ? 0 : domain.getHolidayWorkBonusPayTime().valueAsMinutes());
			dto.setHolWorkSpecBonus(domain.getHolidayWorkSpecificBonusPayTime() == null ? 0 : domain.getHolidayWorkSpecificBonusPayTime().valueAsMinutes());
			dto.setSpecBonus(domain.getSpecificBonusPayTime() == null ? 0 : domain.getSpecificBonusPayTime().valueAsMinutes());
			dto.setWithin(domain.getWithin() == null ? 0 : domain.getWithin().valueAsMinutes());
			dto.setWithinSpecific(domain.getWithinSpecific() == null ? 0 : domain.getWithinSpecific().valueAsMinutes());
			dto.setExcess(domain.getExcess() == null ? 0 : domain.getExcess().valueAsMinutes());
			dto.setExcessSpecific(domain.getExcessSpecific() == null ? 0 : domain.getExcessSpecific().valueAsMinutes());
		}
		return dto;
	}
	
	public AggregateBonusPayTime toDomain(){
		return AggregateBonusPayTime.of(no, toAttendanceTimeMonth(bonus), toAttendanceTimeMonth(specBonus),
				toAttendanceTimeMonth(holWorkBonus), toAttendanceTimeMonth(holWorkSpecBonus),
				toAttendanceTimeMonth(within), toAttendanceTimeMonth(withinSpecific),
				toAttendanceTimeMonth(excess), toAttendanceTimeMonth(excessSpecific));
	}
	
	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time){
		return new AttendanceTimeMonth(time);
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case TIME:
			return Optional.of(ItemValue.builder().value(bonus).valueType(ValueType.TIME));
		case HOLIDAY_WORK:
			return Optional.of(ItemValue.builder().value(holWorkBonus).valueType(ValueType.TIME));
		case (HOLIDAY_WORK + SPECIFIC):
			return Optional.of(ItemValue.builder().value(holWorkSpecBonus).valueType(ValueType.TIME));
		case SPECIFIC:
			return Optional.of(ItemValue.builder().value(specBonus).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case TIME:
		case HOLIDAY_WORK:
		case (HOLIDAY_WORK + SPECIFIC):
		case SPECIFIC:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case TIME:
			bonus = value.valueOrDefault(0); break;
		case HOLIDAY_WORK:
			holWorkBonus = value.valueOrDefault(0); break;
		case (HOLIDAY_WORK + SPECIFIC):
			holWorkSpecBonus = value.valueOrDefault(0); break;
		case SPECIFIC:
			specBonus = value.valueOrDefault(0); break;
		default:
		}
	}

	
}
