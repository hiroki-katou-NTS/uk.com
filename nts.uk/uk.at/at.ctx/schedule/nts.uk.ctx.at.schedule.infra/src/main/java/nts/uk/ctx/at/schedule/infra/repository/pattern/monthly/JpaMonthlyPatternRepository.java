/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.monthly;

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
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPatternPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPatternPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPattern_;

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

		// call KMPMT_MONTH_PATTERN (KmpmtMonthPattern SQL)
		CriteriaQuery<KscmtMonthPattern> cq = criteriaBuilder.createQuery(KscmtMonthPattern.class);

		// root data
		Root<KscmtMonthPattern> root = cq.from(KscmtMonthPattern.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtMonthPattern_.kscmtMonthPatternPK).get(KscmtMonthPatternPK_.cid),
				companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder.asc(root.get(KscmtMonthPattern_.kscmtMonthPatternPK)
				.get(KscmtMonthPatternPK_.mPatternCd)));

		// create query
		TypedQuery<KscmtMonthPattern> query = em.createQuery(cq);

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
		return this.queryProxy().find(new KscmtMonthPatternPK(companyId, monthlyPatternCode),
				KscmtMonthPattern.class).map(c -> this.toDomain(c));

	}

	@Override
	public boolean exists(String companyId, String monthlyPatternCode) {
		Optional<KscmtMonthPattern> kscmtMonthPattern = this.queryProxy().find(new KscmtMonthPatternPK(companyId, monthlyPatternCode), KscmtMonthPattern.class);
		return kscmtMonthPattern.isPresent();
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
		this.commandProxy().remove(KscmtMonthPattern.class,
				new KscmtMonthPatternPK(companyId, monthlyPatternCode));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the monthly pattern
	 */
	private MonthlyPattern toDomain(KscmtMonthPattern entity){
		return new MonthlyPattern(new JpaMonthlyPatternGetMemento(entity));
	}
	
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kmpst month pattern
	 */
	private KscmtMonthPattern toEntity(MonthlyPattern domain){
		KscmtMonthPattern entity = new KscmtMonthPattern();
		domain.saveToMemento(new JpaMonthlyPatternSetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity update.
	 *
	 * @param domain the domain
	 * @return the kmpst month pattern
	 */
	private KscmtMonthPattern toEntityUpdate(MonthlyPattern domain){
		// find by id => optional data
		Optional<KscmtMonthPattern> optionalEntity = this.queryProxy()
				.find(new KscmtMonthPatternPK(domain.getCompanyId().v(),
						domain.getMonthlyPatternCode().v()), KscmtMonthPattern.class);
		KscmtMonthPattern entity = new KscmtMonthPattern();
		// check exist data find by id
		if(optionalEntity.isPresent()){
			entity = optionalEntity.get();
		}
		domain.saveToMemento(new JpaMonthlyPatternSetMemento(entity));
		return entity;
	}

	
}
