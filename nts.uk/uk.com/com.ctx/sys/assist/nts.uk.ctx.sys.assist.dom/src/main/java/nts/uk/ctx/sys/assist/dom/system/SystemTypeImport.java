package nts.uk.ctx.sys.assist.dom.system;

import lombok.Data;

@Data
public class SystemTypeImport {
	
	/**Is it a salary professional*/
	boolean isSalaryProfessional = false;
	/**Human Resources Officer*/
	boolean isHumanResOfficer = false;
	/**Office helper personnel?*/
	boolean isOfficeHelperPersonne = false;
	/**Person in charge of personal information*/
	boolean isPersonalInformation = false;
	
	public SystemTypeImport(boolean isSalaryProfessional, boolean isHumanResOfficer,
			boolean isOfficeHelperPersonne, boolean isPersonalInformation) {
		super();
		this.isSalaryProfessional = isSalaryProfessional;
		this.isHumanResOfficer = isHumanResOfficer;
		this.isOfficeHelperPersonne = isOfficeHelperPersonne;
		this.isPersonalInformation = isPersonalInformation;
	}


}
