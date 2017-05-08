package nts.uk.ctx.at.schedule.dom.budget.premium;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ExtraTime {
	
	private String companyID;
	
	private String extraItemID;
	
	private PremiumName premiumName;

	private String timeItemID;

	private UseClassification useClassification;
    
    public static ExtraTime createFromJavaType(String companyID, String extraItemID, PremiumName premiumName, String timeItemID, UseClassification useClassification) {
		return new ExtraTime(companyID, extraItemID, premiumName, timeItemID, useClassification);
	}
}
