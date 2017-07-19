/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPattern_;

/**
 * The Class JpaMonthlyPatternRepository.
 */
@Stateless
public class JpaMonthlyPatternRepository extends JpaRepository implements MonthlyPatternRepository{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternRepository#add(nts
	 * .uk.ctx.at.schedule.dom.shift.pattern.MonthlyPattern)
	 */
	@Override
	public void add(MonthlyPattern monthlyPattern) {
		this.commandProxy().insert(this.toEntity(monthlyPattern));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternRepository#update(
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPattern)
	 */
	@Override
	public void update(MonthlyPattern monthlyPattern) {
		this.commandProxy().update(this.toEntityUpdate(monthlyPattern));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternRepository#findAll
	 * (java.lang.String)
	 */
	@Override
	public List<MonthlyPattern> findAll(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMPST_MONTH_PATTERN (KmpstMonthPattern SQL)
		CriteriaQuery<KmpstMonthPattern> cq = criteriaBuilder.createQuery(KmpstMonthPattern.class);

		// root data
		Root<KmpstMonthPattern> root = cq.from(KmpstMonthPattern.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KmpstMonthPattern_.kmpstMonthPatternPK).get(KmpstMonthPatternPK_.cid),
				companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder.asc(root.get(KmpstMonthPattern_.kmpstMonthPatternPK)
				.get(KmpstMonthPatternPK_.patternCd)));

		// create query
		TypedQuery<KmpstMonthPattern> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternRepository#
	 * findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<MonthlyPattern> findById(String companyId, String monthlyPatternCode) {
		return this.queryProxy().find(new KmpstMonthPatternPK(companyId, monthlyPatternCode),
				KmpstMonthPattern.class).map(c -> this.toDomain(c));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternRepository#remove(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String monthlyPatternCode) {
		this.commandProxy().remove(KmpstMonthPattern.class,
				new KmpstMonthPatternPK(companyId, monthlyPatternCode));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the monthly pattern
	 */
	private MonthlyPattern toDomain(KmpstMonthPattern entity){
		return new MonthlyPattern(new JpaMonthlyPatternGetMemento(entity));
	}
	
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kmpst month pattern
	 */
	private KmpstMonthPattern toEntity(MonthlyPattern domain){
		KmpstMonthPattern entity = new KmpstMonthPattern();
		domain.saveToMemento(new JpaMonthlyPatternSetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity update.
	 *
	 * @param domain the domain
	 * @return the kmpst month pattern
	 */
	private KmpstMonthPattern toEntityUpdate(MonthlyPattern domain){
		// find by id => optional data
		Optional<KmpstMonthPattern> optionalEntity = this.queryProxy()
				.find(new KmpstMonthPatternPK(domain.getCompanyId().v(),
						domain.getMonthlyPatternCode().v()), KmpstMonthPattern.class);
		KmpstMonthPattern entity = new KmpstMonthPattern();
		// check exist data find by id
		if(optionalEntity.isPresent()){
			entity = optionalEntity.get();
		}
		domain.saveToMemento(new JpaMonthlyPatternSetMemento(entity));
		return entity;
	}

	
}
