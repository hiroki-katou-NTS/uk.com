package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
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
	private Optional<SpecialLeaveUseTimesDto> useTimes;

	public SpecialLeaveUseNumberDto() {
		useDays = new SpecialLeaveUseDaysDto();
		useTimes = Optional.empty();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param useDays
	 * @param useTimes
	 */
	public SpecialLeaveUseNumberDto(SpecialLeaveUseDaysDto useDays, SpecialLeaveUseTimesDto useTimes) {
		this(useDays, Optional.ofNullable(useTimes));
	}

	public static SpecialLeaveUseNumberDto from(SpecialLeaveUseNumber usedNumberInfo) {
		return new SpecialLeaveUseNumberDto(new SpecialLeaveUseDaysDto(usedNumberInfo.getUseDays().getUseDays().v()),
				usedNumberInfo.getUseTimes().map(x -> new SpecialLeaveUseTimesDto(x.getUseTimes().v())));
	}

	public SpecialLeaveUseNumber toDomain() {
		return new SpecialLeaveUseNumber(new SpecialLeaveUseDays(new SpecialLeaveRemainDay(useDays.getUseDays())),
				useTimes.map(x -> new SpecialLeaveUseTimes(new SpecialLeavaRemainTime(x.getUseTimes()))));
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
			return Optional.of(this.useDays);
		case TIME:
			return this.useTimes.map(x -> (SpecialLeaveUseTimesDto) x);

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
			this.useTimes = Optional.ofNullable(value == null ? null : (SpecialLeaveUseTimesDto) value);
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
