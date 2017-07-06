package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WPBonusPaySettingCommand {
	private String workplaceId;

	private String bonusPaySettingCode;

	private int action;
}
