package nts.uk.ctx.at.shared.app.command.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PSBonusPaySettingCommand {
	private String employeeId;

	private String bonusPaySettingCode;

	private int action;
}
