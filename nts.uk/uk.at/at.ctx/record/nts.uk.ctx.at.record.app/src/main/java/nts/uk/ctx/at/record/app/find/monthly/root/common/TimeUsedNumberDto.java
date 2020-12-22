package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.TimeAnnualLeaveUsedTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeavaRemainTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.UseNumber;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 時間年休使用時間 */
/** 年休上限残時間 */
public class TimeUsedNumberDto implements ItemConst, AttendanceItemDataGate {

	/** 使用回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A)
	private int usedTimes;
	
	/** 使用時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private int usedTime;
	
	/** 使用時間付与前 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_C)
	private int usedTimeBeforeGrant;
	
	/** 使用時間付与後 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_D)
	private Integer usedTimeAfterGrant;

	public static TimeUsedNumberDto from(TimeAnnualLeaveUsedTime domain) {
		return domain == null ? null : new TimeUsedNumberDto(
						domain.getUsedTimes().v(), 
						domain.getUsedTime().valueAsMinutes(),
						domain.getUsedTimeBeforeGrant().valueAsMinutes(),
						domain.getUsedTimeAfterGrant().isPresent() ? domain.getUsedTimeAfterGrant().get().valueAsMinutes() : null);
	}

	public TimeAnnualLeaveUsedTime toDomain() {
		return TimeAnnualLeaveUsedTime.of(
							new UsedTimes(usedTimes), new UsedMinutes(usedTime),
							new UsedMinutes(usedTimeBeforeGrant),
							Optional.ofNullable(usedTimeAfterGrant == null ? null : new UsedMinutes(usedTimeAfterGrant)));
	}
	
	public static TimeUsedNumberDto from(AnnualLeaveMaxRemainingTime domain) {
		return domain == null ? null : new TimeUsedNumberDto(
						0, domain.getTime().valueAsMinutes(),
						domain.getTimeBeforeGrant().valueAsMinutes(),
						domain.getTimeAfterGrant().isPresent() ? domain.getTimeAfterGrant().get().valueAsMinutes() : null);
	}

	public AnnualLeaveMaxRemainingTime toMaxRemainingTimeDomain() {
		return AnnualLeaveMaxRemainingTime.of(
							new RemainingMinutes(usedTime),
							new RemainingMinutes(usedTimeBeforeGrant),
							Optional.ofNullable(usedTimeAfterGrant == null ? null : new RemainingMinutes(usedTimeAfterGrant)));
	}
	
	public static TimeUsedNumberDto from(SpecialLeaveUseTimes domain) {
		return domain == null ? null : new TimeUsedNumberDto(
						domain.getUseNumber().v(), 
						domain.getUseTimes().valueAsMinutes(),
						domain.getBeforeUseGrantTimes().valueAsMinutes(),
						domain.getAfterUseGrantTimes().isPresent() ? domain.getAfterUseGrantTimes().get().valueAsMinutes() : null);
	}
	
	public SpecialLeaveUseTimes toSpecial(){
		return new SpecialLeaveUseTimes(new UseNumber(usedTimes), 
										new SpecialLeavaRemainTime(usedTime), 
										new SpecialLeavaRemainTime(usedTimeBeforeGrant), 
										Optional.ofNullable(usedTimeAfterGrant == null ? null : new SpecialLeavaRemainTime(usedTimeAfterGrant)));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(usedTimes).valueType(ValueType.COUNT));
		case TIME:
			return Optional.of(ItemValue.builder().value(usedTime).valueType(ValueType.TIME));
		case (GRANT + BEFORE):
			return Optional.of(ItemValue.builder().value(usedTimeBeforeGrant).valueType(ValueType.TIME));
		case (GRANT + AFTER):
			return Optional.of(ItemValue.builder().value(usedTimeAfterGrant).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case COUNT:
		case TIME:
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
		case COUNT:
			usedTimes = value.valueOrDefault(0); break;
		case TIME:
			usedTime = value.valueOrDefault(0); break;
		case (GRANT + BEFORE):
			usedTimeBeforeGrant = value.valueOrDefault(0); break;
		case (GRANT + AFTER):
			usedTimeAfterGrant = value.valueOrDefault(null); break;
		default:
			break;
		}
	}

	
}
