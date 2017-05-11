package nts.uk.ctx.at.schedule.app.find.budget.premium;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class ExtraTimeDto {
	public String companyID;
	
	public String extraItemID;
	
	public String premiumName;

	public String timeItemID;

	public int useAtr;
}
