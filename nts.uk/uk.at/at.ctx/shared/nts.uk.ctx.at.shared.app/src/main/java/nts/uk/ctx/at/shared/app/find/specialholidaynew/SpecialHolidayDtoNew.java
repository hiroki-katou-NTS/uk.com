package nts.uk.ctx.at.shared.app.find.specialholidaynew;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.grantcondition.SpecialLeaveRestrictionDto;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.grantinformation.GrantRegularDto;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.periodinformation.GrantPeriodicDto;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;

@Value
public class SpecialHolidayDtoNew {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 特別休暇名称 */
	private String specialHolidayName;
	
	/** 付与情報 */
	private GrantRegularDto grantRegularDto;
	
	/** 期限情報 */
	private GrantPeriodicDto grantPeriodicDto;
	
	/** 特別休暇利用条件 */
	private SpecialLeaveRestrictionDto specialLeaveRestrictionDto;

	/** 対象項目 */
	private TargetItemDto targetItemDto;
	
	/** メモ */
	private String memo;

	public static SpecialHolidayDtoNew fromDomain(SpecialHoliday specialHoliday) {
		if(specialHoliday == null) {
			return null;
		}
		
		GrantRegularDto grantRegular = GrantRegularDto.fromDomain(specialHoliday.getGrantRegular() != null ? specialHoliday.getGrantRegular() : null);
		
		GrantPeriodicDto grantPeriodic = GrantPeriodicDto.fromDomain(specialHoliday.getGrantPeriodic() != null ? specialHoliday.getGrantPeriodic() : null);
		
		SpecialLeaveRestrictionDto specialLeaveRestriction = SpecialLeaveRestrictionDto
				.fromDomain(specialHoliday.getSpecialLeaveRestriction() != null ? specialHoliday.getSpecialLeaveRestriction() : null);
		
		TargetItemDto targetItem = TargetItemDto.fromDomain(specialHoliday.getTargetItem() != null ? specialHoliday.getTargetItem() : null);
		
		return new SpecialHolidayDtoNew(
				specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v(),
				specialHoliday.getSpecialHolidayName().v(),
				grantRegular,
				grantPeriodic,
				specialLeaveRestriction,
				targetItem,
				specialHoliday.getMemo().v()
		);
	}
}
