package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import java.util.Optional;

public interface IdpUserAssociationRepository {

	void add(IdpUserAssociation association);
	
	void delete(IdpUserAssociation association);
	
	void deleteByEmployee(String employeeId);
	
	void deleteByIdpUser(String idpUserId);
	
	static Optional<IdpUserAssociation> findByEmployee(String employeeId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	static Optional<IdpUserAssociation> findByIdpUser(String idpUserId) {
		return null;
	}
	
}
