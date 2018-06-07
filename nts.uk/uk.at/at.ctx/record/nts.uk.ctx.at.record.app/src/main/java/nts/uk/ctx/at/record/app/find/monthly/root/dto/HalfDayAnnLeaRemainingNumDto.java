package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

@Data
/** 半日年休残数 */
@NoArgsConstructor
@AllArgsConstructor
public class HalfDayAnnLeaRemainingNumDto {

	/** 回数 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "回数", layout = "A")
	private int times;

	/** 回数付与前 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "回数付与前", layout = "B")
	private int timesBeforeGrant;

	/** 回数付与後 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "回数付与後", layout = "C")
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
