package nts.uk.screen.at.app.command.kdp.kdp003.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.command.kdp.kdp001.a.RegisterStampInputCommand;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStampInputCommandWithEmployeeId extends RegisterStampInputCommand {
	private String companyId;
	
	private String employeeId;
}
