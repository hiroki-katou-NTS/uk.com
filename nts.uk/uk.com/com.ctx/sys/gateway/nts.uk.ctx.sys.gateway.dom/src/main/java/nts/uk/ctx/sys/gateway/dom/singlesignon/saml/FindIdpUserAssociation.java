package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import java.util.Optional;

import javax.inject.Inject;

public class FindIdpUserAssociation {
	
	@Inject
	private IdpUserAssociationRepository idpUserAssociationRepository;
	
	public Optional<IdpUserAssociation> byEmployee(String employeeId){
		return idpUserAssociationRepository.findByEmployee(employeeId);
	}
	
	public Optional<IdpUserAssociation> byIdpUser(String idpUserId){
		return idpUserAssociationRepository.findByIdpUser(idpUserId);
	}
}
