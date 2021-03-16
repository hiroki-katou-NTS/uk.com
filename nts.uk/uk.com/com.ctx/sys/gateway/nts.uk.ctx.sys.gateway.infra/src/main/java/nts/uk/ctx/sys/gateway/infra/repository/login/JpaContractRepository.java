/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.gateway.dom.loginold.Contract;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractGetMemento;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractRepository;
import nts.uk.ctx.sys.gateway.dom.loginold.HashPassword;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthenticationRepository;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtContract;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwdtContract_;

/**
 * The Class JpaContractRepository.
 */
@Stateless
public class JpaContractRepository extends JpaRepository implements ContractRepository {

	@Inject
	private TenantAuthenticationRepository tenantRepo;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.ContractRepository#getContract(java.lang.String)
	 */
	@Override
	public Optional<Contract> getContract(String contractCode) {
		
		// Contractクラスは削除予定。それまでの仮実装
		return tenantRepo.find(contractCode)
				.map(t -> new Contract(new ContractGetMemento() {
					
					@Override
					public HashPassword getPassword() {
						return new HashPassword(t.getHashedPassword());
					}
					
					@Override
					public DatePeriod getContractPeriod() {
						return new DatePeriod(GeneralDate.min(), GeneralDate.max());
					}
					
					@Override
					public ContractCode getContractCode() {
						return new ContractCode(t.getTenantCode());
					}
				}));

	}
}
