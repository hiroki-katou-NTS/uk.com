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
    
    private List<String> timeItemIDs;
    
    public static PremiumSetting createFromJavaType(String companyID, String historyID, String attendanceID, PremiumRate premiumRate, List<String> timeItemIDs) {
		return new PremiumSetting(companyID, historyID, attendanceID, premiumRate, timeItemIDs);
	}
}
