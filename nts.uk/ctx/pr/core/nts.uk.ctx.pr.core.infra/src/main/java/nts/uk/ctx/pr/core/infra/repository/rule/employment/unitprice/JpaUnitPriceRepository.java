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
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHead;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHeadPK;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHeadPK_;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHead_;

/**
 * The Class JpaUnitPriceRepository.
 */
@Stateless
public class JpaUnitPriceRepository extends JpaRepository implements UnitPriceRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#add(
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice)
	 */
	@Override
	public void add(UnitPrice unitPrice) {
		QupmtCUnitpriceHead entity = new QupmtCUnitpriceHead();
		unitPrice.saveToMemento(new JpaUnitPriceSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * update(nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice)
	 */
	@Override
	public void update(UnitPrice unitPrice) {
		QupmtCUnitpriceHead entity = new QupmtCUnitpriceHead();
		unitPrice.saveToMemento(new JpaUnitPriceSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(CompanyCode companyCode, UnitPriceCode code) {
		this.commandProxy().remove(QupmtCUnitpriceHead.class, new QupmtCUnitpriceHeadPK(companyCode.v(), code.v()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * findAll(int)
	 */
	@Override
	public List<UnitPrice> findAll(CompanyCode companyCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QupmtCUnitpriceHead> cq = cb.createQuery(QupmtCUnitpriceHead.class);
		Root<QupmtCUnitpriceHead> root = cq.from(QupmtCUnitpriceHead.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QupmtCUnitpriceHead_.qupmtCUnitpriceHeadPK).get(QupmtCUnitpriceHeadPK_.ccd),
				companyCode.v()));

		cq.orderBy(
				cb.asc(root.get(QupmtCUnitpriceHead_.qupmtCUnitpriceHeadPK).get(QupmtCUnitpriceHeadPK_.cUnitpriceCd)));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getResultList().stream().map(item -> new UnitPrice(new JpaUnitPriceGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * findById(java.lang.String)
	 */
	@Override
	public Optional<UnitPrice> findByCode(CompanyCode companyCode, UnitPriceCode code) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		QupmtCUnitpriceHead entity = em.find(QupmtCUnitpriceHead.class,
				new QupmtCUnitpriceHeadPK(companyCode.v(), code.v()));

		// Return
		return Optional.ofNullable(new UnitPrice(new JpaUnitPriceGetMemento(entity)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * isDuplicateCode(nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceCode)
	 */
	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, UnitPriceCode code) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create query condition.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<QupmtCUnitpriceHead> root = cq.from(QupmtCUnitpriceHead.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QupmtCUnitpriceHead_.qupmtCUnitpriceHeadPK).get(QupmtCUnitpriceHeadPK_.ccd),
				companyCode.v()));
		predicateList.add(
				cb.equal(root.get(QupmtCUnitpriceHead_.qupmtCUnitpriceHeadPK).get(QupmtCUnitpriceHeadPK_.cUnitpriceCd),
						code.v()));

		cq.select(cb.count(root));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return em.createQuery(cq).getSingleResult().longValue() > 0L;
	}

}
