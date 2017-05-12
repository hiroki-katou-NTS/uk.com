package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Value
public class UpdatePremiumBudgetCommand {
	String companyID;

	String historyID;

	String memo;

	Integer unitPrice;

	String startDate;

	String endDate;
	
	List<PremiumSetUpdate> premiumSets;
}

@AllArgsConstructor
@Value
class PremiumSetUpdate {
	String companyID;
	
	String historyID;
	
	String attendanceID;
	
    BigDecimal premiumRate;
    
    String premiumName;
    
    String internalID;
    
    int useAtr;
    
    List<String> timeItemIDs;
}
