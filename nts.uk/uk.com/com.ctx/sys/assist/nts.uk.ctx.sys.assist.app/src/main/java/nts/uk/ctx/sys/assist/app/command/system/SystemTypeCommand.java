package nts.uk.ctx.sys.assist.app.command.system;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SystemTypeCommand {

	
	/**Are employees in charge*/
	private boolean isEmployeeCharge = false;
	/**Is it a salary professional*/
	private boolean isSalaryProfessional = false;
	/**Human Resources Officer*/
	private boolean isHumanResOfficer = false;
	/**Office helper personnel?*/
	private boolean isOfficeHelperPersonne = false;
	/**Person in charge of personal information*/
	private boolean isPersonalInformation = false;
}
