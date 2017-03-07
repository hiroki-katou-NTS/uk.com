/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

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
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHistPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHistPK_;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHist_;

/**
 * The Class JpaWageTableHistoryRepository.
 */
@Stateless
public class JpaWageTableHistoryRepository extends JpaRepository
		implements WageTableHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository#add(
	 * nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory)
	 */
	@Override
	public void add(WageTableHistory wageTableHistory) {
		QwtmtWagetableHist entity = new QwtmtWagetableHist();
		wageTableHistory.saveToMemento(new JpaWageTableHistorySetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository#
	 * update(nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory)
	 */
	@Override
	public void update(WageTableHistory wageTableHistory) {
		QwtmtWagetableHist entity = new QwtmtWagetableHist();
		wageTableHistory.saveToMemento(new JpaWageTableHistorySetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository#
	 * remove(nts.uk.ctx.core.dom.company.CompanyCode,
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableCode, java.lang.String)
	 */
	@Override
	public void remove(CompanyCode companyCode, WageTableCode code, String historyId) {
		this.commandProxy().remove(QwtmtWagetableHist.class,
				new QwtmtWagetableHistPK(companyCode.v(), code.v(), historyId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository#
	 * findAll(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public List<WageTableHistory> findAll(CompanyCode companyCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableHist> cq = cb.createQuery(QwtmtWagetableHist.class);
		Root<QwtmtWagetableHist> root = cq.from(QwtmtWagetableHist.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
				root.get(QwtmtWagetableHist_.qwtmtWagetableHistPK).get(QwtmtWagetableHistPK_.ccd),
				companyCode.v()));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream()
				.map(item -> new WageTableHistory(new JpaWageTableHistoryGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository#
	 * findById(nts.uk.ctx.core.dom.company.CompanyCode, java.lang.String)
	 */
	@Override
	public Optional<WageTableHistory> findById(CompanyCode companyCode, String historyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QwtmtWagetableHist> cq = cb.createQuery(QwtmtWagetableHist.class);
		Root<QwtmtWagetableHist> root = cq.from(QwtmtWagetableHist.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
				root.get(QwtmtWagetableHist_.qwtmtWagetableHistPK).get(QwtmtWagetableHistPK_.ccd),
				companyCode.v()));
		predicateList.add(cb.equal(root.get(QwtmtWagetableHist_.qwtmtWagetableHistPK)
				.get(QwtmtWagetableHistPK_.histId), historyId));

		cq.where(predicateList.toArray(new Predicate[] {}));

		return Optional.of(em.createQuery(cq).getResultList().stream()
				.map(item -> new WageTableHistory(new JpaWageTableHistoryGetMemento(item)))
				.collect(Collectors.toList()).get(0));
	}

}
