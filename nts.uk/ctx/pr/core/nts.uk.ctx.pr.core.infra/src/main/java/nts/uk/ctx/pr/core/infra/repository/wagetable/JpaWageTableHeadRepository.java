/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

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
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHead;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHeadPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHeadPK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHead_;

/**
 * The Class JpaWageTableHeadRepository.
 */
@Stateless
public class JpaWageTableHeadRepository extends JpaRepository implements WageTableHeadRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#add(nts.uk.ctx.
	 * pr.core.dom.wagetable.WageTableHead)
	 */
	@Override
	public void add(WageTableHead wageTableHead) {
		QwtmtWagetableHead entity = new QwtmtWagetableHead();
		wageTableHead.saveToMemento(new JpaWageTableHeadSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#update(nts.uk.
	 * ctx.pr.core.dom.wagetable.WageTableHead)
	 */
	@Override
	public void update(WageTableHead wageTableHead) {
		QwtmtWagetableHead entity = new QwtmtWagetableHead();
		wageTableHead.saveToMemento(new JpaWageTableHeadSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#remove(nts.uk.
	 * ctx.core.dom.company.String, nts.uk.ctx.pr.core.dom.wagetable.String)
	 */
	@Override
	public void remove(String companyCode, String wageTableCode) {
		this.commandProxy().remove(QwtmtWagetableHead.class,
				new QwtmtWagetableHeadPK(companyCode, wageTableCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#findAll(nts.uk.
	 * ctx.core.dom.company.String)
	 */
	@Override
	public List<WageTableHead> findAll(String companyCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableHead> cq = cb.createQuery(QwtmtWagetableHead.class);
		Root<QwtmtWagetableHead> root = cq.from(QwtmtWagetableHead.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
				root.get(QwtmtWagetableHead_.qwtmtWagetableHeadPK).get(QwtmtWagetableHeadPK_.ccd),
				companyCode));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream()
				.map(item -> new WageTableHead(new JpaWageTableHeadGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#findById(nts.uk.
	 * ctx.core.dom.company.String, java.lang.String)
	 */
	@Override
	public Optional<WageTableHead> findByCode(String companyCode, String code) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableHead> cq = cb.createQuery(QwtmtWagetableHead.class);
		Root<QwtmtWagetableHead> root = cq.from(QwtmtWagetableHead.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
				root.get(QwtmtWagetableHead_.qwtmtWagetableHeadPK).get(QwtmtWagetableHeadPK_.ccd),
				companyCode));
		predicateList.add(cb.equal(root.get(QwtmtWagetableHead_.qwtmtWagetableHeadPK)
				.get(QwtmtWagetableHeadPK_.wageTableCd), code));

		cq.where(predicateList.toArray(new Predicate[] {}));

		List<QwtmtWagetableHead> result = em.createQuery(cq).getResultList();

		if (ListUtil.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(
				result.stream().map(item -> new WageTableHead(new JpaWageTableHeadGetMemento(item)))
						.collect(Collectors.toList()).get(0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#isDuplicateCode(
	 * nts.uk.ctx.core.dom.company.String,
	 * nts.uk.ctx.pr.core.dom.wagetable.String)
	 */
	@Override
	public boolean isExistCode(String companyCode, String code) {
		// Get entity manager
		EntityManager em = getEntityManager();

		// Create query
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(QwtmtWagetableHead.class)));
		Root<QwtmtWagetableHead> root = cq.from(QwtmtWagetableHead.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
				root.get(QwtmtWagetableHead_.qwtmtWagetableHeadPK).get(QwtmtWagetableHeadPK_.ccd),
				companyCode));
		predicateList.add(cb.equal(root.get(QwtmtWagetableHead_.qwtmtWagetableHeadPK)
				.get(QwtmtWagetableHeadPK_.wageTableCd), code));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return (em.createQuery(cq).getSingleResult() > 0);
	}

}
