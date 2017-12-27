package nts.uk.ctx.at.shared.app.command.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WTBonusPaySettingCommand {
	private int action;
	
	private String workingTimesheetCode;

	private String bonusPaySettingCode;
}
