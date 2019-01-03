package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayAndTimeDto;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.ActualSpecialLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialLeave;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/** 特別休暇 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialLeaveDto implements ItemConst {
	
	/** 残数 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_A)
	private DayAndTimeDto remain;
	
	/** 残数付与前 */
	@AttendanceItemLayout(jpPropertyName = REMAIN + GRANT + BEFORE, layout = LAYOUT_B)
	private DayAndTimeDto beforeRemainGrant;
	
	/** 使用数 */
	@AttendanceItemLayout(jpPropertyName = USAGE, layout = LAYOUT_C)
	private SpecialLeaveUseNumberDto useNumber;
	
	/** 未消化数 */
	@AttendanceItemLayout(jpPropertyName = NOT_DIGESTION, layout = LAYOUT_D)
	private DayAndTimeDto unDegestionNumber;
	
	/** 残数付与後 */
	@AttendanceItemLayout(jpPropertyName = REMAIN + GRANT + AFTER, layout = LAYOUT_E)
	private DayAndTimeDto afterRemainGrant;
	
	public static SpecialLeaveDto from(ActualSpecialLeave domain){
		return domain == null ? null : new SpecialLeaveDto(DayAndTimeDto.from(domain.getRemain()), 
				DayAndTimeDto.from(domain.getBeforRemainGrant()), 
				SpecialLeaveUseNumberDto.from(domain.getUseNumber()), 
				null, 
				DayAndTimeDto.from(domain.getAfterRemainGrant().orElse(null)));
	}
	
	public static SpecialLeaveDto from(SpecialLeave domain){
		return domain == null ? null : new SpecialLeaveDto(DayAndTimeDto.from(domain.getRemain()), 
				DayAndTimeDto.from(domain.getBeforeRemainGrant()), 
				SpecialLeaveUseNumberDto.from(domain.getUseNumber()), 
				DayAndTimeDto.from(domain.getUnDegestionNumber()), 
				DayAndTimeDto.from(domain.getAfterRemainGrant().orElse(null)));
	}
	
	public ActualSpecialLeave toActualDomain(){
		return new ActualSpecialLeave(remain == null ? null : remain.toActualSpecial(), 
									beforeRemainGrant == null ? null : beforeRemainGrant.toActualSpecial(), 
									useNumber == null ? null : useNumber.toDomain(),
									Optional.ofNullable(afterRemainGrant == null ? null : afterRemainGrant.toActualSpecial()));
	}
	
	public SpecialLeave toDomain(){
		return new SpecialLeave(remain == null ? null : remain.toSpecial(), 
				beforeRemainGrant == null ? null : beforeRemainGrant.toSpecial(), 
				useNumber == null ? null : useNumber.toDomain(),
				unDegestionNumber == null ? null : unDegestionNumber.toUnDegest(),
				Optional.ofNullable(afterRemainGrant == null ? null : afterRemainGrant.toSpecial()));
	}
}
