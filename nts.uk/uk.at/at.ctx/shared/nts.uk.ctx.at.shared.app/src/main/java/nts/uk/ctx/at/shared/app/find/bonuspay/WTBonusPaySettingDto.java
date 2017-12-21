package nts.uk.ctx.at.shared.app.find.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class WTBonusPaySettingDto {
	public String companyId;
	public String workingTimesheetCode;
	public String bonusPaySettingCode;
}
