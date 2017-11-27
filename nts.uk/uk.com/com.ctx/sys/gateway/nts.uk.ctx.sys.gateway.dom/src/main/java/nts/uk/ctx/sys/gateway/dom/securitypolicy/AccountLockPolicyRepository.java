package nts.uk.ctx.sys.gateway.dom.securitypolicy;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.login.ContractCode;

public interface AccountLockPolicyRepository {
	Optional<AccountLockPolicy> getAccountLockPolicy(ContractCode contractCode);

	void updateAccountLockPolicy(AccountLockPolicy accountLockPolicy);
}
