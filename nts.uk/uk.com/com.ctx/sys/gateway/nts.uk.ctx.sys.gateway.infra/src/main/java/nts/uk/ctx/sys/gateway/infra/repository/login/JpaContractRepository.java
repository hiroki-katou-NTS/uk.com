/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.repository.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.gateway.dom.login.Contract;
import nts.uk.ctx.sys.gateway.dom.login.ContractRepository;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtContract;
import nts.uk.ctx.sys.gateway.infra.entity.login.SgwmtContract_;

/**
 * The Class JpaContractRepository.
 */
@Stateless
public class JpaContractRepository extends JpaRepository implements ContractRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.login.ContractRepository#getContract(java.lang.String)
	 */
	@Override
	public Optional<Contract> getContract(String contractCode) {

		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<SgwmtContract> query = builder.createQuery(SgwmtContract.class);
		Root<SgwmtContract> root = query.from(SgwmtContract.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(SgwmtContract_.contractCd), contractCode));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<SgwmtContract> result = em.createQuery(query).getResultList();
		//get contract
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new Contract(new JpaContractGetMemento(result.get(0))));
		}
	}
}
