/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.rule.employment.unitprice;

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
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHist;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHistPK;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHistPK_;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHist_;

/**
 * The Class JpaUnitPriceHistoryRepository.
 */
@Stateless
public class JpaUnitPriceHistoryRepository extends JpaRepository implements UnitPriceHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistoryRepository#add(nts.uk.ctx.pr.core.dom.rule.employment.
	 * unitprice.UnitPriceHistory)
	 */
	@Override
	public void add(UnitPriceHistory unitPriceHistory) {
		QupmtCUnitpriceHist entity = new QupmtCUnitpriceHist();
		unitPriceHistory.saveToMemento(new JpaUnitPriceHistorySetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistoryRepository#update(nts.uk.ctx.pr.core.dom.rule.employment.
	 * unitprice.UnitPriceHistory)
	 */
	@Override
	public void update(UnitPriceHistory unitPriceHistory) {
		QupmtCUnitpriceHist entity = new QupmtCUnitpriceHist();
		unitPriceHistory.saveToMemento(new JpaUnitPriceHistorySetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistoryRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(CompanyCode companyCode, UnitPriceCode cUnitpriceCd, String histId) {
		this.commandProxy().remove(QupmtCUnitpriceHist.class,
				new QupmtCUnitpriceHistPK(companyCode.v(), cUnitpriceCd.v(), histId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistoryRepository#findAll(int)
	 */
	@Override
	public List<UnitPriceHistory> findAll(CompanyCode companyCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QupmtCUnitpriceHist> cq = cb.createQuery(QupmtCUnitpriceHist.class);
		Root<QupmtCUnitpriceHist> root = cq.from(QupmtCUnitpriceHist.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QupmtCUnitpriceHist_.qupmtCUnitpriceHistPK).get(QupmtCUnitpriceHistPK_.ccd),
				companyCode.v()));

		cq.orderBy(cb.asc(root.get(QupmtCUnitpriceHist_.strYm)));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream()
				.map(item -> new UnitPriceHistory(new JpaUnitPriceHistoryGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistoryRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<UnitPriceHistory> findById(CompanyCode companyCode, UnitPriceCode cUnitpriceCd, String histId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		QupmtCUnitpriceHist entity = em.find(QupmtCUnitpriceHist.class,
				new QupmtCUnitpriceHistPK(companyCode.v(), cUnitpriceCd.v(), histId));

		// Return
		return Optional.ofNullable(new UnitPriceHistory(new JpaUnitPriceHistoryGetMemento(entity)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistoryRepository#isInvalidDateRange(nts.arc.time.YearMonth)
	 */
	@Override
	public boolean isInvalidDateRange(CompanyCode companyCode, UnitPriceCode cUnitpriceCd, YearMonth startMonth) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create query condition.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<QupmtCUnitpriceHist> root = cq.from(QupmtCUnitpriceHist.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QupmtCUnitpriceHist_.qupmtCUnitpriceHistPK).get(QupmtCUnitpriceHistPK_.ccd),
				companyCode.v()));
		predicateList.add(
				cb.equal(root.get(QupmtCUnitpriceHist_.qupmtCUnitpriceHistPK).get(QupmtCUnitpriceHistPK_.cUnitpriceCd),
						cUnitpriceCd.v()));
		predicateList.add(cb.ge(root.get(QupmtCUnitpriceHist_.strYm), startMonth.v()));
		predicateList.add(cb.le(root.get(QupmtCUnitpriceHist_.endYm), startMonth.v()));

		cq.select(cb.count(root));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getSingleResult().longValue() > 0L;
	}
}
