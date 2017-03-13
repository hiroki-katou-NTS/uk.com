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
import nts.uk.ctx.pr.core.dom.wagetable.reference.WageTableMasterRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WageTableMasterRefRepository;
import nts.uk.ctx.pr.core.infra.entity.wagetable.reference.QwtmtWagetableRefTable;
import nts.uk.ctx.pr.core.infra.entity.wagetable.reference.QwtmtWagetableRefTablePK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.reference.QwtmtWagetableRefTable_;

/**
 * The Class JpaWageTableMasterRefRepository.
 */
@Stateless
public class JpaWageTableMasterRefRepository extends JpaRepository
		implements WageTableMasterRefRepository {

	/*
	 * (non-Javadoc)
	 *
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository#
	 * findAll(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public List<WageTableMasterRef> findAll(String companyCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableRefTable> cq = cb.createQuery(QwtmtWagetableRefTable.class);
		Root<QwtmtWagetableRefTable> root = cq.from(QwtmtWagetableRefTable.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QwtmtWagetableRefTable_.qwtmtWagetableRefTablePK)
				.get(QwtmtWagetableRefTablePK_.ccd), companyCode));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream()
				.map(item -> new WageTableMasterRef(new JpaWageTableMasterRefGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.reference.WageTableMasterRefRepository#
	 * findByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WageTableMasterRef> findByCode(String companyCode, String code) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableRefTable> cq = cb.createQuery(QwtmtWagetableRefTable.class);
		Root<QwtmtWagetableRefTable> root = cq.from(QwtmtWagetableRefTable.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QwtmtWagetableRefTable_.qwtmtWagetableRefTablePK)
				.get(QwtmtWagetableRefTablePK_.ccd), companyCode));
		predicateList.add(cb.equal(root.get(QwtmtWagetableRefTable_.qwtmtWagetableRefTablePK)
				.get(QwtmtWagetableRefTablePK_.refTableNo), code));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<QwtmtWagetableRefTable> result = em.createQuery(cq).getResultList();

		if (ListUtil.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(result.stream()
				.map(item -> new WageTableMasterRef(new JpaWageTableMasterRefGetMemento(item)))
				.collect(Collectors.toList()).get(0));
	}

}
