package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayUsedNumberDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.TimeUsedNumberDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;

@Data
/** 年休使用数 */
@NoArgsConstructor
@AllArgsConstructor
public class DayTimeUsedNumberDto implements ItemConst, AttendanceItemDataGate {

	/** 使用日数 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private DayUsedNumberDto usedDays;

	/** 使用時間 */
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private TimeUsedNumberDto usedTime;

	public static DayTimeUsedNumberDto from(AnnualLeaveUsedNumber domain) {
		return domain == null ? null : new DayTimeUsedNumberDto(
												DayUsedNumberDto.from(domain.getUsedDays()),
												TimeUsedNumberDto.from(domain.getUsedTime().orElse(null)));
	}
	
	public AnnualLeaveUsedNumber toAnnual() {
		return AnnualLeaveUsedNumber.of(
								usedDays == null ? new AnnualLeaveUsedDays() : usedDays.toAnnual(), 
								Optional.ofNullable(usedTime == null ? null : usedTime.toDomain()));
	}
	
	public SpecialLeaveUseNumber toSpecial(){
		return new SpecialLeaveUseNumber(usedDays == null ? null : usedDays.toSpecial(),
										Optional.ofNullable(usedTime == null ? null : usedTime.toSpecial()));
	}
	
	public static DayTimeUsedNumberDto from(SpecialLeaveUseNumber domain){
		return domain == null ? null : new DayTimeUsedNumberDto(DayUsedNumberDto.from(domain.getUseDays()), 
																	TimeUsedNumberDto.from(domain.getUseTimes().orElse(null)));
	}


	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case DAYS:
			return new DayUsedNumberDto();
		case TIME:
			return new TimeUsedNumberDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case DAYS:
			return Optional.ofNullable(usedDays);
		case TIME:
			return Optional.ofNullable(usedTime);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case DAYS:
			usedDays = (DayUsedNumberDto) value; break;
		case TIME:
			usedTime = (TimeUsedNumberDto) value; break;
		default:
			break;
		}
	}
	
	
}
