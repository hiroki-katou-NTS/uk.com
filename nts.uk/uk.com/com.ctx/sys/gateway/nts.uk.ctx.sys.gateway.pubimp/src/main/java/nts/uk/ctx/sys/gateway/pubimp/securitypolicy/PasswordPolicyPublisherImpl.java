package nts.uk.ctx.sys.gateway.pubimp.securitypolicy;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyRepository;
import nts.uk.ctx.sys.gateway.pub.securitypolicy.PasswordPolicyDto;
import nts.uk.ctx.sys.gateway.pub.securitypolicy.PasswordPolicyPublisher;
@Stateless
public class PasswordPolicyPublisherImpl implements PasswordPolicyPublisher{
	@Inject
	private PasswordPolicyRepository PasswordPolicyRepo;
	@Override
	public Optional<PasswordPolicyDto> getPasswordPolicy(String contractCode) {
		Optional<PasswordPolicy> passwordPolicyOpt = this.PasswordPolicyRepo.getPasswordPolicy(new ContractCode(contractCode));
		if(passwordPolicyOpt.isPresent()){
			PasswordPolicy passwordPolicy = passwordPolicyOpt.get();
			return Optional.ofNullable(new PasswordPolicyDto(contractCode, passwordPolicy.getNotificationPasswordChange().v().signum(), passwordPolicy.isLoginCheck(), passwordPolicy.isInitialPasswordChange(), passwordPolicy.isUse(), passwordPolicy.getHistoryCount().v().intValue(), passwordPolicy.getLowestDigits().v().intValue(), passwordPolicy.getValidityPeriod().v().intValue(), passwordPolicy.getNumberOfDigits().v().intValue(), passwordPolicy.getSymbolCharacters().v().intValue(), passwordPolicy.getAlphabetDigit().v().intValue())); 
		}
		return Optional.empty();
	}

	@Override
	public void updatePasswordPolicy(PasswordPolicyDto passwordPolicy) {
		this.PasswordPolicyRepo.updatePasswordPolicy(this.toDomain(passwordPolicy));
	}
	
	private PasswordPolicy toDomain(PasswordPolicyDto passwordPolicy) {
		return PasswordPolicy.createFromJavaType(passwordPolicy.getContractCode(),
				passwordPolicy.getNotificationPasswordChange(),
				passwordPolicy.isLoginCheck(),
				passwordPolicy.initialPasswordChange,
				passwordPolicy.isUse, passwordPolicy.historyCount,
				passwordPolicy.lowestDigits, passwordPolicy.validityPeriod,
				passwordPolicy.numberOfDigits, passwordPolicy.symbolCharacters,
				passwordPolicy.alphabetDigit);
	}

}
