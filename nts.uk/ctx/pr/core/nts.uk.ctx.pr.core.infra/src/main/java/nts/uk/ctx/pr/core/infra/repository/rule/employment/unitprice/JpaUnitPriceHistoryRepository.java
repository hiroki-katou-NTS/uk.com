/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
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
import nts.gul.collection.ListUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceDetail;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceDetailPK;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceDetailPK_;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceDetail_;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHeader;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHeaderPK;

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
	public void add(UnitPrice unitPrice, UnitPriceHistory unitPriceHistory) {
		QupmtCUnitpriceDetail entity = new QupmtCUnitpriceDetail();
		unitPriceHistory.saveToMemento(new JpaUnitPriceHistorySetMemento(entity));

		Optional<QupmtCUnitpriceHeader> optQupmtCUnitpriceHead = this.queryProxy().find(
				new QupmtCUnitpriceHeaderPK(unitPrice.getCompanyCode().v(), unitPrice.getCode().v()),
				QupmtCUnitpriceHeader.class);

		QupmtCUnitpriceHeader qupmtCUnitpriceHead = new QupmtCUnitpriceHeader();
		unitPrice.saveToMemento(new JpaUnitPriceSetMemento(qupmtCUnitpriceHead));

		if (optQupmtCUnitpriceHead.isPresent()) {
			this.commandProxy().update(qupmtCUnitpriceHead);
		} else {
			this.commandProxy().insert(qupmtCUnitpriceHead);
		}

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
	public void update(UnitPrice unitPrice, UnitPriceHistory unitPriceHistory) {
		QupmtCUnitpriceDetail entity = new QupmtCUnitpriceDetail();
		unitPriceHistory.saveToMemento(new JpaUnitPriceHistorySetMemento(entity));

		Optional<QupmtCUnitpriceHeader> optQupmtCUnitpriceHead = this.queryProxy().find(
				new QupmtCUnitpriceHeaderPK(unitPrice.getCompanyCode().v(), unitPrice.getCode().v()),
				QupmtCUnitpriceHeader.class);

		QupmtCUnitpriceHeader qupmtCUnitpriceHead = new QupmtCUnitpriceHeader();
		unitPrice.saveToMemento(new JpaUnitPriceSetMemento(qupmtCUnitpriceHead));

		if (optQupmtCUnitpriceHead.isPresent()) {
			this.commandProxy().update(qupmtCUnitpriceHead);
		} else {
			this.commandProxy().insert(qupmtCUnitpriceHead);
		}

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
		this.commandProxy().remove(QupmtCUnitpriceDetail.class,
				new QupmtCUnitpriceDetailPK(companyCode.v(), cUnitpriceCd.v(), histId));
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
		CriteriaQuery<QupmtCUnitpriceDetail> cq = cb.createQuery(QupmtCUnitpriceDetail.class);
		Root<QupmtCUnitpriceDetail> root = cq.from(QupmtCUnitpriceDetail.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(
				cb.equal(root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.ccd),
						companyCode.v()));

		cq.orderBy(cb.desc(root.get(QupmtCUnitpriceDetail_.strYm)));
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
	public Optional<UnitPriceHistory> findById(CompanyCode companyCode, String histId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QupmtCUnitpriceDetail> cq = cb.createQuery(QupmtCUnitpriceDetail.class);
		Root<QupmtCUnitpriceDetail> root = cq.from(QupmtCUnitpriceDetail.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(
				cb.equal(root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.ccd),
						companyCode.v()));
		predicateList.add(cb.equal(
				root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.histId), histId));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<QupmtCUnitpriceDetail> result = em.createQuery(cq).getResultList();

		// Check empty.
		if (ListUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new UnitPriceHistory(new JpaUnitPriceHistoryGetMemento(result.get(0))));
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
		Root<QupmtCUnitpriceDetail> root = cq.from(QupmtCUnitpriceDetail.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(
				cb.equal(root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.ccd),
						companyCode.v()));
		predicateList.add(cb.equal(
				root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.cUnitpriceCd),
				cUnitpriceCd.v()));
		predicateList.add(cb.ge(root.get(QupmtCUnitpriceDetail_.strYm), startMonth.v()));

		cq.select(cb.count(root));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getSingleResult().longValue() > 0L;
	}

	@Override
	public Optional<UnitPriceHistory> findLastHistory(CompanyCode companyCode, UnitPriceCode unitPriceCode) {
		return findByIndex(0, companyCode, unitPriceCode);
	}

	@Override
	public Optional<UnitPriceHistory> findSecondLastHistory(CompanyCode companyCode, UnitPriceCode unitPriceCode) {
		return findByIndex(1, companyCode, unitPriceCode);
	}

	private Optional<UnitPriceHistory> findByIndex(int index, CompanyCode companyCode, UnitPriceCode unitPriceCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QupmtCUnitpriceDetail> cq = cb.createQuery(QupmtCUnitpriceDetail.class);
		Root<QupmtCUnitpriceDetail> root = cq.from(QupmtCUnitpriceDetail.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(
				cb.equal(root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.ccd),
						companyCode.v()));
		predicateList.add(cb.equal(
				root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.cUnitpriceCd),
				unitPriceCode.v()));

		cq.orderBy(cb.desc(root.get(QupmtCUnitpriceDetail_.strYm)));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<QupmtCUnitpriceDetail> result = em.createQuery(cq).getResultList();

		// Check empty.
		if (ListUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new UnitPriceHistory(new JpaUnitPriceHistoryGetMemento(result.get(index))));
	}

	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, UnitPriceCode unitPriceCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QupmtCUnitpriceDetail> cq = cb.createQuery(QupmtCUnitpriceDetail.class);
		Root<QupmtCUnitpriceDetail> root = cq.from(QupmtCUnitpriceDetail.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(
				cb.equal(root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.ccd),
						companyCode.v()));
		predicateList.add(cb.equal(
				root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.cUnitpriceCd),
				unitPriceCode));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<QupmtCUnitpriceDetail> result = em.createQuery(cq).getResultList();

		// Check empty.
		if (ListUtil.isEmpty(result)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository#deleteHistory(java.lang.String)
	 */
	@Override
	public void deleteHistory(String uuid) {
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository#findLastestHistoryByMasterCode(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<UnitPriceHistory> findLastestHistoryByMasterCode(String companyCode, String masterCode) {
		return this.findByIndex(0, new CompanyCode(companyCode), new UnitPriceCode(masterCode));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository#findHistoryByUuid(java.lang.String)
	 */
	@Override
	public Optional<UnitPriceHistory> findHistoryByUuid(String uuid) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QupmtCUnitpriceDetail> cq = cb.createQuery(QupmtCUnitpriceDetail.class);
		Root<QupmtCUnitpriceDetail> root = cq.from(QupmtCUnitpriceDetail.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(
				root.get(QupmtCUnitpriceDetail_.qupmtCUnitpriceDetailPK).get(QupmtCUnitpriceDetailPK_.histId), uuid));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<QupmtCUnitpriceDetail> result = em.createQuery(cq).getResultList();

		// Check empty.
		if (ListUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new UnitPriceHistory(new JpaUnitPriceHistoryGetMemento(result.get(0))));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository#addHistory(nts.uk.ctx.pr.core.dom.base.simplehistory.History)
	 */
	@Override
	public void addHistory(UnitPriceHistory history) {
		QupmtCUnitpriceDetail entity = new QupmtCUnitpriceDetail();
		history.saveToMemento(new JpaUnitPriceHistorySetMemento(entity));
		this.commandProxy().update(entity);		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryRepository#updateHistory(nts.uk.ctx.pr.core.dom.base.simplehistory.History)
	 */
	@Override
	public void updateHistory(UnitPriceHistory unitPriceHistory) {
		QupmtCUnitpriceDetail entity = new QupmtCUnitpriceDetail();
		unitPriceHistory.saveToMemento(new JpaUnitPriceHistorySetMemento(entity));
		this.commandProxy().update(entity);
	}
}
