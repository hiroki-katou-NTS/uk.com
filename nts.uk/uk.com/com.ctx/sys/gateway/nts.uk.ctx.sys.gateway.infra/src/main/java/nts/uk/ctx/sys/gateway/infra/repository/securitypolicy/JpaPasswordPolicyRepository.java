package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicyRepository;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.SgwmtPasswordPolicy;

/**
 * The Class JpaPasswordPolicyRepository.
 */
@Stateless
public class JpaPasswordPolicyRepository extends JpaRepository implements PasswordPolicyRepository {
	
	/** The select by contract code. */
	private static  final String SELECT_BY_CONTRACT_CODE = "SELECT c FROM SgwmtPasswordPolicy c WHERE c.contractCode = :contractCode";

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicyRepository#getPasswordPolicy(nts.uk.ctx.sys.gateway.dom.login.ContractCode)
	 */
	@Override
	public Optional<PasswordPolicy> getPasswordPolicy(ContractCode contractCode) {
		Optional<SgwmtPasswordPolicy> sgwmtPasswordPolicyOptional = this.queryProxy()
				.query(SELECT_BY_CONTRACT_CODE, SgwmtPasswordPolicy.class)
				.setParameter("contractCode", contractCode, ContractCode.class).getSingle();
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
		Optional<SgwmtPasswordPolicy> sgwmtPasswordPolicyOPtional = this.queryProxy()
				.find(passwordPolicy.getContractCode().v(), SgwmtPasswordPolicy.class);
		if (sgwmtPasswordPolicyOPtional.isPresent()) {
			SgwmtPasswordPolicy sgwmtPasswordPolicy = sgwmtPasswordPolicyOPtional.get();
			if (passwordPolicy.isUse()) {
				sgwmtPasswordPolicy.notificationPasswordChange = passwordPolicy.getNotificationPasswordChange().v();
				sgwmtPasswordPolicy.loginCheck = new BigDecimal(passwordPolicy.isLoginCheck() ? 1 : 0);
				sgwmtPasswordPolicy.initialPasswordChange = new BigDecimal(
						passwordPolicy.isInitialPasswordChange() ? 1 : 0);
				sgwmtPasswordPolicy.isUse = new BigDecimal(1);
				sgwmtPasswordPolicy.historyCount = passwordPolicy.getHistoryCount().v();
				sgwmtPasswordPolicy.lowestDigits = passwordPolicy.getLowestDigits().v();
				sgwmtPasswordPolicy.validityPeriod = passwordPolicy.getValidityPeriod().v();
				sgwmtPasswordPolicy.numberOfDigits = passwordPolicy.getNumberOfDigits().v();
				sgwmtPasswordPolicy.symbolCharacters = passwordPolicy.getSymbolCharacters().v();
				sgwmtPasswordPolicy.alphabetDigit = passwordPolicy.getAlphabetDigit().v();
			} else {
				sgwmtPasswordPolicy.notificationPasswordChange = new BigDecimal(0);
				sgwmtPasswordPolicy.loginCheck = new BigDecimal(0);
				sgwmtPasswordPolicy.initialPasswordChange = new BigDecimal(0);
				sgwmtPasswordPolicy.isUse = new BigDecimal(0);
				sgwmtPasswordPolicy.historyCount = new BigDecimal(0);
				sgwmtPasswordPolicy.lowestDigits = new BigDecimal(1);
				sgwmtPasswordPolicy.validityPeriod = new BigDecimal(0);
				sgwmtPasswordPolicy.numberOfDigits = new BigDecimal(0);
				sgwmtPasswordPolicy.symbolCharacters = new BigDecimal(0);
				sgwmtPasswordPolicy.alphabetDigit = new BigDecimal(0);
			}

		} else {
			if (passwordPolicy.isUse()) {
				this.commandProxy().insert(this.toEntity(passwordPolicy));
			} else {
				this.commandProxy().insert(new SgwmtPasswordPolicy(passwordPolicy.getContractCode().v(), new BigDecimal(0), new BigDecimal(0),
						new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(1), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)));
			}
		}
	}

	/**
	 * To domain.
	 *
	 * @param sgwmtPasswordPolicy the sgwst password policy
	 * @return the password policy
	 */
	private PasswordPolicy toDomain(SgwmtPasswordPolicy sgwmtPasswordPolicy) {
		return PasswordPolicy.createFromJavaType(sgwmtPasswordPolicy.contractCode,
				sgwmtPasswordPolicy.notificationPasswordChange.intValue(),
				sgwmtPasswordPolicy.loginCheck.intValue() == 1 ? true : false,
				sgwmtPasswordPolicy.initialPasswordChange.intValue() == 1 ? true : false,
				sgwmtPasswordPolicy.isUse.intValue() == 1 ? true : false, sgwmtPasswordPolicy.historyCount.intValue(),
				sgwmtPasswordPolicy.lowestDigits.intValue(), sgwmtPasswordPolicy.validityPeriod.intValue(),
				sgwmtPasswordPolicy.numberOfDigits.intValue(), sgwmtPasswordPolicy.symbolCharacters.intValue(),
				sgwmtPasswordPolicy.alphabetDigit.intValue());
	}

	/**
	 * To entity.
	 *
	 * @param passwordPolicy the password policy
	 * @return the sgwst password policy
	 */
	private SgwmtPasswordPolicy toEntity(PasswordPolicy passwordPolicy) {
		return new SgwmtPasswordPolicy(passwordPolicy.getContractCode().v(),
				passwordPolicy.getNotificationPasswordChange().v(),
				new BigDecimal(passwordPolicy.isLoginCheck() ? 1 : 0),
				new BigDecimal(passwordPolicy.isInitialPasswordChange() ? 1 : 0),
				new BigDecimal(passwordPolicy.isUse() ? 1 : 0), passwordPolicy.getHistoryCount().v(),
				passwordPolicy.getLowestDigits().v(), passwordPolicy.getValidityPeriod().v(),
				passwordPolicy.getNumberOfDigits().v(), passwordPolicy.getSymbolCharacters().v(),
				passwordPolicy.getAlphabetDigit().v());
	}

}
