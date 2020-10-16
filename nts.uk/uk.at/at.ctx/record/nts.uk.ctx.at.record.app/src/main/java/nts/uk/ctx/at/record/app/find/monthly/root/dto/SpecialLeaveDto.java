package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayAndTimeDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.ActualSpecialLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeave;

/** 特別休暇 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialLeaveDto implements ItemConst, AttendanceItemDataGate {
	
	/** 残数 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_A)
	private DayAndTimeDto remain;
	
	/** 残数付与前 */
	@AttendanceItemLayout(jpPropertyName = REMAIN + GRANT + BEFORE, layout = LAYOUT_B)
	private DayAndTimeDto beforeRemainGrant;
	
	/** 使用数 */
	@AttendanceItemLayout(jpPropertyName = USAGE, layout = LAYOUT_C)
	private DayTimeUsedNumberDto useNumber;
	
	/** 未消化数 */
	@AttendanceItemLayout(jpPropertyName = NOT_DIGESTION, layout = LAYOUT_D)
	private DayAndTimeDto unDegestionNumber;
	
	/** 残数付与後 */
	@AttendanceItemLayout(jpPropertyName = REMAIN + GRANT + AFTER, layout = LAYOUT_E)
	private DayAndTimeDto afterRemainGrant;
	
	public static SpecialLeaveDto from(ActualSpecialLeave domain){
		return domain == null ? null : new SpecialLeaveDto(DayAndTimeDto.from(domain.getRemain()), 
				DayAndTimeDto.from(domain.getBeforRemainGrant()), 
				DayTimeUsedNumberDto.from(domain.getUseNumber()), 
				null, 
				DayAndTimeDto.from(domain.getAfterRemainGrant().orElse(null)));
	}
	
	public static SpecialLeaveDto from(SpecialLeave domain){
		return domain == null ? null : new SpecialLeaveDto(DayAndTimeDto.from(domain.getRemain()), 
				DayAndTimeDto.from(domain.getBeforeRemainGrant()), 
				DayTimeUsedNumberDto.from(domain.getUseNumber()), 
				DayAndTimeDto.from(domain.getUnDegestionNumber()), 
				DayAndTimeDto.from(domain.getAfterRemainGrant().orElse(null)));
	}
	
	public ActualSpecialLeave toActualDomain(){
		return new ActualSpecialLeave(remain == null ? null : remain.toActualSpecial(), 
									beforeRemainGrant == null ? null : beforeRemainGrant.toActualSpecial(), 
									useNumber == null ? null : useNumber.toSpecial(),
									Optional.ofNullable(afterRemainGrant == null ? null : afterRemainGrant.toActualSpecial()));
	}
	
	public SpecialLeave toDomain(){
		return new SpecialLeave(remain == null ? null : remain.toSpecial(), 
				beforeRemainGrant == null ? null : beforeRemainGrant.toSpecial(), 
				useNumber == null ? null : useNumber.toSpecial(),
				unDegestionNumber == null ? null : unDegestionNumber.toUnDegest(),
				Optional.ofNullable(afterRemainGrant == null ? null : afterRemainGrant.toSpecial()));
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case USAGE:
			return new DayTimeUsedNumberDto();
		case REMAIN:
		case (REMAIN + GRANT + BEFORE):
		case NOT_DIGESTION:
		case (REMAIN + GRANT + AFTER):
			return new DayAndTimeDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case USAGE:
			return Optional.ofNullable(useNumber);
		case REMAIN:
			return Optional.ofNullable(remain);
		case (REMAIN + GRANT + BEFORE):
			return Optional.ofNullable(beforeRemainGrant);
		case NOT_DIGESTION:
			return Optional.ofNullable(unDegestionNumber);
		case (REMAIN + GRANT + AFTER):
			return Optional.ofNullable(afterRemainGrant);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case USAGE:
			useNumber = (DayTimeUsedNumberDto) value; break;
		case REMAIN:
			remain = (DayAndTimeDto) value; break;
		case (REMAIN + GRANT + BEFORE):
			beforeRemainGrant = (DayAndTimeDto) value; break;
		case NOT_DIGESTION:
			unDegestionNumber = (DayAndTimeDto) value; break;
		case (REMAIN + GRANT + AFTER):
			afterRemainGrant = (DayAndTimeDto) value; break;
		default:
			break;
		}
	}

	
}
