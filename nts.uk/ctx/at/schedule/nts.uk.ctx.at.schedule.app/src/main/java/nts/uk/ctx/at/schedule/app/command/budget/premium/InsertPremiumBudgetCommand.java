package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Value
public class InsertPremiumBudgetCommand {
	String companyID;

	String historyID;

	String memo;

	Integer unitPrice;

	String startDate;

	String endDate;
	
	List<PremiumSetInsert> premiumSets;
}

@AllArgsConstructor
@Value
class PremiumSetInsert {
	String companyID;
	
	String historyID;
	
	String attendanceID;
	
    BigDecimal premiumRate;
    
    String premiumName;
    
    String internalID;
    
    int useAtr;
    
    List<String> timeItemIDs;
}
