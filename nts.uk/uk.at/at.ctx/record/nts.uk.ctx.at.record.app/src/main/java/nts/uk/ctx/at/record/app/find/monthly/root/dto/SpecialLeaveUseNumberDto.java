package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayUsedNumberDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.TimeUsedNumberDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeaveUseNumber;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

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
		return new SpecialLeaveUseNumber(useDays == null ? null : useDays.toSpecial(),
										Optional.ofNullable(useTimes == null ? null : useTimes.toSpecial()));
	}
	
	public static SpecialLeaveUseNumberDto from(SpecialLeaveUseNumber domain){
		return domain == null ? null : new SpecialLeaveUseNumberDto(DayUsedNumberDto.from(domain.getUseDays()), 
																	TimeUsedNumberDto.from(domain.getUseTimes().orElse(null)));
	}
}