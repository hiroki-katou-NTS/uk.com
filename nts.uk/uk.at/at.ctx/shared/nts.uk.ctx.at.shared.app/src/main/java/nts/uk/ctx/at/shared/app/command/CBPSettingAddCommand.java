package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class CBPSettingAddCommand {
	public String companyId;
	public String bonusPaySettingCode;
}
