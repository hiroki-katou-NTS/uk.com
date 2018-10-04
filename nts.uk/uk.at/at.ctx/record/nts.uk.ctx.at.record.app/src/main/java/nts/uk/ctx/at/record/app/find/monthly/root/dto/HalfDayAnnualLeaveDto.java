package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
/** 半日年休 */
@NoArgsConstructor
@AllArgsConstructor
public class HalfDayAnnualLeaveDto implements ItemConst {

	/** 残数 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_A)
	private HalfDayAnnLeaRemainingNumDto remainingNum;

	/** 使用数 */
	@AttendanceItemLayout(jpPropertyName = USAGE, layout = LAYOUT_B)
	private HalfDayAnnLeaRemainingNumDto usedNum;

	public static HalfDayAnnualLeaveDto from(HalfDayAnnualLeave domain) {
		return domain == null ? null : new HalfDayAnnualLeaveDto(
						HalfDayAnnLeaRemainingNumDto.from(domain.getRemainingNum()),
						HalfDayAnnLeaRemainingNumDto.from(domain.getUsedNum()));
	}

	public HalfDayAnnualLeave toDomain() {
		return HalfDayAnnualLeave.of(
							remainingNum == null ? new HalfDayAnnLeaRemainingNum() : remainingNum.toRemainingNumDomain(),
							usedNum == null ? new HalfDayAnnLeaUsedNum() : usedNum.toUsedNumDomain());
	}
}
