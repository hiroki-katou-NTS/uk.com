/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternSet_;

/**
 * The Class JpaMonthlyPatternSettingRepository.
 */
@Stateless
public class JpaMonthlyPatternSettingRepository extends JpaRepository
		implements MonthlyPatternSettingRepository {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository#
	 * add(nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetting)
	 */
	@Override
	public void add(MonthlyPatternSetting monthlyPatternSetting) {
		this.commandProxy().insert(this.toEntity(monthlyPatternSetting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository#
	 * update(nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetting)
	 */
	@Override
	public void update(MonthlyPatternSetting monthlyPatternSetting) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository#
	 * findById(java.lang.String)
	 */
	@Override
	public Optional<MonthlyPatternSetting> findById(String emmployeeId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMPST_MONTH_PATTERN_SET (KmpstMonthPatternSet SQL)
		CriteriaQuery<KmpstMonthPatternSet> cq = criteriaBuilder
				.createQuery(KmpstMonthPatternSet.class);

		// root data
		Root<KmpstMonthPatternSet> root = cq.from(KmpstMonthPatternSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KmpstMonthPatternSet_.kmpstMonthPatternSetPK)
						.get(KmpstMonthPatternSetPK_.sid), emmployeeId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmpstMonthPatternSet> query = em.createQuery(cq);

		// exclude select
		return Optional.ofNullable(this.toDomain(query.getSingleResult()));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the monthly pattern setting
	 */
	private MonthlyPatternSetting toDomain(KmpstMonthPatternSet entity){
		return new MonthlyPatternSetting(new JpaMonthlyPatternSettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kmpst month pattern set
	 */
	private KmpstMonthPatternSet toEntity(MonthlyPatternSetting domain){
		KmpstMonthPatternSet entity = new KmpstMonthPatternSet();
		domain.saveToMemento(new JpaMonthlyPatternSettingSetMemento(entity));
		return entity;
	}



}
