package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class PSBonusPaySettingUpdateCommand {
		public String employeeId;
		public String bonusPaySettingCode;
}
