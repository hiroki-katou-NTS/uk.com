package nts.uk.ctx.sys.assist.app.comand.system;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
	
	public SystemTypeCommand(boolean isEmployeeCharge, boolean isSalaryProfessional, boolean isHumanResOfficer,
			boolean isOfficeHelperPersonne, boolean isPersonalInformation) {
		super();
		this.isEmployeeCharge = isEmployeeCharge;
		this.isSalaryProfessional = isSalaryProfessional;
		this.isHumanResOfficer = isHumanResOfficer;
		this.isOfficeHelperPersonne = isOfficeHelperPersonne;
		this.isPersonalInformation = isPersonalInformation;
	}
}
