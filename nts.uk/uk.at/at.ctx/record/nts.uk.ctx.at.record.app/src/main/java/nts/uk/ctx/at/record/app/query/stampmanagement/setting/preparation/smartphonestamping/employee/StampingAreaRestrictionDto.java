package nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaLimit;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

@AllArgsConstructor
@Data
public class StampingAreaRestrictionDto {
	
	private String employeeId;
	
	private int locationInformation;
	
	private int isLimitArea;
	
	public EmployeeStampingAreaRestrictionSetting toDomain() {
		NotUseAtr locationInformationEn = NotUseAtr.toEnum(locationInformation);
		StampingAreaLimit isLimitAreaEn = StampingAreaLimit.toEnum(isLimitArea);
		StampingAreaRestriction areaRestriction = new StampingAreaRestriction(locationInformationEn,isLimitAreaEn);
		return new EmployeeStampingAreaRestrictionSetting (employeeId,areaRestriction);
	}
	
	public static  StampingAreaRestrictionDto toDto (EmployeeStampingAreaRestrictionSetting areaRestrictionSetting) {
		int isLimitAreaEn = areaRestrictionSetting.getStampingAreaRestriction().getUseLocationInformation().value;
		int locationInformationEn = areaRestrictionSetting.getStampingAreaRestriction().getStampingAreaLimit().value;
		return new StampingAreaRestrictionDto(areaRestrictionSetting.getEmployeeId(), locationInformationEn, isLimitAreaEn);

	}
}
