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
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
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
	 * ctx.core.dom.company.CompanyCode,
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableCode)
	 */
	@Override
	public void remove(CompanyCode companyCode, WageTableCode wageTableCode) {
		this.commandProxy().remove(QwtmtWagetableHead.class,
				new QwtmtWagetableHeadPK(companyCode.v(), wageTableCode.v()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#findAll(nts.uk.
	 * ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public List<WageTableHead> findAll(CompanyCode companyCode) {
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
				companyCode.v()));

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
	 * ctx.core.dom.company.CompanyCode, java.lang.String)
	 */
	@Override
	public Optional<WageTableHead> findByCode(CompanyCode companyCode, WageTableCode code) {
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
				companyCode.v()));
		predicateList.add(cb.equal(root.get(QwtmtWagetableHead_.qwtmtWagetableHeadPK)
				.get(QwtmtWagetableHeadPK_.wageTableCd), code));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return Optional.of(em.createQuery(cq).getResultList().stream()
				.map(item -> new WageTableHead(new JpaWageTableHeadGetMemento(item)))
				.collect(Collectors.toList()).get(0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#isDuplicateCode(
	 * nts.uk.ctx.core.dom.company.CompanyCode,
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableCode)
	 */
	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, WageTableCode code) {
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
				companyCode.v()));
		predicateList.add(cb.equal(root.get(QwtmtWagetableHead_.qwtmtWagetableHeadPK)
				.get(QwtmtWagetableHeadPK_.wageTableCd), code));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return (em.createQuery(cq).getSingleResult() > 0);
	}

}
