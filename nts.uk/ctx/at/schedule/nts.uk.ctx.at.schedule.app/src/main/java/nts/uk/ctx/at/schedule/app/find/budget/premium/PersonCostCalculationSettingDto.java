package nts.uk.ctx.at.schedule.app.find.budget.premium;

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

@AllArgsConstructor
@Value
public class PersonCostCalculationSettingDto {
	String companyID;

	String historyID;
	
	GeneralDate startDate;

	GeneralDate endDate;

	int unitPrice;

	String memo;
	
	List<PremiumSetDto> premiumSets;
}

@AllArgsConstructor
@Value
class PremiumSetDto {
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
