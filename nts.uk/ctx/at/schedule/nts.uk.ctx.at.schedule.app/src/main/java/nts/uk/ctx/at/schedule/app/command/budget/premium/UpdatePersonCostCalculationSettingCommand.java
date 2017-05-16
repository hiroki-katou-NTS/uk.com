package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Value
public class UpdatePersonCostCalculationSettingCommand {
	String companyID;

	String historyID;
	
	String startDate;

	String endDate;

	int unitPrice;

	String memo;
	
	List<PremiumSetUpdate> premiumSets;
}

@AllArgsConstructor
@Value
class PremiumSetUpdate {
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
