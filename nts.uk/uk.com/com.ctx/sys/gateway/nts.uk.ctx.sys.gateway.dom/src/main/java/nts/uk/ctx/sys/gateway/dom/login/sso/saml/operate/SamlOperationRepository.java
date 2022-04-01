package nts.uk.ctx.sys.gateway.dom.login.sso.saml.operate;

import java.util.Optional;

public interface SamlOperationRepository {

	void insert(SamlOperation domain);

	void update(SamlOperation domain);
	
	void delete(SamlOperation domain);
	
	Optional<SamlOperation> find(String tenantCode);
}
