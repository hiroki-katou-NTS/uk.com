package nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

public interface AccountLockPolicyRepository {
	Optional<AccountLockPolicy> getAccountLockPolicy(ContractCode contractCode);

	void updateAccountLockPolicy(AccountLockPolicy accountLockPolicy);
}
