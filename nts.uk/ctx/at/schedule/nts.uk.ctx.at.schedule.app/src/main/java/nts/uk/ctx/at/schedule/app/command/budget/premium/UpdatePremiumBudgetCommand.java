package nts.uk.ctx.at.schedule.app.command.budget.premium;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Value
public class UpdatePremiumBudgetCommand {
	String CID;

	String HID;

	String memo;

	Integer unitprice;

	GeneralDate startDate;

	GeneralDate endDate;
}
