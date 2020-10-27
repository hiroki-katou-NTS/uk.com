/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishmentRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSyaPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSyaPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSya_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSyaPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSyaPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSya_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSyaPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSyaPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSya_;

/**
 * The Class JpaPersonalEstablishmentRepository.
 */
@Stateless
public class JpaPerEstablishmentRepository extends JpaRepository
		implements PersonalEstablishmentRepository {
	
	/** The default value. */
	private static final int DEFAULT_VALUE = 0;
	
	/** The total month of year. */
	private static final int TOTAL_MONTH_OF_YEAR = 12;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Personal.
	 * PersonalEstablishmentRepository#findById(java.lang.String, int)
	 */
	@Override
	public Optional<PersonalEstablishment> findById(String PersonalId, int targetYear) {

		// get by data base 
		List<KscmtEstTimeSya> estimateTimePersonals = this.getEstimateTimePersonal(PersonalId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimateTimePersonals)){
			estimateTimePersonals = this.getEstimateTimePersonalDefault(PersonalId, targetYear);
		}
		
		
		// get by data base 
		List<KscmtEstPriceSya> estimatePricePersonals = this.getEstimatePricePersonal(PersonalId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimatePricePersonals)){
			estimatePricePersonals = this.getEstimatePricePersonalDefault(PersonalId, targetYear);
		}
		
		// get by data base 
		List<KscmtEstDaysSya> estimateDaysPersonals = this.getEstimateDaysPersonal(PersonalId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimateDaysPersonals)){
			estimateDaysPersonals = this.getEstimateDaysPersonalDefault(PersonalId, targetYear);
		}
		
		return Optional.ofNullable(
				this.toDomain(estimateTimePersonals, estimatePricePersonals, estimateDaysPersonals));
	}

	/**
	 * Gets the estimate time Personal default.
	 *
	 * @param PersonalId the Personal id
	 * @param targetYear the target year
	 * @return the estimate time Personal default
	 */
	private List<KscmtEstTimeSya> getEstimateTimePersonalDefault(String PersonalId,
			int targetYear) {
		List<KscmtEstTimeSya> estimateTimePersonals = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimateTimePersonals.add(this.toEntityTimeDefault(PersonalId, targetYear, index));
		}
		return estimateTimePersonals;
	}
	
	/**
	 * Gets the estimate time personal.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 * @return the estimate time personal
	 */
	private List<KscmtEstTimeSya> getEstimateTimePersonal(String employeeId, int targetYear){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_Per_SET (KscmtEstTimeSya SQL)
		CriteriaQuery<KscmtEstTimeSya> cq = criteriaBuilder
				.createQuery(KscmtEstTimeSya.class);

		// root data
		Root<KscmtEstTimeSya> root = cq.from(KscmtEstTimeSya.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal Personal id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeSya_.kscmtEstTimeSyaPK).get(KscmtEstTimeSyaPK_.sid),
				employeeId));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeSya_.kscmtEstTimeSyaPK)
						.get(KscmtEstTimeSyaPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeSya> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	
	/**
	 * Gets the estimate price personal default.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 * @return the estimate price personal default
	 */
	private List<KscmtEstPriceSya> getEstimatePricePersonalDefault(String employeeId,
			int targetYear) {
		List<KscmtEstPriceSya> estimatePricePersonals = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimatePricePersonals.add(this.toEntityPriceDefault(employeeId, targetYear, index));
		}
		return estimatePricePersonals;
	}
	
	/**
	 * Gets the estimate price personal.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 * @return the estimate price personal
	 */
	private List<KscmtEstPriceSya> getEstimatePricePersonal(String employeeId, int targetYear) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_PRICE_Per_SET (KscmtEstPriceSya SQL)
		CriteriaQuery<KscmtEstPriceSya> cq = criteriaBuilder
				.createQuery(KscmtEstPriceSya.class);

		// root data
		Root<KscmtEstPriceSya> root = cq.from(KscmtEstPriceSya.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceSya_.kscmtEstPriceSyaPK)
						.get(KscmtEstPriceSyaPK_.sid), employeeId));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceSya_.kscmtEstPriceSyaPK)
						.get(KscmtEstPriceSyaPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstPriceSya> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	
	/**
	 * Gets the estimate days personal default.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 * @return the estimate days personal default
	 */
	private List<KscmtEstDaysSya> getEstimateDaysPersonalDefault(String employeeId,
			int targetYear) {
		List<KscmtEstDaysSya> estimateDaysPersonals = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimateDaysPersonals.add(this.toEntityDaysDefault(employeeId, targetYear, index));
		}
		return estimateDaysPersonals;
	}
	
	/**
	 * Gets the estimate days personal.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 * @return the estimate days personal
	 */
	private List<KscmtEstDaysSya> getEstimateDaysPersonal(String employeeId, int targetYear) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		// call KSCMT_EST_DAYS_Per_SET (KscmtEstDaysSya SQL)
		CriteriaQuery<KscmtEstDaysSya> cq = criteriaBuilder
				.createQuery(KscmtEstDaysSya.class);
		
		// root data
		Root<KscmtEstDaysSya> root = cq.from(KscmtEstDaysSya.class);
		
		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		// equal employee id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstDaysSya_.kscmtEstDaysSyaPK).get(KscmtEstDaysSyaPK_.sid),
				employeeId));
		
		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstDaysSya_.kscmtEstDaysSyaPK)
						.get(KscmtEstDaysSyaPK_.targetYear), targetYear));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// create query
		TypedQuery<KscmtEstDaysSya> query = em.createQuery(cq);
		
		// exclude select
		return query.getResultList();
	}
	
	/**
	 * To domain.
	 *
	 * @param estimateTimePersonals the estimate time Personals
	 * @param estimatePricePersonals the estimate price Personals
	 * @return the Personal establishment
	 */
	private PersonalEstablishment toDomain(List<KscmtEstTimeSya> estimateTimePersonals,
			List<KscmtEstPriceSya> estimatePricePersonals,
			List<KscmtEstDaysSya> estimateDaysPersonals) {
		return new PersonalEstablishment(new JpaPerEstablishmentGetMemento(estimateTimePersonals,
				estimatePricePersonals, estimateDaysPersonals));
	}
	
	/**
	 * To entity time default.
	 *
	 * @param PersonalId the Personal id
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est time Per set
	 */
	public KscmtEstTimeSya toEntityTimeDefault(String PersonalId, int targetYear, int targetCls) {
		KscmtEstTimeSya entity = new KscmtEstTimeSya();
		entity.setKscmtEstTimeSyaPK(new KscmtEstTimeSyaPK(PersonalId, targetYear, targetCls));
		entity.setEstCondition1stTime(DEFAULT_VALUE);
		entity.setEstCondition2ndTime(DEFAULT_VALUE);
		entity.setEstCondition3rdTime(DEFAULT_VALUE);
		entity.setEstCondition4thTime(DEFAULT_VALUE);
		entity.setEstCondition5thTime(DEFAULT_VALUE);
		return entity;
	}
	
	
	/**
	 * To entity price default.
	 *
	 * @param PersonalId the Personal id
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est price Per set
	 */
	public KscmtEstPriceSya toEntityPriceDefault(String PersonalId, int targetYear,
			int targetCls) {
		KscmtEstPriceSya entity = new KscmtEstPriceSya();
		entity.setKscmtEstPriceSyaPK(
				new KscmtEstPriceSyaPK(PersonalId, targetYear, targetCls));
		entity.setEstCondition1stMny(DEFAULT_VALUE);
		entity.setEstCondition2ndMny(DEFAULT_VALUE);
		entity.setEstCondition3rdMny(DEFAULT_VALUE);
		entity.setEstCondition4thMny(DEFAULT_VALUE);
		entity.setEstCondition5thMny(DEFAULT_VALUE);
		return entity;
	}
	
	/**
	 * To entity days default.
	 *
	 * @param employeeId the employee id
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est days per set
	 */
	public KscmtEstDaysSya toEntityDaysDefault(String employeeId, int targetYear,
			int targetCls) {
		KscmtEstDaysSya entity = new KscmtEstDaysSya();
		entity.setKscmtEstDaysSyaPK(new KscmtEstDaysSyaPK(employeeId, targetYear, targetCls));
		entity.setEstCondition1stDays(DEFAULT_VALUE);
		entity.setEstCondition2ndDays(DEFAULT_VALUE);
		entity.setEstCondition3rdDays(DEFAULT_VALUE);
		entity.setEstCondition4thDays(DEFAULT_VALUE);
		entity.setEstCondition5thDays(DEFAULT_VALUE);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Personal.
	 * PersonalEstablishmentRepository#savePersonalEstablishment(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.Personal.PersonalEstablishment)
	 */
	@Override
	public void savePersonalEstablishment(PersonalEstablishment personalEstablishment) {
		// find by id => optional data
		List<KscmtEstTimeSya> estimateTimePersonals = this.getEstimateTimePersonal(
				personalEstablishment.getEmployeeId(), personalEstablishment.getTargetYear().v());

		boolean isAddTime = false;
		boolean isAddPrice = false;
		boolean isAddDays = false;
		// check exist data
		if (CollectionUtil.isEmpty(estimateTimePersonals)) {
			estimateTimePersonals = this.getEstimateTimePersonalDefault(
					personalEstablishment.getEmployeeId(),
					personalEstablishment.getTargetYear().v());
			isAddTime = true;
		}

		// find by id => optional data
		List<KscmtEstPriceSya> estimatePricePersonals = this.getEstimatePricePersonal(
				personalEstablishment.getEmployeeId(), personalEstablishment.getTargetYear().v());

		// check exist data
		if (CollectionUtil.isEmpty(estimatePricePersonals)) {
			estimatePricePersonals = this.getEstimatePricePersonalDefault(
					personalEstablishment.getEmployeeId(),
					personalEstablishment.getTargetYear().v());
			isAddPrice = true;
		}
		// find by id => optional data
		List<KscmtEstDaysSya> estimateDaysPersonals = this.getEstimateDaysPersonal(
				personalEstablishment.getEmployeeId(), personalEstablishment.getTargetYear().v());

		// check exist data
		if (CollectionUtil.isEmpty(estimateDaysPersonals)) {
			estimateDaysPersonals = this.getEstimateDaysPersonalDefault(
					personalEstablishment.getEmployeeId(),
					personalEstablishment.getTargetYear().v());
			isAddDays = true;
		}

		personalEstablishment.getAdvancedSetting()
				.saveToMemento(new JpaPerEstDetailSetSetMemento(estimateTimePersonals,
						estimatePricePersonals, estimateDaysPersonals));
		if (isAddTime) {
			this.commandProxy().insertAll(estimateTimePersonals);
		} else {
			this.commandProxy().updateAll(estimateTimePersonals);
		}

		if (isAddPrice) {
			this.commandProxy().insertAll(estimatePricePersonals);
		} else {
			this.commandProxy().updateAll(estimatePricePersonals);
		}
		if (isAddDays) {
			this.commandProxy().insertAll(estimateDaysPersonals);
		} else {
			this.commandProxy().updateAll(estimateDaysPersonals);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.personal.
	 * PersonalEstablishmentRepository#removePersonalEstablishment(java.lang.
	 * String, int)
	 */
	@Override
	public void removePersonalEstablishment(String employeeId, int targetYear) {

		// find by time
		List<KscmtEstTimeSya> estimateTimePersonals = this.getEstimateTimePersonal(employeeId,
				targetYear);

		// find by price
		List<KscmtEstPriceSya> estimatePricePersonals = this.getEstimatePricePersonal(employeeId,
				targetYear);

		// find by number of day
		List<KscmtEstDaysSya> estimateDaysPersonals = this.getEstimateDaysPersonal(employeeId,
				targetYear);

		// remove all data
		this.commandProxy().removeAll(estimateTimePersonals);
		this.commandProxy().removeAll(estimatePricePersonals);
		this.commandProxy().removeAll(estimateDaysPersonals);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.personal.
	 * PersonalEstablishmentRepository#findAll(int)
	 */
	@Override
	public List<String> findAll(int targetYear) {

		// get all setting of company
		List<KscmtEstTimeSya> estimatePersonal = this.getEstimatePersonal(targetYear);

		Map<String, KscmtEstTimeSya> mapEstimatePersonal = new HashMap<>();
		estimatePersonal.forEach(estimate -> {
			if (!mapEstimatePersonal.containsKey(estimate.getKscmtEstTimeSyaPK().getSid())) {
				mapEstimatePersonal.put(estimate.getKscmtEstTimeSyaPK().getSid(), estimate);
			}
		});

		// to list data
		return mapEstimatePersonal.values().stream()
				.map(estimate -> estimate.getKscmtEstTimeSyaPK().getSid())
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Gets the estimate personal.
	 *
	 * @param targetYear the target year
	 * @return the estimate personal
	 */
	private List<KscmtEstTimeSya> getEstimatePersonal(int targetYear){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_Per_SET (KscmtEstTimeSya SQL)
		CriteriaQuery<KscmtEstTimeSya> cq = criteriaBuilder
				.createQuery(KscmtEstTimeSya.class);

		// root data
		Root<KscmtEstTimeSya> root = cq.from(KscmtEstTimeSya.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeSya_.kscmtEstTimeSyaPK)
						.get(KscmtEstTimeSyaPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeSya> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}

}
