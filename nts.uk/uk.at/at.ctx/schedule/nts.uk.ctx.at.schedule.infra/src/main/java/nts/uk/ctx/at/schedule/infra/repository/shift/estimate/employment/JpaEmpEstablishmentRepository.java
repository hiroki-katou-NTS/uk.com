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
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmp_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmpPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmpPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmp_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmpPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmpPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmp_;

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
		List<KscmtEstTimeEmp> estimateTimeEmployments = this.getEstimateTimeEmployment(companyId,
				employmentCode, targetYear);

		// check exist data
		if (CollectionUtil.isEmpty(estimateTimeEmployments)) {
			estimateTimeEmployments = this.getEstimateTimeEmploymentDefault(companyId,
					employmentCode, targetYear);
		}

		// get by data base
		List<KscmtEstPriceEmp> estimatePriceEmployments = this
				.getEstimatePriceEmployment(companyId, employmentCode, targetYear);

		// check exist data
		if (CollectionUtil.isEmpty(estimatePriceEmployments)) {
			estimatePriceEmployments = this.getEstimatePriceEmploymentDefault(companyId,
					employmentCode, targetYear);
		}

		// get by data base
		List<KscmtEstDaysEmp> estimateDaysEmployments = this.getEstimateDaysEmployment(companyId,
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
	private List<KscmtEstTimeEmp> getEstimateTimeEmploymentDefault(String companyId,
			String employmentCode, int targetYear) {
		List<KscmtEstTimeEmp> estimateTimeEmployments = new ArrayList<>();
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
	private List<KscmtEstTimeEmp> getEstimateTimeEmployment(String companyId,
			String emmploymentCode, int targetYear) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_COM (KscmtEstTimeEmp SQL)
		CriteriaQuery<KscmtEstTimeEmp> cq = criteriaBuilder
				.createQuery(KscmtEstTimeEmp.class);

		// root data
		Root<KscmtEstTimeEmp> root = cq.from(KscmtEstTimeEmp.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeEmp_.kscmtEstTimeEmpPK).get(KscmtEstTimeEmpPK_.cid),
				companyId));
		
		// equal employment code
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeEmp_.kscmtEstTimeEmpPK).get(KscmtEstTimeEmpPK_.empcd),
				emmploymentCode));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeEmp_.kscmtEstTimeEmpPK)
						.get(KscmtEstTimeEmpPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeEmp> query = em.createQuery(cq);

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
	private List<KscmtEstPriceEmp> getEstimatePriceEmploymentDefault(String companyId,
			String employementCode, int targetYear) {
		List<KscmtEstPriceEmp> estimatePriceEmployments = new ArrayList<>();
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
	private List<KscmtEstPriceEmp> getEstimatePriceEmployment(String companyId,
			String employmentCode, int targetYear) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_PRICE_COM (KscmtEstPriceEmp SQL)
		CriteriaQuery<KscmtEstPriceEmp> cq = criteriaBuilder
				.createQuery(KscmtEstPriceEmp.class);

		// root data
		Root<KscmtEstPriceEmp> root = cq.from(KscmtEstPriceEmp.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceEmp_.kscmtEstPriceEmpPK)
						.get(KscmtEstPriceEmpPK_.cid), companyId));
		
		// equal employment code
		lstpredicateWhere
		.add(criteriaBuilder.equal(root.get(KscmtEstPriceEmp_.kscmtEstPriceEmpPK)
				.get(KscmtEstPriceEmpPK_.empcd), employmentCode));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceEmp_.kscmtEstPriceEmpPK)
						.get(KscmtEstPriceEmpPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstPriceEmp> query = em.createQuery(cq);

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
	private List<KscmtEstDaysEmp> getEstimateDaysEmploymentDefault(String companyId,
			String employmentCode, int targetYear) {
		List<KscmtEstDaysEmp> estimateDaysEmployments = new ArrayList<>();
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
	private List<KscmtEstDaysEmp> getEstimateDaysEmployment(String companyId, String employmentCode, int targetYear) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		// call KSCMT_EST_DAYS_COM (KscmtEstDaysEmp SQL)
		CriteriaQuery<KscmtEstDaysEmp> cq = criteriaBuilder
				.createQuery(KscmtEstDaysEmp.class);
		
		// root data
		Root<KscmtEstDaysEmp> root = cq.from(KscmtEstDaysEmp.class);
		
		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstDaysEmp_.kscmtEstDaysEmpPK).get(KscmtEstDaysEmpPK_.cid),
				companyId));
		
		// equal employment code
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstDaysEmp_.kscmtEstDaysEmpPK).get(KscmtEstDaysEmpPK_.empcd),
				employmentCode));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstDaysEmp_.kscmtEstDaysEmpPK)
						.get(KscmtEstDaysEmpPK_.targetYear), targetYear));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// create query
		TypedQuery<KscmtEstDaysEmp> query = em.createQuery(cq);
		
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
	private EmploymentEstablishment toDomain(List<KscmtEstTimeEmp> estimateTimeEmployments,
			List<KscmtEstPriceEmp> estimatePriceEmployments,
			List<KscmtEstDaysEmp> estimateDaysEmployments) {
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
	public KscmtEstTimeEmp toEntityTimeDefault(String companyId, String employmentCode,
			int targetYear, int targetCls) {
		KscmtEstTimeEmp entity = new KscmtEstTimeEmp();
		entity.setKscmtEstTimeEmpPK(
				new KscmtEstTimeEmpPK(companyId, employmentCode, targetYear, targetCls));
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
	public KscmtEstPriceEmp toEntityPriceDefault(String companyId, String employmentCode,
			int targetYear, int targetCls) {
		KscmtEstPriceEmp entity = new KscmtEstPriceEmp();
		entity.setKscmtEstPriceEmpPK(
				new KscmtEstPriceEmpPK(companyId, employmentCode, targetYear, targetCls));
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
	public KscmtEstDaysEmp toEntityDaysDefault(String companyId, String employmentCode,
			int targetYear, int targetCls) {
		KscmtEstDaysEmp entity = new KscmtEstDaysEmp();
		entity.setKscmtEstDaysEmpPK(
				new KscmtEstDaysEmpPK(companyId, employmentCode, targetYear, targetCls));
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
		List<KscmtEstTimeEmp> estimateTimeEmployments = this.getEstimateTimeEmployment(
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
		List<KscmtEstPriceEmp> estimatePriceEmployments = this.getEstimatePriceEmployment(
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
		List<KscmtEstDaysEmp> estimateDaysEmployments = this.getEstimateDaysEmployment(
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
		List<KscmtEstTimeEmp> estimateTimeEmployments = this.getEstimateTimeEmployment(companyId,
				employmentCode, targetYear);

		// find by price
		List<KscmtEstPriceEmp> estimatePriceEmployments = this
				.getEstimatePriceEmployment(companyId, employmentCode, targetYear);

		// find by number of day
		List<KscmtEstDaysEmp> estimateDaysEmployments = this.getEstimateDaysEmployment(companyId,
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
	private List<KscmtEstTimeEmp> getEstimateTime(String companyId, int targetYear) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_COM (KscmtEstTimeEmp SQL)
		CriteriaQuery<KscmtEstTimeEmp> cq = criteriaBuilder
				.createQuery(KscmtEstTimeEmp.class);

		// root data
		Root<KscmtEstTimeEmp> root = cq.from(KscmtEstTimeEmp.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeEmp_.kscmtEstTimeEmpPK).get(KscmtEstTimeEmpPK_.cid),
				companyId));
		
		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeEmp_.kscmtEstTimeEmpPK)
						.get(KscmtEstTimeEmpPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeEmp> query = em.createQuery(cq);

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
		List<KscmtEstTimeEmp> estimateTime = this.getEstimateTime(companyId, targetYear);
		Map<String, KscmtEstTimeEmp> mapEstimateTime = new HashMap<>();
		estimateTime.forEach(estimate -> {
			if (!mapEstimateTime.containsKey(estimate.getKscmtEstTimeEmpPK().getEmpcd())) {
				mapEstimateTime.put(estimate.getKscmtEstTimeEmpPK().getEmpcd(), estimate);
			}
		});

		return mapEstimateTime.values().stream()
				.map(estimate -> estimate.getKscmtEstTimeEmpPK().getEmpcd())
				.collect(Collectors.toList());
	}


}
