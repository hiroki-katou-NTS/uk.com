package nts.uk.ctx.at.schedule.app.command.budget.premium.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class PremiumSetCommand {
	String companyID;
	
	String historyID;
	
	Integer displayNumber;
	
	Integer rate;
    
	Integer attendanceID;
    
    String name;
    
    int useAtr;
    
    List<ShortAttendanceItemCommand> attendanceItems;
}
