package nts.uk.ctx.sys.gateway.dom.login.sso.saml;

import java.util.Optional;

import javax.ejb.Stateless;

@Stateless
public interface IdpUserAssociationRepository {

	void insert(IdpUserAssociation association);
	
	void delete(String tenantCode, String idpUserName);
	
	Optional<IdpUserAssociation> findByIdpUser(String tenantCode, String idpUserName);
}
