package nts.uk.ctx.at.schedule.app.command.budget.premium;

import lombok.Value;

@Value
public class UpdateExtraTimeCommand {
	public String companyID;
	
	public String extraItemID;
	
	public String premiumName;

	public String timeItemID;

	public int useClassification;
}
