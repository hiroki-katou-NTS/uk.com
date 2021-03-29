package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnualLeave;

@Data
/** 半日年休 */
@NoArgsConstructor
@AllArgsConstructor
public class HalfDayAnnualLeaveDto implements ItemConst, AttendanceItemDataGate {

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

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case REMAIN:
		case USAGE:
			return new HalfDayAnnLeaRemainingNumDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case REMAIN:
			return Optional.ofNullable(remainingNum);
		case USAGE:
			return Optional.ofNullable(usedNum);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case REMAIN:
			remainingNum = (HalfDayAnnLeaRemainingNumDto) value; break;
		case USAGE:
			usedNum = (HalfDayAnnLeaRemainingNumDto) value; break;
		default:
			break;
		}
	}
}
