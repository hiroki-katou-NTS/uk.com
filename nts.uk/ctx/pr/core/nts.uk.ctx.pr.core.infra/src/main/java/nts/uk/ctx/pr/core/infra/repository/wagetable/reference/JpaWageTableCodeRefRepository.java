/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WageTableCodeRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WageTableCodeRefRepository;
import nts.uk.ctx.pr.core.infra.entity.wagetable.reference.QwtmtWagetableRefCd;
import nts.uk.ctx.pr.core.infra.entity.wagetable.reference.QwtmtWagetableRefCdPK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.reference.QwtmtWagetableRefCd_;

/**
 * The Class JpaWageTableCodeRefRepository.
 */
@Stateless
public class JpaWageTableCodeRefRepository extends JpaRepository
		implements WageTableCodeRefRepository {

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository#
	 * findAll(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public List<WageTableCodeRef> findAll(String companyCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableRefCd> cq = cb.createQuery(QwtmtWagetableRefCd.class);
		Root<QwtmtWagetableRefCd> root = cq.from(QwtmtWagetableRefCd.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QwtmtWagetableRefCd_.qwtmtWagetableRefCdPK)
				.get(QwtmtWagetableRefCdPK_.ccd), companyCode));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream()
				.map(item -> new WageTableCodeRef(new JpaWageTableCodeRefGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.reference.WageTableCodeRefRepository#
	 * findByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WageTableCodeRef> findByCode(String companyCode, String code) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableRefCd> cq = cb.createQuery(QwtmtWagetableRefCd.class);
		Root<QwtmtWagetableRefCd> root = cq.from(QwtmtWagetableRefCd.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QwtmtWagetableRefCd_.qwtmtWagetableRefCdPK)
				.get(QwtmtWagetableRefCdPK_.ccd), companyCode));
		predicateList.add(cb.equal(root.get(QwtmtWagetableRefCd_.qwtmtWagetableRefCdPK)
				.get(QwtmtWagetableRefCdPK_.refCdNo), code));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<QwtmtWagetableRefCd> result = em.createQuery(cq).getResultList();

		if (ListUtil.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(result.stream()
				.map(item -> new WageTableCodeRef(new JpaWageTableCodeRefGetMemento(item)))
				.collect(Collectors.toList()).get(0));
	}

}
