package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import java.util.Optional;

import lombok.Getter;
import lombok.val;

@Getter
public class IdpUserAssociation {
	
	//社員ID
	private String employeeId;
	
	//Idpユーザ名
	private String idpUserName;

	
	public IdpUserAssociation(String employeeId, String idpUserName) {
		this.employeeId = employeeId;
		this.idpUserName = idpUserName;	
	}
	
	public static Optional<String> getAssociateEmployee(String idpUserName) {
		val association = IdpUserAssociationRepository.findByIdpUser(idpUserName);
		if(association.isPresent()) {
			return Optional.of(association.get().employeeId);
		}
		return Optional.empty();
	}
	
	public static Optional<String> getAssociateIdpUserName(String employeeId) {
		val association = IdpUserAssociationRepository.findByEmployee(employeeId);
		if(association.isPresent()) {
			return Optional.of(association.get().idpUserName);
		}
		return Optional.empty();
	}
	
}
