package nts.uk.ctx.sys.assist.dom.system;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemTypeImport {
	
	/**Is Employee Charge*/
	boolean isEmployeeCharge = false;
	
	/**Is it a salary professional*/
	boolean isSalaryProfessional = false;
	
	/**Human Resources Officer*/
	boolean isHumanResOfficer = false;
	
	/**Office helper personnel*/
	boolean isOfficeHelperPersonne = false;
	
	/**Person in charge of personal information*/
	boolean isPersonalInformation = false;
}
