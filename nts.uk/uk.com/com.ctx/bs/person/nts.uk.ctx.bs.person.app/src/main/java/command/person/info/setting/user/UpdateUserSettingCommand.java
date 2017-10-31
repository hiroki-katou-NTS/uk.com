package command.person.info.setting.user;

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
