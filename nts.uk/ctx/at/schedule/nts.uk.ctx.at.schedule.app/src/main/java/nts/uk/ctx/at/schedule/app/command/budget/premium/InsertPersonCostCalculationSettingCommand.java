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
public class InsertPersonCostCalculationSettingCommand {
	String companyID;

	String historyID;
	
	String startDate;

	String endDate;

	int unitPrice;

	String memo;
	
	List<PremiumSetInsert> premiumSets;
}

@AllArgsConstructor
@Value
class PremiumSetInsert {
	String companyID;
	
	String historyID;
	
	BigDecimal premiumID;
	
    BigDecimal rate;
    
    BigDecimal attendanceID;
    
    String name;
    
    BigDecimal displayNumber;
    
    int useAtr;
    
    List<BigDecimal> timeItemIDs;
}
