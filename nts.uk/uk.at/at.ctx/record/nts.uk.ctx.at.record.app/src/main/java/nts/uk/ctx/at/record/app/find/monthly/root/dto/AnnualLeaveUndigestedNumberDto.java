package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUndigestedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.UndigestedAnnualLeaveDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.UndigestedTimeAnnualLeaveTime;

@Data
/** 年休未消化数 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUndigestedNumberDto implements ItemConst, AttendanceItemDataGate {

	/** 未消化日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double undigestedDays;

	/** 未消化時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private Integer undigestedTime;

	public static AnnualLeaveUndigestedNumberDto from(AnnualLeaveUndigestedNumber domain) {
		return domain == null ? null : new AnnualLeaveUndigestedNumberDto(
						domain.getUndigestedDays() == null ? 0d : domain.getUndigestedDays().getUndigestedDays().v(),
						domain.getUndigestedTime().isPresent()
								? domain.getUndigestedTime().get().getUndigestedTime().valueAsMinutes() : null);
	}

	public AnnualLeaveUndigestedNumber toDomain() {
		return AnnualLeaveUndigestedNumber.of(
				UndigestedAnnualLeaveDays.of(new AnnualLeaveUsedDayNumber(undigestedDays)),
				Optional.ofNullable(undigestedTime == null ? null
						: UndigestedTimeAnnualLeaveTime.of(new UsedMinutes(undigestedTime))));
	}@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(undigestedDays).valueType(ValueType.DAYS));
		case TIME:
			return Optional.of(ItemValue.builder().value(undigestedTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case TIME:
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
			undigestedDays = value.valueOrDefault(0d); break;
		case TIME:
			undigestedTime = value.valueOrDefault(null); break;
		default:
			break;
		}
	}
}
