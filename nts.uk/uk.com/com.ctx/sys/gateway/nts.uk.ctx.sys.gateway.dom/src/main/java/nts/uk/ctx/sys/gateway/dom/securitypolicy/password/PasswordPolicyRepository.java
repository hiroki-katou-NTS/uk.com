package nts.uk.ctx.sys.gateway.dom.securitypolicy.password;

import java.util.Optional;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;

public interface PasswordPolicyRepository {

	void insert(PasswordPolicy domain);

	void update(PasswordPolicy domain);

	void updatePasswordPolicy(PasswordPolicy passwordPolicy);
	
	Optional<PasswordPolicy> getPasswordPolicy(String tenantCode);
	
	Optional<PasswordPolicy> getPasswordPolicy(ContractCode contractCode);
}
