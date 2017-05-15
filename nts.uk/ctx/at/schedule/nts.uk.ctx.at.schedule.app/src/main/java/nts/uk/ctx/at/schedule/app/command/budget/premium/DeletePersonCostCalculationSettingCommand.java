package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumRate;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseAttribute;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Value
public class DeletePersonCostCalculationSettingCommand {
	String companyID;

	String historyID;
	
	String startDate;

	String endDate;

	int unitPrice;

	String memo;
	
	List<PremiumSetDelete> premiumSets;
}

@AllArgsConstructor
@Value
class PremiumSetDelete {
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
