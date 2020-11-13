package nts.uk.ctx.sys.gateway.dom.securitypolicy;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

public interface PasswordPolicyRepository {
	Optional<PasswordPolicy> getPasswordPolicy(ContractCode contractCode);

	void updatePasswordPolicy(PasswordPolicy passwordPolicy);
}
