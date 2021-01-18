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

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.setting.KscmtMonthPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.setting.KscmtMonthPatternSet_;

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
		this.commandProxy().remove(KscmtMonthPatternSet.class, employeeId);
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
		CriteriaQuery<KscmtMonthPatternSet> cq = criteriaBuilder
				.createQuery(KscmtMonthPatternSet.class);

		// root data
		Root<KscmtMonthPatternSet> root = cq.from(KscmtMonthPatternSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtMonthPatternSet_.sid), employeeId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtMonthPatternSet> query = em.createQuery(cq).setMaxResults(FIRST_LENGTH);

		// exclude select
		List<KscmtMonthPatternSet> resData = query.getResultList();

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
		CriteriaQuery<KscmtMonthPatternSet> cq = criteriaBuilder
				.createQuery(KscmtMonthPatternSet.class);

		// root data
		Root<KscmtMonthPatternSet> root = cq.from(KscmtMonthPatternSet.class);

		// select root
		cq.select(root);
		
		List<KscmtMonthPatternSet> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// Predicate where clause
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			// equal employee id
			lstpredicateWhere
					.add(criteriaBuilder.and(root.get(KscmtMonthPatternSet_.sid).in(splitData)));
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
			resultList.addAll(em.createQuery(cq).getResultList());
		});

		// exclude select
		return resultList.stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());

	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the monthly pattern setting
	 */
	private MonthlyPatternSetting toDomain(KscmtMonthPatternSet entity){
		return new MonthlyPatternSetting(new JpaMonthlyPatternSettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kmpst month pattern set
	 */
	private KscmtMonthPatternSet toEntity(MonthlyPatternSetting domain){
		Optional<KscmtMonthPatternSet> optional = this.queryProxy().find(domain.getEmployeeId(),
				KscmtMonthPatternSet.class);
		
		KscmtMonthPatternSet entity = new KscmtMonthPatternSet();
		if (optional.isPresent()) {
			entity = optional.get();
		}
		domain.saveToMemento(new JpaMonthlyPatternSettingSetMemento(entity));
		return entity;
	}


}
