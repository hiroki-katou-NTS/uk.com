package nts.uk.ctx.at.schedule.app.find.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumRate;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@Value
public class PremiumBudgetDto {
	String companyID;

	String historyID;

	String memo;

	Integer unitPrice;

	GeneralDate startDate;

	GeneralDate endDate;
	
	List<PremiumSetDto> premiumSets;
}

@AllArgsConstructor
@Value
class PremiumSetDto {
	String companyID;
	
	String historyID;
	
	String attendanceID;
	
    BigDecimal premiumRate;
    
    String premiumName;
    
    String internalID;
    
    int useAtr;
    
    List<String> timeItemIDs;
}
