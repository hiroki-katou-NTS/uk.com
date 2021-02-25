package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
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

	/** 回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A)
	private int times;

	/** 回数付与前 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_B)
	private int timesBeforeGrant;

	/** 回数付与後 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_C)
	private Integer timesAfterGrant;

	public static HalfDayAnnLeaRemainingNumDto from(HalfDayAnnLeaRemainingNum domain) {
		return domain == null ? null : new HalfDayAnnLeaRemainingNumDto(
								domain.getTimes().v(), 
								domain.getTimesBeforeGrant().v(),
								domain.getTimesAfterGrant().isPresent() ? domain.getTimesAfterGrant().get().v() : null);
	}

	public HalfDayAnnLeaRemainingNum toRemainingNumDomain() {
		return HalfDayAnnLeaRemainingNum.of(
						new RemainingTimes(times), 
						new RemainingTimes(timesBeforeGrant),
						Optional.ofNullable(timesAfterGrant == null ? null : new RemainingTimes(timesAfterGrant)));
	}
	
	public static HalfDayAnnLeaRemainingNumDto from(HalfDayAnnLeaUsedNum domain) {
		return domain == null ? null : new HalfDayAnnLeaRemainingNumDto(
								domain.getTimes().v(), 
								domain.getTimesBeforeGrant().v(),
								domain.getTimesAfterGrant().isPresent() ? domain.getTimesAfterGrant().get().v() : null);
	}

	public HalfDayAnnLeaUsedNum toUsedNumDomain() {
		return HalfDayAnnLeaUsedNum.of(
						new UsedTimes(times), 
						new UsedTimes(timesBeforeGrant),
						Optional.ofNullable(timesAfterGrant == null ? null : new UsedTimes(timesAfterGrant)));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(times).valueType(ValueType.COUNT));
		case (GRANT + BEFORE):
			return Optional.of(ItemValue.builder().value(timesBeforeGrant).valueType(ValueType.COUNT));
		case (GRANT + AFTER):
			return Optional.of(ItemValue.builder().value(timesAfterGrant).valueType(ValueType.COUNT));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case COUNT:
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
			times = value.valueOrDefault(0); break;
		case (GRANT + BEFORE):
			timesBeforeGrant = value.valueOrDefault(0); break;
		case (GRANT + AFTER):
			timesAfterGrant = value.valueOrDefault(null); break;
		default:
			break;
		}
	}
}
