package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

public interface TenantAuthenticationRepository {
	
	Optional<TenantAuthentication> find(String tenantCode);
}
