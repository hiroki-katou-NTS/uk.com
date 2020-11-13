package nts.uk.ctx.sys.gateway.infra.repository.securitypolicy;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicy;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicyRepository;
import nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.SgwstAccountLockPolicy;

/**
 * The Class JpaAccountLockPolicy.
 */
@Stateless
public class JpaAccountLockPolicy extends JpaRepository implements AccountLockPolicyRepository {
	
	/** The select by contract code. */
	private static final String SELECT_BY_CONTRACT_CODE = "SELECT c FROM SgwstAccountLockPolicy c WHERE c.contractCode = :contractCode";

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicyRepository#getAccountLockPolicy(nts.uk.ctx.sys.gateway.dom.login.ContractCode)
	 */
	@Override
	public Optional<AccountLockPolicy> getAccountLockPolicy(ContractCode contractCode) {
		Optional<SgwstAccountLockPolicy> sgwstAccountLockPolicyOptional = this.queryProxy()
				.query(SELECT_BY_CONTRACT_CODE, SgwstAccountLockPolicy.class)
				.setParameter("contractCode", contractCode, ContractCode.class).getSingle();
		if (sgwstAccountLockPolicyOptional.isPresent()) {
			return Optional.ofNullable(this.toDomain(sgwstAccountLockPolicyOptional.get()));
		}
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicyRepository#updateAccountLockPolicy(nts.uk.ctx.sys.gateway.dom.securitypolicy.AccountLockPolicy)
	 */
	@Override
	public void updateAccountLockPolicy(AccountLockPolicy accountLockPolicy) {
		Optional<SgwstAccountLockPolicy> sgwstAccountLockPolicyOptional = this.queryProxy()
				.find(accountLockPolicy.getContractCode().v(), SgwstAccountLockPolicy.class);
		if (sgwstAccountLockPolicyOptional.isPresent()) {
			SgwstAccountLockPolicy sgwstAccountLockPolicy = sgwstAccountLockPolicyOptional.get();
			if (accountLockPolicy.isUse()) {
				sgwstAccountLockPolicy.errorCount = accountLockPolicy.getErrorCount().v();
				sgwstAccountLockPolicy.isUse = new BigDecimal(1);
				sgwstAccountLockPolicy.lockInterval = new BigDecimal(accountLockPolicy.getLockInterval().v());
				sgwstAccountLockPolicy.lockOutMessage = accountLockPolicy.getLockOutMessage().v() == "" ? "　"
						: accountLockPolicy.getLockOutMessage().v();

			} else {
				sgwstAccountLockPolicy.errorCount = new BigDecimal(1);
				sgwstAccountLockPolicy.isUse = new BigDecimal(0);
				sgwstAccountLockPolicy.lockInterval = new BigDecimal(0);
				sgwstAccountLockPolicy.lockOutMessage = "　";
			}
		} else {
			if (accountLockPolicy.isUse()) {
				this.commandProxy().insert(this.toEntity(accountLockPolicy));
			} else {
				this.commandProxy().insert(new SgwstAccountLockPolicy(accountLockPolicy.getContractCode().v(),
						new BigDecimal(1), new BigDecimal(0), "　", new BigDecimal(0)));
			}
		}
	}

	/**
	 * To domain.
	 *
	 * @param sgwstAccountLockPolicy the sgwst account lock policy
	 * @return the account lock policy
	 */
	private AccountLockPolicy toDomain(SgwstAccountLockPolicy sgwstAccountLockPolicy) {
		return AccountLockPolicy.createFromJavaType(sgwstAccountLockPolicy.contractCode,
				sgwstAccountLockPolicy.errorCount.intValue(), sgwstAccountLockPolicy.lockInterval.intValue(),
				sgwstAccountLockPolicy.lockOutMessage, sgwstAccountLockPolicy.isUse.intValue() == 1 ? true : false);
	}

	/**
	 * To entity.
	 *
	 * @param accountLockPolicy the account lock policy
	 * @return the sgwst account lock policy
	 */
	private SgwstAccountLockPolicy toEntity(AccountLockPolicy accountLockPolicy) {
		return new SgwstAccountLockPolicy(accountLockPolicy.getContractCode().v(),
				accountLockPolicy.getErrorCount().v(), new BigDecimal(accountLockPolicy.getLockInterval().v()),
				accountLockPolicy.getLockOutMessage().v() == "" ? "　" : accountLockPolicy.getLockOutMessage().v(),
				new BigDecimal(accountLockPolicy.isUse() ? 1 : 0));
	}

}
