package nts.uk.ctx.sys.gateway.app.find.tenantlogin;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;

@Stateless
public class TenanLoginFinder {

	@Inject
	private TenantAuthenticationRepository tenanrep;
	
	public boolean checkExist(String contractCode) {
		Optional<TenantAuthentication> tenanAuth = tenanrep.find(contractCode, GeneralDate.today());
		
		// During the usage period
		if (tenanAuth.isPresent())
			return true;
		else
			return false;
	
	}
}
