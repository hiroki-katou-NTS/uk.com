package nts.uk.ctx.at.schedule.app.command.budget.premium.command;

import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
public class UpdatePremiumItemCommand {
	private String companyID;
	
	private Integer displayNumber;
	
	private String name;

	private int useAtr;
	
	private boolean isChange;
}
