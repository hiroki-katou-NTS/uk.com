package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class WTBonusPaySettingUpdateCommand {
	public String companyId;
	public String workingTimesheetCode;
	public String bonusPaySettingCode;
}
