/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.monthly.setting;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.setting.KmpstMonthPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.setting.KmpstMonthPatternSet_;

/**
 * The Class JpaMonthlyPatternSettingRepository.
 */
@Stateless
public class JpaMonthlyPatternSettingRepository extends JpaRepository
		implements MonthlyPatternSettingRepository {
	
	/** The Constant FIRST_LENGTH. */
	public static final int FIRST_LENGTH = 1;
	
	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;
	
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
		this.commandProxy().update(this.toEntity(monthlyPatternSetting));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository#
	 * remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String employeeId) {
		this.commandProxy().remove(KmpstMonthPatternSet.class, employeeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository#
	 * findById(java.lang.String)
	 */
	@Override
	public Optional<MonthlyPatternSetting> findById(String employeeId) {
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
				.add(criteriaBuilder.equal(root.get(KmpstMonthPatternSet_.sid), employeeId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmpstMonthPatternSet> query = em.createQuery(cq).setMaxResults(FIRST_LENGTH);

		// exclude select
		List<KmpstMonthPatternSet> resData = query.getResultList();

		if (CollectionUtil.isEmpty(resData)) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(resData.get(FIRST_DATA)));
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingRepository#
	 * findAllByEmployeeIds(java.lang.String)
	 */
	@Override
	public List<MonthlyPatternSetting> findAllByEmployeeIds(List<String> employeeIds) {
		
		// check exist data input
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}
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
				.add(criteriaBuilder.and(root.get(KmpstMonthPatternSet_.sid).in(employeeIds)));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmpstMonthPatternSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());

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
