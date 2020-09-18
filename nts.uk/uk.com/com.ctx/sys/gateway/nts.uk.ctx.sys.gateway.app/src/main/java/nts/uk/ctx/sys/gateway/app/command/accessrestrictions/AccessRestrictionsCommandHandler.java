package nts.uk.ctx.sys.gateway.app.command.accessrestrictions;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.accessrestrictions.AccessRestrictions;
import nts.uk.ctx.sys.gateway.dom.accessrestrictions.AccessRestrictionsRepository;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class AccessRestrictionsCommandHandler {

	@Inject
	private AccessRestrictionsRepository repo;
	
	public void insertAccessRestrictions() {
		String contractCode = AppContexts.user().contractCode();
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		if (!domain.isPresent()) {
			repo.insert(new AccessRestrictions(NotUseAtr.NOT_USE, new ContractCode(contractCode), new ArrayList<>()));
		}
	}
	
	public void addAllowdIpAddress(AllowedIPAddressUpdateCommand command) {
		String contractCode = AppContexts.user().contractCode();
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		if (domain.isPresent()) {
			domain.get().addIPAddress(command.allowedIPAddressNew.toDomain());
			repo.update(domain.get());
		}
	}
	
	public void updateAllowdIpAddress(AllowedIPAddressUpdateCommand command) {
		String contractCode = AppContexts.user().contractCode();
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		if (domain.isPresent()) {
			domain.get().updateIPAddress(command.allowedIPAddressOld.toDomain(), command.allowedIPAddressNew.toDomain());
			repo.update(domain.get());
		}
	}
	
	public void deleteAllowdIpAddress(AllowedIPAddressUpdateCommand command) {
		String contractCode = AppContexts.user().contractCode();
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		if (domain.isPresent()) {
			domain.get().deleteIPAddress(command.allowedIPAddressOld.toDomain().getStartAddress());
			repo.update(domain.get());
		}
	}
}
