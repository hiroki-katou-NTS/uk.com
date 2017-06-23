package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class PSBonusPaySettingAddCommand {
	public String employeeId;
	public String bonusPaySettingCode;
}
