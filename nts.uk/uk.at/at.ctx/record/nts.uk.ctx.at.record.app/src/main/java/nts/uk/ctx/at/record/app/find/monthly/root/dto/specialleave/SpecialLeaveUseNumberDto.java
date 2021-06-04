package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeavaRemainTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseTimes;

/**
 * 特別休暇使用数
 * 
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveUseNumberDto implements ItemConst, AttendanceItemDataGate {

	/**
	 * 使用日数
	 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private SpecialLeaveUseDaysDto useDays;

	/**
	 * 使用時間
	 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private SpecialLeaveUseTimesDto useTimes;

	public static SpecialLeaveUseNumberDto from(SpecialLeaveUseNumber usedNumberInfo) {
		return new SpecialLeaveUseNumberDto(usedNumberInfo.getUseDays().map(x -> new SpecialLeaveUseDaysDto(x.v())).orElse(null),
								usedNumberInfo.getUseTimes().map(x -> new SpecialLeaveUseTimesDto(x.getUseTimes().v())).orElse(null));
	}

	public SpecialLeaveUseNumber toDomain() {
		return new SpecialLeaveUseNumber(
				useDays == null ? Optional.of(new SpecialLeaveUseDays(0.0)) : Optional.of(new SpecialLeaveUseDays((useDays.getUseDays()))),
				Optional.ofNullable(useTimes == null ? null : new SpecialLeaveUseTimes(new SpecialLeavaRemainTime(useTimes.getUseTimes()))));
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		
		switch (path) {
		case DAYS:
			return new SpecialLeaveUseDaysDto();
		case TIME:
			return new SpecialLeaveUseTimesDto();

		default:
			return newInstanceOf(path);
		}
		
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {

		switch (path) {
		case DAYS:
			return Optional.ofNullable(this.useDays);
		case TIME:
			return Optional.ofNullable(this.useTimes);

		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {

		switch (path) {
		case DAYS:
			this.useDays = (SpecialLeaveUseDaysDto) value;
			break;
		case TIME:
			this.useTimes = (SpecialLeaveUseTimesDto) value;
			break;
		}

	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case TIME:
			return PropType.OBJECT;
		default:
			return PropType.OBJECT;
		}
	}
}
