package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class PremiumSetting {
	
	private String companyID;
	
	private String historyID;
	
	private String attendanceID;
	
    private PremiumRate premiumRate;
    
    private PremiumName premiumName;
    
    private String internalID;
    
    private UseClassification useAtr;
    
    private List<String> timeItemIDs;
    
    public static PremiumSetting createFromJavaType(String companyID, String historyID, String attendanceID, PremiumRate premiumRate, 
    		PremiumName premiumName, String externalID, UseClassification useAtr, List<String> timeItemIDs) {
		return new PremiumSetting(companyID, historyID, attendanceID, premiumRate, premiumName, externalID, useAtr, timeItemIDs);
	}
}
