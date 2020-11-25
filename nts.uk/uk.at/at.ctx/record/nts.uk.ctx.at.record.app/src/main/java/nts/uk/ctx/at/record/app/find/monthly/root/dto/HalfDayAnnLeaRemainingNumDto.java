package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;

@Data
/** 半日年休残数 */
@NoArgsConstructor
@AllArgsConstructor
public class HalfDayAnnLeaRemainingNumDto implements ItemConst {

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
}
