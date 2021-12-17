package nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaLimit;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

@AllArgsConstructor
@Data
public class StampingAreaRestrictionDto {
	
	private String employeeId;
	
	private int locationInformation;
	
	private int isLimitArea;
	
	public StampingAreaRestriction toDomain() {
		NotUseAtr locationInformationEn = NotUseAtr.toEnum(locationInformation);
		StampingAreaLimit isLimitAreaEn = StampingAreaLimit.toEnum(isLimitArea);
		return new StampingAreaRestriction (locationInformationEn,isLimitAreaEn);
	}
	
}
