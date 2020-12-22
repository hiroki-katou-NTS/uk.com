package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseDays;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 積立年休使用数 */
public class DayUsedNumberDto implements ItemConst, AttendanceItemDataGate {
	
	/** 使用日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double usedDays;
	
	/** 使用日数付与前 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_B)
	private double usedDaysBeforeGrant;
	
	/** 使用日数付与後 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_C)
	private Double usedDaysAfterGrant;
	
	public static DayUsedNumberDto from(ReserveLeaveUsedNumber domain){
		return domain == null ? null : new DayUsedNumberDto(domain.getUsedDays().v(), 
											domain.getUsedDaysBeforeGrant().v(), 
											domain.getUsedDaysAfterGrant().isPresent() ? domain.getUsedDaysAfterGrant().get().v() : null);
	}
	
	public ReserveLeaveUsedNumber toDomain(){
		return ReserveLeaveUsedNumber.of(new ReserveLeaveUsedDayNumber(usedDays), new ReserveLeaveUsedDayNumber(usedDaysBeforeGrant), 
				Optional.ofNullable(usedDaysAfterGrant == null ? null : new ReserveLeaveUsedDayNumber(usedDaysAfterGrant)));
	}
	
	public static DayUsedNumberDto from(SpecialLeaveUseDays domain){
		return domain == null ? null : new DayUsedNumberDto(domain.getUseDays().v(), domain.getBeforeUseGrantDays().v(), 
				domain.getAfterUseGrantDays().isPresent() ? domain.getAfterUseGrantDays().get().v() : null);
	}
	
	public SpecialLeaveUseDays toSpecial(){
		return new SpecialLeaveUseDays(new SpecialLeaveRemainDay(usedDays), new SpecialLeaveRemainDay(usedDaysBeforeGrant), 
				Optional.ofNullable(usedDaysAfterGrant == null ? null : new SpecialLeaveRemainDay(usedDaysAfterGrant)));
	}
	
	public static DayUsedNumberDto from(AnnualLeaveUsedDays domain) {
		return domain == null ? null : new DayUsedNumberDto(
									domain.getUsedDays().v(), 
									domain.getUsedDaysBeforeGrant().v(),
									domain.getUsedDaysAfterGrant().isPresent() ? domain.getUsedDaysAfterGrant().get().v() : null);
	}
	
	public AnnualLeaveUsedDays toAnnual() {
		return AnnualLeaveUsedDays.of(
								new AnnualLeaveUsedDayNumber(usedDays), 
								new AnnualLeaveUsedDayNumber(usedDaysBeforeGrant), 
								Optional.ofNullable(usedDaysAfterGrant == null 
										? null : new AnnualLeaveUsedDayNumber(usedDaysAfterGrant)));
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(usedDays).valueType(ValueType.DAYS));
		case (GRANT + BEFORE):
			return Optional.of(ItemValue.builder().value(usedDaysBeforeGrant).valueType(ValueType.DAYS));
		case (GRANT + AFTER):
			return Optional.of(ItemValue.builder().value(usedDaysAfterGrant).valueType(ValueType.DAYS));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case (GRANT + BEFORE):
		case (GRANT + AFTER):
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			usedDays = value.valueOrDefault(0d); break;
		case (GRANT + BEFORE):
			usedDaysBeforeGrant = value.valueOrDefault(0d); break;
		case (GRANT + AFTER):
			usedDaysAfterGrant = value.valueOrDefault(null); break;
		default:
			break;
		}
	}
}
