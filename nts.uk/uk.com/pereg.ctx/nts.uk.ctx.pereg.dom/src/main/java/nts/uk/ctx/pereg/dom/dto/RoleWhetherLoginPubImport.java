package nts.uk.ctx.pereg.dom.dto;

import lombok.Getter;

@Getter
public class RoleWhetherLoginPubImport {

	
	/**就業担当者か*/
	private boolean isEmployeeCharge ;
	/**給与担当者か*/
	private boolean isSalaryProfessional ;
	/**人事担当者か*/
	private boolean isHumanResOfficer ;
	/**オフィスヘルパー担当者か*/
	private boolean isOfficeHelperPersonne ;
	/**個人情報担当者か*/
	private boolean isPersonalInformation ;
	
	public RoleWhetherLoginPubImport (boolean isEmployeeCharge, boolean isSalaryProfessional, boolean isHumanResOfficer, boolean isOfficeHelperPersonne, boolean isPersonalInformation ){
		this.isEmployeeCharge = isEmployeeCharge;
		this.isSalaryProfessional = isSalaryProfessional;
		this.isHumanResOfficer = isHumanResOfficer;
		this.isOfficeHelperPersonne = isOfficeHelperPersonne;
		this.isPersonalInformation = isPersonalInformation;
	}
	
}
