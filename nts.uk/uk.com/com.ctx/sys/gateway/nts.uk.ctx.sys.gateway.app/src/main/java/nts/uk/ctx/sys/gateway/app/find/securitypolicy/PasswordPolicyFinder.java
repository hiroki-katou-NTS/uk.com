package nts.uk.ctx.sys.gateway.app.find.securitypolicy;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.app.find.securitypolicy.dto.PasswordPolicyDto;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PasswordPolicyFinder {
	@Inject
	private PasswordPolicyRepository passwordPolicyRepository;

	public PasswordPolicyDto getPasswordPolicy() {
		String contractCode = AppContexts.user().contractCode();
		Optional<PasswordPolicy> passwordPolicyOptional = this.passwordPolicyRepository
				.getPasswordPolicy(new ContractCode(contractCode));
		if (passwordPolicyOptional.isPresent()) {
			return this.toDto(passwordPolicyOptional.get());
		} else {
			return null;
		}
	}

	private PasswordPolicyDto toDto(PasswordPolicy passwordPolicy) {
		return new PasswordPolicyDto(
				passwordPolicy.getNotificationPasswordChange().v().intValue(), passwordPolicy.isLoginCheck(),
				passwordPolicy.isInitialPasswordChange(), passwordPolicy.isUse(),
				passwordPolicy.getHistoryCount().v().intValue(), passwordPolicy.getLowestDigits().v().intValue(),
				passwordPolicy.getValidityPeriod().v().intValue(), passwordPolicy.getNumberOfDigits().v().intValue(),
				passwordPolicy.getSymbolCharacters().v().intValue(), passwordPolicy.getAlphabetDigit().v().intValue());
	}

}
