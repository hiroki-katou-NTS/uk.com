package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;

/**
 * 年休上限残時間
 * @author hayata_maekawa
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRemainNumberDto  implements ItemConst, AttendanceItemDataGate {
	


	/** 時間年休残時間付与前 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = REMAIN + BEFORE, layout = LAYOUT_A)
	private int usedTimeBeforeGrant;

	/** 時間年休残時間付与後 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = REMAIN + AFTER, layout = LAYOUT_B)
	private Integer usedTimeAfterGrant;
	
	
	public static TimeRemainNumberDto from(AnnualLeaveMaxRemainingTime domain) {
		return domain == null ? null : new TimeRemainNumberDto(
						domain.getTimeBeforeGrant().valueAsMinutes(),
						domain.getTimeAfterGrant().isPresent() ? domain.getTimeAfterGrant().get().valueAsMinutes() : null);
	}

	public AnnualLeaveMaxRemainingTime toMaxRemainingTimeDomain() {
		return AnnualLeaveMaxRemainingTime.of(
							new LeaveRemainingTime(usedTimeBeforeGrant),
							Optional.ofNullable(usedTimeAfterGrant == null ? null : new LeaveRemainingTime(usedTimeAfterGrant)));
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (REMAIN + BEFORE):
			return Optional.of(ItemValue.builder().value(usedTimeBeforeGrant).valueType(ValueType.TIME));
		case (REMAIN + AFTER):
			return Optional.of(ItemValue.builder().value(usedTimeAfterGrant).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (REMAIN + BEFORE):
		case (REMAIN + AFTER):
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (REMAIN + BEFORE):
			usedTimeBeforeGrant = value.valueOrDefault(0); break;
		case (REMAIN + AFTER):
			usedTimeAfterGrant = value.valueOrDefault(null); break;
		default:
			break;
		}
	}

}
