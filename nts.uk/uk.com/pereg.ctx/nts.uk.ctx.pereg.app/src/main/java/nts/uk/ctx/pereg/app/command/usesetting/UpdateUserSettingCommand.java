package nts.uk.ctx.pereg.app.command.usesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserSettingCommand {
	
	private String employeeId;
	private int empCodeValType;
	private int cardNoValType;
	private int recentRegType;
	private String empCodeLetter;
	private String cardNoLetter;
	
}
