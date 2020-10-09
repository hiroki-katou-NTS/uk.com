package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import lombok.Getter;

@Getter
public class SamlOperation {
	
	private int contractCode;
	
	private boolean useSingleSignOn;
	
	private boolean addIdpAccountAuto;
	
	private SamlOperation(int contractCode, boolean useSingleSignOn, boolean addIdpAccountAuto) {
		this.contractCode = contractCode;
		this.useSingleSignOn = useSingleSignOn;
		this.addIdpAccountAuto = addIdpAccountAuto;
	}
	
	public void employeeCreated(String companyCode, String employeeId, String employeeCode) {
		if(this.useSingleSignOn && this.addIdpAccountAuto) {
			String idpUserName = generateIdpUserName(companyCode, employeeCode);
			IdpUserAssociation association = new IdpUserAssociation(employeeId, idpUserName);
		}
	}
	
	private String generateIdpUserName(String companyCode, String employeeCode) {
		return "";
	}

}
