package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumberInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUsedInfo;

/**
 * @author thanh_nx
 *
 *         特別休暇
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialLeaveDto implements ItemConst, AttendanceItemDataGate {

	/**
	 * 特別休暇使用情報
	 */
	@AttendanceItemLayout(jpPropertyName = USAGE, layout = LAYOUT_A)
	private SpecialLeaveUsedInfoDto usedNumberInfo;

	/**
	 * 特別休暇残数情報
	 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_B)
	private SpecialLeaveRemainingNumberInfoDto remainingNumberInfo;

	public static SpecialLeaveDto from(SpecialLeave domain) {

		return new SpecialLeaveDto(SpecialLeaveUsedInfoDto.from(domain.getUsedNumberInfo()),
				SpecialLeaveRemainingNumberInfoDto.from(domain.getRemainingNumberInfo()));
	}

	public SpecialLeave toDomain() {
		return new SpecialLeave(usedNumberInfo == null ? new SpecialLeaveUsedInfo() : usedNumberInfo.toDomain(), 
								remainingNumberInfo == null ? new SpecialLeaveRemainingNumberInfo() : remainingNumberInfo.toDomain());
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (USAGE.equals(path)) {
			return new SpecialLeaveUsedInfoDto();
		} else if (REMAIN.equals(path)) {
			return new SpecialLeaveRemainingNumberInfoDto();
		}
		return null;
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {

		if (USAGE.equals(path)) {
			return Optional.ofNullable(this.usedNumberInfo);
		} else if (REMAIN.equals(path)) {
			return Optional.ofNullable(this.remainingNumberInfo);
		}
		return Optional.empty();
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (USAGE.equals(path)) {
			this.usedNumberInfo = (SpecialLeaveUsedInfoDto) value;
		} else if (REMAIN.equals(path)) {
			this.remainingNumberInfo = (SpecialLeaveRemainingNumberInfoDto) value;
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case USAGE:
		case REMAIN:
			return PropType.OBJECT;
		default:
			return PropType.OBJECT;
		}
	}

}
