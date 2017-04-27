package nts.uk.ctx.at.schedule.app.find.budget.premium;

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
public class PremiumBudgetDto {
	String CID;

	String HID;

	String memo;

	Integer unitprice;

	GeneralDate startDate;

	GeneralDate endDate;
}
