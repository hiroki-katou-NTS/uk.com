package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class WPBonusPaySettingAddCommand {
	public String workplaceId;
	public String bonusPaySettingCode;
}
