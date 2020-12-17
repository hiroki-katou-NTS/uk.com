package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayUsedNumberDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.TimeUsedNumberDto;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;

/** 特別休暇使用数 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveUseNumberDto implements ItemConst {

	/** 使用日数 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS, layout = LAYOUT_A)
	private DayUsedNumberDto useDays;

	/** 使用時間 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME, layout = LAYOUT_B)
	private TimeUsedNumberDto useTimes;

	public SpecialLeaveUseNumber toDomain(){
		return SpecialLeaveUseNumber.of(useDays.getUsedDays(), useTimes.getUsedTime());
	}

	public static SpecialLeaveUseNumberDto from(SpecialLeaveUseNumber domain){

		DayUsedNumberDto dayUsedNumberDto
			= new DayUsedNumberDto(domain.getUseDays().getUseDays().v(), 0.0, 0.0);
		return domain == null ? null : new SpecialLeaveUseNumberDto(dayUsedNumberDto,
					TimeUsedNumberDto.from(domain.getUseTimes().orElse(null)));
	}
}