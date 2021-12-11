package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;

/** 半日年休使用 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HalfDayAnnLeaUsedNumDto  implements ItemConst, AttendanceItemDataGate{

	/** 回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = USAGE, layout = LAYOUT_A)
	private int usedTimes;
	
	
	/** 回数付与前 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = USAGE + BEFORE, layout = LAYOUT_B)
	private int usedTimesBeforeGrant;

	/** 回数付与後 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = USAGE + AFTER, layout = LAYOUT_C)
	private Integer usedTimesAfterGrant;
	
	public static HalfDayAnnLeaUsedNumDto from(HalfDayAnnLeaUsedNum domain) {
		return domain == null ? null : new HalfDayAnnLeaUsedNumDto(
								domain.getTimes().v(),
								domain.getTimesBeforeGrant().v(),
								domain.getTimesAfterGrant().isPresent() ? domain.getTimesAfterGrant().get().v() : null);
	}
	
	public HalfDayAnnLeaUsedNum toUsedNumDomain() {
		return HalfDayAnnLeaUsedNum.of(
						new UsedTimes(usedTimes), 
						new UsedTimes(usedTimesBeforeGrant),
						Optional.ofNullable(usedTimesAfterGrant == null ? null : new UsedTimes(usedTimesAfterGrant)));
	}
	
	
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (USAGE):
			return Optional.of(ItemValue.builder().value(usedTimes).valueType(ValueType.COUNT));	
		case (USAGE + BEFORE):
			return Optional.of(ItemValue.builder().value(usedTimesBeforeGrant).valueType(ValueType.COUNT));
		case (USAGE + AFTER):
			return Optional.of(ItemValue.builder().value(usedTimesAfterGrant).valueType(ValueType.COUNT));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (USAGE):
		case (USAGE + BEFORE):
		case (USAGE + AFTER):
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (USAGE):
			usedTimes = value.valueOrDefault(0); break;
		case (USAGE + BEFORE):
			usedTimesBeforeGrant = value.valueOrDefault(0); break;
		case (USAGE + AFTER):
			usedTimesAfterGrant = value.valueOrDefault(null); break;
		default:
			break;
		}
	}
}
