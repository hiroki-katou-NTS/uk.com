package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import java.util.Optional;

public interface IdpUserAssociationRepository {

	void insert(IdpUserAssociation association);
	
	void delete(IdpUserAssociation association);
	
	Optional<IdpUserAssociation> findByEmployee(String employeeId);
	
	Optional<IdpUserAssociation> findByIdpUser(String idpUserId);
}
