package nts.uk.ctx.sys.gateway.dom.tenantlogin;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

@Stateless
public interface TenantAuthenticateRepository {
	
	void insert(TenantAuthenticate domain);
	
	void update(TenantAuthenticate domain);
	
	void delete(TenantAuthenticate domain);
	
	Optional<TenantAuthenticate> find(String tenantCode);

	Optional<TenantAuthenticate> find(String tenantCode, GeneralDate Date);
}
