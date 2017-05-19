package nts.uk.ctx.at.schedule.app.find.budget.premium;

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
	
	Integer premiumID;
	
	Integer rate;
    
	Integer attendanceID;
    
    String name;
    
    Integer displayNumber;
    
    int useAtr;
    
    List<Integer> attendanceItems;
}
