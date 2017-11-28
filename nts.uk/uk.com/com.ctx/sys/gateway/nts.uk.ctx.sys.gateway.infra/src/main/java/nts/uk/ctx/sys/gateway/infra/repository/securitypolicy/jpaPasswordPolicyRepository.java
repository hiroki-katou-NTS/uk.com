package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicyRepository;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.SgwstPasswordPolicy;

@Stateless
public class jpaPasswordPolicyRepository extends JpaRepository implements PasswordPolicyRepository {
	private final String SELECT_BY_CONTRACT_CODE = "SELECT c FROM SgwstPasswordPolicy c WHERE c.contractCode = :contractCode";

	@Override
	public Optional<PasswordPolicy> getPasswordPolicy(ContractCode contractCode) {
		Optional<SgwstPasswordPolicy> sgwstPasswordPolicyOptional = this.queryProxy()
				.query(SELECT_BY_CONTRACT_CODE, SgwstPasswordPolicy.class)
				.setParameter("contractCode", contractCode, ContractCode.class).getSingle();
		if (sgwstPasswordPolicyOptional.isPresent()) {
			return Optional.ofNullable(this.toDomain(sgwstPasswordPolicyOptional.get()));
		}
		return Optional.empty();
	}

	@Override
	public void updatePasswordPolicy(PasswordPolicy passwordPolicy) {
		Optional<SgwstPasswordPolicy> sgwstPasswordPolicyOPtional = this.queryProxy()
				.find(passwordPolicy.getContractCode().v(), SgwstPasswordPolicy.class);
		if (sgwstPasswordPolicyOPtional.isPresent()) {
			SgwstPasswordPolicy sgwstPasswordPolicy = sgwstPasswordPolicyOPtional.get();
			if (passwordPolicy.isUse()) {
				sgwstPasswordPolicy.notificationPasswordChange = passwordPolicy.getNotificationPasswordChange().v();
				sgwstPasswordPolicy.loginCheck = new BigDecimal(passwordPolicy.isLoginCheck() ? 1 : 0);
				sgwstPasswordPolicy.initialPasswordChange = new BigDecimal(
						passwordPolicy.isInitialPasswordChange() ? 1 : 0);
				sgwstPasswordPolicy.isUse = new BigDecimal(1);
				sgwstPasswordPolicy.historyCount = passwordPolicy.getHistoryCount().v();
				sgwstPasswordPolicy.lowestDigits = passwordPolicy.getLowestDigits().v();
				sgwstPasswordPolicy.validityPeriod = passwordPolicy.getValidityPeriod().v();
				sgwstPasswordPolicy.numberOfDigits = passwordPolicy.getNumberOfDigits().v();
				sgwstPasswordPolicy.symbolCharacters = passwordPolicy.getSymbolCharacters().v();
				sgwstPasswordPolicy.alphabetDigit = passwordPolicy.getAlphabetDigit().v();
			} else {
				sgwstPasswordPolicy.notificationPasswordChange = null;
				sgwstPasswordPolicy.loginCheck = null;
				sgwstPasswordPolicy.initialPasswordChange = null;
				sgwstPasswordPolicy.isUse = new BigDecimal(0);
				sgwstPasswordPolicy.historyCount = null;
				sgwstPasswordPolicy.lowestDigits = null;
				sgwstPasswordPolicy.validityPeriod = null;
				sgwstPasswordPolicy.numberOfDigits = null;
				sgwstPasswordPolicy.symbolCharacters = null;
				sgwstPasswordPolicy.alphabetDigit = null;
			}

		} else {
			if (passwordPolicy.isUse()) {
				this.commandProxy().insert(this.toEntity(passwordPolicy));
			} else {
				this.commandProxy().insert(new SgwstPasswordPolicy(passwordPolicy.getContractCode().v(), null, null,
						null, new BigDecimal(0), null, null, null, null, null, null));
			}
		}
	}

	private PasswordPolicy toDomain(SgwstPasswordPolicy sgwstPasswordPolicy) {
		return PasswordPolicy.createFromJavaType(sgwstPasswordPolicy.contractCode,
				sgwstPasswordPolicy.notificationPasswordChange.intValue(),
				sgwstPasswordPolicy.loginCheck.intValue() == 1 ? true : false,
				sgwstPasswordPolicy.initialPasswordChange.intValue() == 1 ? true : false,
				sgwstPasswordPolicy.isUse.intValue() == 1 ? true : false, sgwstPasswordPolicy.historyCount.intValue(),
				sgwstPasswordPolicy.lowestDigits.intValue(), sgwstPasswordPolicy.validityPeriod.intValue(),
				sgwstPasswordPolicy.numberOfDigits.intValue(), sgwstPasswordPolicy.symbolCharacters.intValue(),
				sgwstPasswordPolicy.alphabetDigit.intValue());
	}

	private SgwstPasswordPolicy toEntity(PasswordPolicy passwordPolicy) {
		return new SgwstPasswordPolicy(passwordPolicy.getContractCode().v(),
				passwordPolicy.getNotificationPasswordChange().v(),
				new BigDecimal(passwordPolicy.isLoginCheck() ? 1 : 0),
				new BigDecimal(passwordPolicy.isInitialPasswordChange() ? 1 : 0),
				new BigDecimal(passwordPolicy.isUse() ? 1 : 0), passwordPolicy.getHistoryCount().v(),
				passwordPolicy.getLowestDigits().v(), passwordPolicy.getValidityPeriod().v(),
				passwordPolicy.getNumberOfDigits().v(), passwordPolicy.getSymbolCharacters().v(),
				passwordPolicy.getAlphabetDigit().v());
	}

}
