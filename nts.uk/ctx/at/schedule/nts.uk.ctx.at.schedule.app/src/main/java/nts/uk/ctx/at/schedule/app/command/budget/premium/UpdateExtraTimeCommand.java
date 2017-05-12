package nts.uk.ctx.at.schedule.app.command.budget.premium;

import lombok.Value;

@Value
public class UpdateExtraTimeCommand {
	public String extraItemID;
	
	public String companyID;
	
	public int useAtr;
	
	public String timeItemID;
	
	public String name;
}
