/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

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
import nts.uk.ctx.at.schedule.dom.shift.estimate.employment.EmploymentEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.employment.EmploymentEstablishmentRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpSet_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmpSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmpSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmpSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmpSet_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmpSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmpSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmpSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmpSet_;

/**
 * The Class JpaEmploymentEstablishmentRepository.
 */
@Stateless
public class JpaEmpEstablishmentRepository extends JpaRepository
		implements EmploymentEstablishmentRepository {
	
	/** The default value. */
	private static final int DEFAULT_VALUE = 0;
	
	/** The total month of year. */
	private static final int TOTAL_MONTH_OF_YEAR = 12;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.employment.
	 * EmploymentEstablishmentRepository#findById(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Optional<EmploymentEstablishment> findById(String companyId, String employmentCode,
			int targetYear) {

		// get by data base
		List<KscmtEstTimeEmpSet> estimateTimeEmployments = this.getEstimateTimeEmployment(companyId,
				employmentCode, targetYear);

		// check exist data
		if (CollectionUtil.isEmpty(estimateTimeEmployments)) {
			estimateTimeEmployments = this.getEstimateTimeEmploymentDefault(companyId,
					employmentCode, targetYear);
		}

		// get by data base
		List<KscmtEstPriceEmpSet> estimatePriceEmployments = this
				.getEstimatePriceEmployment(companyId, employmentCode, targetYear);

		// check exist data
		if (CollectionUtil.isEmpty(estimatePriceEmployments)) {
			estimatePriceEmployments = this.getEstimatePriceEmploymentDefault(companyId,
					employmentCode, targetYear);
		}

		// get by data base
		List<KscmtEstDaysEmpSet> estimateDaysEmployments = this.getEstimateDaysEmployment(companyId,
				employmentCode, targetYear);

		// check exist data
		if (CollectionUtil.isEmpty(estimateDaysEmployments)) {
			estimateDaysEmployments = this.getEstimateDaysEmploymentDefault(companyId,
					employmentCode, targetYear);
		}

		return Optional.ofNullable(this.toDomain(estimateTimeEmployments, estimatePriceEmployments,
				estimateDaysEmployments));
	}

	/**
	 * Gets the estimate time employment default.
	 *
	 * @param companyId the company id
	 * @param employmentCode the employment code
	 * @param targetYear the target year
	 * @return the estimate time employment default
	 */
	private List<KscmtEstTimeEmpSet> getEstimateTimeEmploymentDefault(String companyId,
			String employmentCode, int targetYear) {
		List<KscmtEstTimeEmpSet> estimateTimeEmployments = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimateTimeEmployments
					.add(this.toEntityTimeDefault(companyId, employmentCode, targetYear, index));
		}
		return estimateTimeEmployments;
	}
	
	/**
	 * Gets the estimate time employment.
	 *
	 * @param companyId the company id
	 * @param emmploymentCode the emmployment code
	 * @param targetYear the target year
	 * @return the estimate time employment
	 */
	private List<KscmtEstTimeEmpSet> getEstimateTimeEmployment(String companyId,
			String emmploymentCode, int targetYear) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_COM_SET (KscmtEstTimeEmpSet SQL)
		CriteriaQuery<KscmtEstTimeEmpSet> cq = criteriaBuilder
				.createQuery(KscmtEstTimeEmpSet.class);

		// root data
		Root<KscmtEstTimeEmpSet> root = cq.from(KscmtEstTimeEmpSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeEmpSet_.kscmtEstTimeEmpSetPK).get(KscmtEstTimeEmpSetPK_.cid),
				companyId));
		
		// equal employment code
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeEmpSet_.kscmtEstTimeEmpSetPK).get(KscmtEstTimeEmpSetPK_.empcd),
				emmploymentCode));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeEmpSet_.kscmtEstTimeEmpSetPK)
						.get(KscmtEstTimeEmpSetPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeEmpSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	
	/**
	 * Gets the estimate price Employment default.
	 *
	 * @param employementCode the Employment id
	 * @param targetYear the target year
	 * @return the estimate price Employment default
	 */
	private List<KscmtEstPriceEmpSet> getEstimatePriceEmploymentDefault(String companyId,
			String employementCode, int targetYear) {
		List<KscmtEstPriceEmpSet> estimatePriceEmployments = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimatePriceEmployments
					.add(this.toEntityPriceDefault(companyId, employementCode, targetYear, index));
		}
		return estimatePriceEmployments;
	}
	
	/**
	 * Gets the estimate price Employment.
	 *
	 * @param employmentCode the Employment id
	 * @param targetYear the target year
	 * @return the estimate price Employment
	 */
	private List<KscmtEstPriceEmpSet> getEstimatePriceEmployment(String companyId,
			String employmentCode, int targetYear) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_PRICE_COM_SET (KscmtEstPriceEmpSet SQL)
		CriteriaQuery<KscmtEstPriceEmpSet> cq = criteriaBuilder
				.createQuery(KscmtEstPriceEmpSet.class);

		// root data
		Root<KscmtEstPriceEmpSet> root = cq.from(KscmtEstPriceEmpSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceEmpSet_.kscmtEstPriceEmpSetPK)
						.get(KscmtEstPriceEmpSetPK_.cid), companyId));
		
		// equal employment code
		lstpredicateWhere
		.add(criteriaBuilder.equal(root.get(KscmtEstPriceEmpSet_.kscmtEstPriceEmpSetPK)
				.get(KscmtEstPriceEmpSetPK_.empcd), employmentCode));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceEmpSet_.kscmtEstPriceEmpSetPK)
						.get(KscmtEstPriceEmpSetPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstPriceEmpSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	
	/**
	 * Gets the estimate days Employment default.
	 *
	 * @param employmentCode the Employment id
	 * @param targetYear the target year
	 * @return the estimate days Employment default
	 */
	private List<KscmtEstDaysEmpSet> getEstimateDaysEmploymentDefault(String companyId,
			String employmentCode, int targetYear) {
		List<KscmtEstDaysEmpSet> estimateDaysEmployments = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimateDaysEmployments
					.add(this.toEntityDaysDefault(companyId, employmentCode, targetYear, index));
		}
		return estimateDaysEmployments;
	}

	/**
	 * Gets the estimate days Employment.
	 *
	 * @param employmentCode the Employment id
	 * @param targetYear the target year
	 * @return the estimate days Employment
	 */
	private List<KscmtEstDaysEmpSet> getEstimateDaysEmployment(String companyId, String employmentCode, int targetYear) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		// call KSCMT_EST_DAYS_COM_SET (KscmtEstDaysEmpSet SQL)
		CriteriaQuery<KscmtEstDaysEmpSet> cq = criteriaBuilder
				.createQuery(KscmtEstDaysEmpSet.class);
		
		// root data
		Root<KscmtEstDaysEmpSet> root = cq.from(KscmtEstDaysEmpSet.class);
		
		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstDaysEmpSet_.kscmtEstDaysEmpSetPK).get(KscmtEstDaysEmpSetPK_.cid),
				companyId));
		
		// equal employment code
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstDaysEmpSet_.kscmtEstDaysEmpSetPK).get(KscmtEstDaysEmpSetPK_.empcd),
				employmentCode));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstDaysEmpSet_.kscmtEstDaysEmpSetPK)
						.get(KscmtEstDaysEmpSetPK_.targetYear), targetYear));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// create query
		TypedQuery<KscmtEstDaysEmpSet> query = em.createQuery(cq);
		
		// exclude select
		return query.getResultList();
	}
	
	/**
	 * To domain.
	 *
	 * @param estimateTimeEmployments the estimate time Employments
	 * @param estimatePriceEmployments the estimate price Employments
	 * @return the Employment establishment
	 */
	private EmploymentEstablishment toDomain(List<KscmtEstTimeEmpSet> estimateTimeEmployments,
			List<KscmtEstPriceEmpSet> estimatePriceEmployments,
			List<KscmtEstDaysEmpSet> estimateDaysEmployments) {
		return new EmploymentEstablishment(new JpaEmpEstablishmentGetMemento(estimateTimeEmployments,
				estimatePriceEmployments, estimateDaysEmployments));
	}
	
	/**
	 * To entity time default.
	 *
	 * @param companyId the company id
	 * @param employmentCode the employment code
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est time emp set
	 */
	public KscmtEstTimeEmpSet toEntityTimeDefault(String companyId, String employmentCode,
			int targetYear, int targetCls) {
		KscmtEstTimeEmpSet entity = new KscmtEstTimeEmpSet();
		entity.setKscmtEstTimeEmpSetPK(
				new KscmtEstTimeEmpSetPK(companyId, employmentCode, targetYear, targetCls));
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
	 * @param companyId the company id
	 * @param employmentCode the employment code
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est price emp set
	 */
	public KscmtEstPriceEmpSet toEntityPriceDefault(String companyId, String employmentCode,
			int targetYear, int targetCls) {
		KscmtEstPriceEmpSet entity = new KscmtEstPriceEmpSet();
		entity.setKscmtEstPriceEmpSetPK(
				new KscmtEstPriceEmpSetPK(companyId, employmentCode, targetYear, targetCls));
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
	 * @param companyId the company id
	 * @param employmentCode the employment code
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est days emp set
	 */
	public KscmtEstDaysEmpSet toEntityDaysDefault(String companyId, String employmentCode,
			int targetYear, int targetCls) {
		KscmtEstDaysEmpSet entity = new KscmtEstDaysEmpSet();
		entity.setKscmtEstDaysEmpSetPK(
				new KscmtEstDaysEmpSetPK(companyId, employmentCode, targetYear, targetCls));
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
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Employment.
	 * EmploymentEstablishmentRepository#saveEmploymentEstablishment(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.Employment.EmploymentEstablishment)
	 */
	@Override
	public void saveEmploymentEstablishment(EmploymentEstablishment employmentEstablishment) {
		// find by id => optional data
		List<KscmtEstTimeEmpSet> estimateTimeEmployments = this.getEstimateTimeEmployment(
				employmentEstablishment.getCompanyId().v(),
				employmentEstablishment.getEmploymentCode().v(),
				employmentEstablishment.getTargetYear().v());

		boolean isAddTime = false;
		boolean isAddPrice = false;
		boolean isAddDays = false;
		// check exist data
		if (CollectionUtil.isEmpty(estimateTimeEmployments)) {
			estimateTimeEmployments = this.getEstimateTimeEmploymentDefault(
					employmentEstablishment.getCompanyId().v(),
					employmentEstablishment.getEmploymentCode().v(),
					employmentEstablishment.getTargetYear().v());
			isAddTime = true;
		}

		// find by id => optional data
		List<KscmtEstPriceEmpSet> estimatePriceEmployments = this.getEstimatePriceEmployment(
				employmentEstablishment.getCompanyId().v(),
				employmentEstablishment.getEmploymentCode().v(), employmentEstablishment.getTargetYear().v());

		// check exist data
		if (CollectionUtil.isEmpty(estimatePriceEmployments)) {
			estimatePriceEmployments = this.getEstimatePriceEmploymentDefault(
					employmentEstablishment.getCompanyId().v(),
					employmentEstablishment.getEmploymentCode().v(),
					employmentEstablishment.getTargetYear().v());
			isAddPrice = true;
		}
		// find by id => optional data
		List<KscmtEstDaysEmpSet> estimateDaysEmployments = this.getEstimateDaysEmployment(
				employmentEstablishment.getCompanyId().v(),
				employmentEstablishment.getEmploymentCode().v(), employmentEstablishment.getTargetYear().v());

		// check exist data
		if (CollectionUtil.isEmpty(estimateDaysEmployments)) {
			estimateDaysEmployments = this.getEstimateDaysEmploymentDefault(
					employmentEstablishment.getCompanyId().v(),
					employmentEstablishment.getEmploymentCode().v(),
					employmentEstablishment.getTargetYear().v());
			isAddDays = true;
		}

		employmentEstablishment.getAdvancedSetting()
				.saveToMemento(new JpaEmpEstDetailSetSetMemento(estimateTimeEmployments,
						estimatePriceEmployments, estimateDaysEmployments));
		if(isAddTime){
			this.commandProxy().insertAll(estimateTimeEmployments);
		}
		else {
			this.commandProxy().updateAll(estimateTimeEmployments);
		}
		
		if(isAddPrice){
			this.commandProxy().insertAll(estimatePriceEmployments);
		}
		else {
			this.commandProxy().updateAll(estimatePriceEmployments);
		}
		if(isAddDays){
			this.commandProxy().insertAll(estimateDaysEmployments);
		}
		else {
			this.commandProxy().updateAll(estimateDaysEmployments);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.employment.
	 * EmploymentEstablishmentRepository#removeEmploymentEstablishment(java.lang
	 * .String, java.lang.String, int)
	 */
	@Override
	public void removeEmploymentEstablishment(String companyId, String employmentCode,
			int targetYear) {
		// find by time
		List<KscmtEstTimeEmpSet> estimateTimeEmployments = this.getEstimateTimeEmployment(companyId,
				employmentCode, targetYear);

		// find by price
		List<KscmtEstPriceEmpSet> estimatePriceEmployments = this
				.getEstimatePriceEmployment(companyId, employmentCode, targetYear);

		// find by number of day
		List<KscmtEstDaysEmpSet> estimateDaysEmployments = this.getEstimateDaysEmployment(companyId,
				employmentCode, targetYear);

		// remove all data
		this.commandProxy().removeAll(estimateTimeEmployments);
		this.commandProxy().removeAll(estimatePriceEmployments);
		this.commandProxy().removeAll(estimateDaysEmployments);
		
	}	

	/**
	 * Gets the estimate time.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate time
	 */
	private List<KscmtEstTimeEmpSet> getEstimateTime(String companyId, int targetYear) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_COM_SET (KscmtEstTimeEmpSet SQL)
		CriteriaQuery<KscmtEstTimeEmpSet> cq = criteriaBuilder
				.createQuery(KscmtEstTimeEmpSet.class);

		// root data
		Root<KscmtEstTimeEmpSet> root = cq.from(KscmtEstTimeEmpSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeEmpSet_.kscmtEstTimeEmpSetPK).get(KscmtEstTimeEmpSetPK_.cid),
				companyId));
		
		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeEmpSet_.kscmtEstTimeEmpSetPK)
						.get(KscmtEstTimeEmpSetPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeEmpSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.employment.
	 * EmploymentEstablishmentRepository#findAllEmploymentSetting(java.lang.
	 * String, int)
	 */
	@Override
	public List<String> findAllEmploymentSetting(String companyId, int targetYear) {
		List<KscmtEstTimeEmpSet> estimateTime = this.getEstimateTime(companyId, targetYear);
		Map<String, KscmtEstTimeEmpSet> mapEstimateTime = new HashMap<>();
		estimateTime.forEach(estimate -> {
			if (!mapEstimateTime.containsKey(estimate.getKscmtEstTimeEmpSetPK().getEmpcd())) {
				mapEstimateTime.put(estimate.getKscmtEstTimeEmpSetPK().getEmpcd(), estimate);
			}
		});

		return mapEstimateTime.values().stream()
				.map(estimate -> estimate.getKscmtEstTimeEmpSetPK().getEmpcd())
				.collect(Collectors.toList());
	}


}
