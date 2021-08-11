package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicyRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.complexity.PasswordComplexityRequirement;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.SgwmtPasswordPolicy;

/**
 * The Class JpaPasswordPolicyRepository.
 */
@Stateless
public class JpaPasswordPolicyRepository extends JpaRepository implements PasswordPolicyRepository {
	
	/** The select by contract code. */
	private static  final String SELECT_BY_CONTRACT_CODE = "SELECT c FROM SgwmtPasswordPolicy c WHERE c.contractCd = :contractCd";

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicyRepository#getPasswordPolicy(nts.uk.ctx.sys.gateway.dom.login.ContractCode)
	 */
	@Override
	public Optional<PasswordPolicy> getPasswordPolicy(ContractCode contractCd) {
		Optional<SgwmtPasswordPolicy> sgwmtPasswordPolicyOptional = this.queryProxy()
				.query(SELECT_BY_CONTRACT_CODE, SgwmtPasswordPolicy.class)
				.setParameter("contractCd", contractCd, ContractCode.class).getSingle();
		if (sgwmtPasswordPolicyOptional.isPresent()) {
			return Optional.ofNullable(this.toDomain(sgwmtPasswordPolicyOptional.get()));
		}
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicyRepository#updatePasswordPolicy(nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicy)
	 */
	@Override
	public void updatePasswordPolicy(PasswordPolicy passwordPolicy) {
		
		Optional<SgwmtPasswordPolicy> sgwstPasswordPolicyOPtional = this.queryProxy()
				.find(passwordPolicy.getContractCode().v(), SgwmtPasswordPolicy.class);
		
		if (sgwstPasswordPolicyOPtional.isPresent()) {
			SgwmtPasswordPolicy sgwstPasswordPolicy = sgwstPasswordPolicyOPtional.get();
			sgwstPasswordPolicy.notificationPasswordChange = passwordPolicy.getNotificationPasswordChange().v().intValue();
			sgwstPasswordPolicy.loginCheck = passwordPolicy.isLoginCheck();
			sgwstPasswordPolicy.initialPasswordChange = passwordPolicy.isInitialPasswordChange();
			sgwstPasswordPolicy.isUse = passwordPolicy.isUse();
			sgwstPasswordPolicy.historyCount = passwordPolicy.getHistoryCount().v().intValue();
			sgwstPasswordPolicy.lowestDigits = passwordPolicy.getComplexityRequirement().getMinimumLength().v();
			sgwstPasswordPolicy.validityPeriod = passwordPolicy.getValidityPeriod().v().intValue();
			sgwstPasswordPolicy.numberOfDigits = passwordPolicy.getComplexityRequirement().getNumeralDigits().v();
			sgwstPasswordPolicy.symbolCharacters = passwordPolicy.getComplexityRequirement().getSymbolDigits().v();
			sgwstPasswordPolicy.alphabetDigit = passwordPolicy.getComplexityRequirement().getAlphabetDigits().v();

		} else {
			this.commandProxy().insert(this.toEntity(passwordPolicy));
		}
	}

	/**
	 * To domain.
	 *
	 * @param sgwstPasswordPolicy the sgwst password policy
	 * @return the password policy
	 */
	private PasswordPolicy toDomain(SgwmtPasswordPolicy sgwstPasswordPolicy) {
		
		val complexity = PasswordComplexityRequirement.createFromJavaType(
				sgwstPasswordPolicy.lowestDigits,
				sgwstPasswordPolicy.numberOfDigits,
				sgwstPasswordPolicy.symbolCharacters,
				sgwstPasswordPolicy.alphabetDigit);
		
		return PasswordPolicy.createFromJavaType(
				sgwstPasswordPolicy.contractCd,
				sgwstPasswordPolicy.notificationPasswordChange,
				sgwstPasswordPolicy.loginCheck,
				sgwstPasswordPolicy.initialPasswordChange,
				sgwstPasswordPolicy.isUse,
				sgwstPasswordPolicy.historyCount,
				sgwstPasswordPolicy.validityPeriod,
				complexity);
	}

	/**
	 * To entity.
	 *
	 * @param passwordPolicy the password policy
	 * @return the sgwst password policy
	 */
	private SgwmtPasswordPolicy toEntity(PasswordPolicy passwordPolicy) {
		return new SgwmtPasswordPolicy(passwordPolicy.getContractCode().v(),
				passwordPolicy.getNotificationPasswordChange().v().intValue(),
				passwordPolicy.isLoginCheck(),
				passwordPolicy.isInitialPasswordChange(),
				passwordPolicy.isUse(),
				passwordPolicy.getHistoryCount().v().intValue(),
				passwordPolicy.getComplexityRequirement().getMinimumLength().v().intValue(),
				passwordPolicy.getValidityPeriod().v().intValue(),
				passwordPolicy.getComplexityRequirement().getNumeralDigits().v().intValue(),
				passwordPolicy.getComplexityRequirement().getSymbolDigits().v().intValue(),
				passwordPolicy.getComplexityRequirement().getAlphabetDigits().v().intValue());
	}

}
