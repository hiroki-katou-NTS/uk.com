package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedDays;

@Data
/** 年休使用日数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsedDaysDto implements ItemConst, AttendanceItemDataGate {

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

	public static AnnualLeaveUsedDaysDto from(AnnualLeaveUsedDays domain) {
		return domain == null ? null : new AnnualLeaveUsedDaysDto(
									domain.getUsedDays().v(), 
									domain.getUsedDaysBeforeGrant().v(),
									domain.getUsedDaysAfterGrant().isPresent() ? domain.getUsedDaysAfterGrant().get().v() : null);
	}
	
	public AnnualLeaveUsedDays toDomain() {
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
			return Optional.of(ItemValue.builder().value(usedDays).valueType(ValueType.TIME));
		case GRANT + BEFORE:
			return Optional.of(ItemValue.builder().value(usedDaysBeforeGrant).valueType(ValueType.TIME));
		case GRANT + AFTER:
			return Optional.of(ItemValue.builder().value(usedDaysAfterGrant).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			this.usedDays = value.valueOrDefault(null);
			break;
		case GRANT + BEFORE:
			this.usedDaysBeforeGrant = value.valueOrDefault(null);
			break;
		case GRANT + AFTER:
			this.usedDaysAfterGrant = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case GRANT + BEFORE:
		case GRANT + AFTER:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
}
