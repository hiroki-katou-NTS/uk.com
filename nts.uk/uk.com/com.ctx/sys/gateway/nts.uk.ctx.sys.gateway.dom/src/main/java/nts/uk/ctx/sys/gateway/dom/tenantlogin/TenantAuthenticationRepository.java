package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface TenantAuthenticationRepository {
	
	void insert(TenantAuthentication domain);
	
	void update(TenantAuthentication domain);
	
	void delete(TenantAuthentication domain);
	
	Optional<TenantAuthentication> find(String tenantCode);

	Optional<TenantAuthentication> find(String tenantCode, GeneralDate Date);
}
