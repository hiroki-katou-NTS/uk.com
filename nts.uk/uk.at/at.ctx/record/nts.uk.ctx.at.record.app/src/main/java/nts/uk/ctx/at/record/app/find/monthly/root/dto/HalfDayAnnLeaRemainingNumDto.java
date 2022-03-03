package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;

@Data
/** 半日年休残数 */
@NoArgsConstructor
@AllArgsConstructor
public class HalfDayAnnLeaRemainingNumDto implements ItemConst, AttendanceItemDataGate {


	/** 回数付与前 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = REMAIN + BEFORE, layout = LAYOUT_B)
	private int timesBeforeGrant;

	/** 回数付与後 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = REMAIN + AFTER, layout = LAYOUT_C)
	private Integer timesAfterGrant;

	public static HalfDayAnnLeaRemainingNumDto from(HalfDayAnnLeaRemainingNum domain) {
		return domain == null ? null : new HalfDayAnnLeaRemainingNumDto(
								domain.getTimesBeforeGrant().v(),
								domain.getTimesAfterGrant().isPresent() ? domain.getTimesAfterGrant().get().v() : null);
	}

	public HalfDayAnnLeaRemainingNum toRemainingNumDomain() {
		return HalfDayAnnLeaRemainingNum.of(
						new RemainingTimes(timesBeforeGrant),
						Optional.ofNullable(timesAfterGrant == null ? null : new RemainingTimes(timesAfterGrant)));
	}
	
	public static HalfDayAnnLeaRemainingNumDto from(HalfDayAnnLeaUsedNum domain) {
		return domain == null ? null : new HalfDayAnnLeaRemainingNumDto(
								domain.getTimesBeforeGrant().v(),
								domain.getTimesAfterGrant().isPresent() ? domain.getTimesAfterGrant().get().v() : null);
	}


	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (REMAIN + BEFORE):
			return Optional.of(ItemValue.builder().value(timesBeforeGrant).valueType(ValueType.COUNT));
		case (REMAIN + AFTER):
			return Optional.of(ItemValue.builder().value(timesAfterGrant).valueType(ValueType.COUNT));
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
			timesBeforeGrant = value.valueOrDefault(0); break;
		case (REMAIN + AFTER):
			timesAfterGrant = value.valueOrDefault(null); break;
		default:
			break;
		}
	}
}
