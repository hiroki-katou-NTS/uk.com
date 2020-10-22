package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import java.util.Optional;

import javax.ejb.Stateless;

public interface SamlOperationRepository {

	void insert(SamlOperation domain);

	void update(SamlOperation domain);
	
	void delete(SamlOperation domain);
	
	Optional<SamlOperation> find(String tenantCode);
}
