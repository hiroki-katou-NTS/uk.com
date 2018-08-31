/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComSet_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComSet_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet_;

/**
 * The Class JpaCompanyEstablishmentRepository.
 */
@Stateless
public class JpaComEstablishmentRepository extends JpaRepository
		implements CompanyEstablishmentRepository {
	
	/** The default value. */
	private static final int DEFAULT_VALUE = 0;
	
	/** The total month of year. */
	private static final int TOTAL_MONTH_OF_YEAR = 12;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentRepository#findById(java.lang.String, int)
	 */
	@Override
	public Optional<CompanyEstablishment> findById(String companyId, int targetYear) {

		// get by data base 
		List<KscmtEstTimeComSet> estimateTimeCompanys = this.getEstimateTimeCompany(companyId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimateTimeCompanys)){
			estimateTimeCompanys = this.getEstimateTimeCompanyDefault(companyId, targetYear);
		}
		
		
		// get by data base 
		List<KscmtEstPriceComSet> estimatePriceCompanys = this.getEstimatePriceCompany(companyId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimatePriceCompanys)){
			estimatePriceCompanys = this.getEstimatePriceCompanyDefault(companyId, targetYear);
		}
		
		// get by data base 
		List<KscmtEstDaysComSet> estimateDaysCompanys = this.getEstimateDaysCompany(companyId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimateDaysCompanys)){
			estimateDaysCompanys = this.getEstimateDaysCompanyDefault(companyId, targetYear);
		}
		
		return Optional.ofNullable(
				this.toDomain(estimateTimeCompanys, estimatePriceCompanys, estimateDaysCompanys));
	}

	/**
	 * Gets the estimate time company default.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate time company default
	 */
	private List<KscmtEstTimeComSet> getEstimateTimeCompanyDefault(String companyId,
			int targetYear) {
		List<KscmtEstTimeComSet> estimateTimeCompanys = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimateTimeCompanys.add(this.toEntityTimeDefault(companyId, targetYear, index));
		}
		return estimateTimeCompanys;
	}
	
	/**
	 * Gets the estimate time company.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate time company
	 */
	private List<KscmtEstTimeComSet> getEstimateTimeCompany(String companyId, int targetYear){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_COM_SET (KscmtEstTimeComSet SQL)
		CriteriaQuery<KscmtEstTimeComSet> cq = criteriaBuilder
				.createQuery(KscmtEstTimeComSet.class);

		// root data
		Root<KscmtEstTimeComSet> root = cq.from(KscmtEstTimeComSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeComSet_.kscmtEstTimeComSetPK).get(KscmtEstTimeComSetPK_.cid),
				companyId));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeComSet_.kscmtEstTimeComSetPK)
						.get(KscmtEstTimeComSetPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeComSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	
	/**
	 * Gets the estimate price company default.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate price company default
	 */
	private List<KscmtEstPriceComSet> getEstimatePriceCompanyDefault(String companyId,
			int targetYear) {
		List<KscmtEstPriceComSet> estimatePriceCompanys = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimatePriceCompanys.add(this.toEntityPriceDefault(companyId, targetYear, index));
		}
		return estimatePriceCompanys;
	}
	
	/**
	 * Gets the estimate price company.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate price company
	 */
	private List<KscmtEstPriceComSet> getEstimatePriceCompany(String companyId, int targetYear) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_PRICE_COM_SET (KscmtEstPriceComSet SQL)
		CriteriaQuery<KscmtEstPriceComSet> cq = criteriaBuilder
				.createQuery(KscmtEstPriceComSet.class);

		// root data
		Root<KscmtEstPriceComSet> root = cq.from(KscmtEstPriceComSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceComSet_.kscmtEstPriceComSetPK)
						.get(KscmtEstPriceComSetPK_.cid), companyId));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceComSet_.kscmtEstPriceComSetPK)
						.get(KscmtEstPriceComSetPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstPriceComSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	
	/**
	 * Gets the estimate days company default.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate days company default
	 */
	private List<KscmtEstDaysComSet> getEstimateDaysCompanyDefault(String companyId,
			int targetYear) {
		List<KscmtEstDaysComSet> estimateDaysCompanys = new ArrayList<>();
		for (int index = DEFAULT_VALUE; index <= TOTAL_MONTH_OF_YEAR; index++) {
			estimateDaysCompanys.add(this.toEntityDaysDefault(companyId, targetYear, index));
		}
		return estimateDaysCompanys;
	}
	
	/**
	 * Gets the estimate days company.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate days company
	 */
	private List<KscmtEstDaysComSet> getEstimateDaysCompany(String companyId, int targetYear) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		// call KSCMT_EST_DAYS_COM_SET (KscmtEstDaysComSet SQL)
		CriteriaQuery<KscmtEstDaysComSet> cq = criteriaBuilder
				.createQuery(KscmtEstDaysComSet.class);
		
		// root data
		Root<KscmtEstDaysComSet> root = cq.from(KscmtEstDaysComSet.class);
		
		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstDaysComSet_.kscmtEstDaysComSetPK).get(KscmtEstDaysComSetPK_.cid),
				companyId));
		
		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstDaysComSet_.kscmtEstDaysComSetPK)
						.get(KscmtEstDaysComSetPK_.targetYear), targetYear));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// create query
		TypedQuery<KscmtEstDaysComSet> query = em.createQuery(cq);
		
		// exclude select
		return query.getResultList();
	}
	
	/**
	 * To domain.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 * @param estimatePriceCompanys the estimate price companys
	 * @return the company establishment
	 */
	private CompanyEstablishment toDomain(List<KscmtEstTimeComSet> estimateTimeCompanys,
			List<KscmtEstPriceComSet> estimatePriceCompanys,
			List<KscmtEstDaysComSet> estimateDaysCompanys) {
		return new CompanyEstablishment(new JpaComEstablishmentGetMemento(estimateTimeCompanys,
				estimatePriceCompanys, estimateDaysCompanys));
	}
	
	/**
	 * To entity time default.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est time com set
	 */
	public KscmtEstTimeComSet toEntityTimeDefault(String companyId, int targetYear, int targetCls) {
		KscmtEstTimeComSet entity = new KscmtEstTimeComSet();
		entity.setKscmtEstTimeComSetPK(new KscmtEstTimeComSetPK(companyId, targetYear, targetCls));
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
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est price com set
	 */
	public KscmtEstPriceComSet toEntityPriceDefault(String companyId, int targetYear,
			int targetCls) {
		KscmtEstPriceComSet entity = new KscmtEstPriceComSet();
		entity.setKscmtEstPriceComSetPK(
				new KscmtEstPriceComSetPK(companyId, targetYear, targetCls));
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
	 * @param targetYear the target year
	 * @param targetCls the target cls
	 * @return the kscmt est days com set
	 */
	public KscmtEstDaysComSet toEntityDaysDefault(String companyId, int targetYear,
			int targetCls) {
		KscmtEstDaysComSet entity = new KscmtEstDaysComSet();
		entity.setKscmtEstDaysComSetPK(new KscmtEstDaysComSetPK(companyId, targetYear, targetCls));
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
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentRepository#saveCompanyEstablishment(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.company.CompanyEstablishment)
	 */
	@Override
	public void saveCompanyEstablishment(CompanyEstablishment companyEstablishment) {
		// find by id => optional data
		List<KscmtEstTimeComSet> estimateTimeCompanys = this.getEstimateTimeCompany(
				companyEstablishment.getCompanyId().v(), companyEstablishment.getTargetYear().v());

		boolean isAddTime = false;
		boolean isAddPrice = false;
		boolean isAddDays = false;
		// check exist data
		if (CollectionUtil.isEmpty(estimateTimeCompanys)) {
			estimateTimeCompanys = this.getEstimateTimeCompanyDefault(
					companyEstablishment.getCompanyId().v(),
					companyEstablishment.getTargetYear().v());
			isAddTime = true;
		}

		// find by id => optional data
		List<KscmtEstPriceComSet> estimatePriceCompanys = this.getEstimatePriceCompany(
				companyEstablishment.getCompanyId().v(), companyEstablishment.getTargetYear().v());

		// check exist data
		if (CollectionUtil.isEmpty(estimatePriceCompanys)) {
			estimatePriceCompanys = this.getEstimatePriceCompanyDefault(
					companyEstablishment.getCompanyId().v(),
					companyEstablishment.getTargetYear().v());
			isAddPrice = true;
		}
		// find by id => optional data
		List<KscmtEstDaysComSet> estimateDaysCompanys = this.getEstimateDaysCompany(
				companyEstablishment.getCompanyId().v(), companyEstablishment.getTargetYear().v());

		// check exist data
		if (CollectionUtil.isEmpty(estimateDaysCompanys)) {
			estimateDaysCompanys = this.getEstimateDaysCompanyDefault(
					companyEstablishment.getCompanyId().v(),
					companyEstablishment.getTargetYear().v());
			isAddDays = true;
		}

		companyEstablishment.getAdvancedSetting()
				.saveToMemento(new JpaComEstDetailSetSetMemento(estimateTimeCompanys,
						estimatePriceCompanys, estimateDaysCompanys));
		if(isAddTime){
			this.commandProxy().insertAll(estimateTimeCompanys);
		}
		else {
			this.commandProxy().updateAll(estimateTimeCompanys);
		}
		
		if(isAddPrice){
			this.commandProxy().insertAll(estimatePriceCompanys);
		}
		else {
			this.commandProxy().updateAll(estimatePriceCompanys);
		}
		if(isAddDays){
			this.commandProxy().insertAll(estimateDaysCompanys);
		}
		else {
			this.commandProxy().updateAll(estimateDaysCompanys);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentRepository#removeCompanyEstablishment(java.lang.
	 * String, int)
	 */
	@Override
	public void removeCompanyEstablishment(String companyId, int targetYear) {
		
		// find by time
		List<KscmtEstTimeComSet> estimateTimeCompanys = this.getEstimateTimeCompany(companyId,
				targetYear);
		
		// find by price
		List<KscmtEstPriceComSet> estimatePriceCompanys = this.getEstimatePriceCompany(companyId,
				targetYear);
		
		// find by number of day
		List<KscmtEstDaysComSet> estimateDaysCompanys = this.getEstimateDaysCompany(companyId,
				targetYear);
		
		// remove all data
		this.commandProxy().removeAll(estimateTimeCompanys);
		this.commandProxy().removeAll(estimatePriceCompanys);
		this.commandProxy().removeAll(estimateDaysCompanys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentRepository#checkSetting(java.lang.String, int)
	 */
	@Override
	public boolean checkSetting(String companyId, int targetYear) {
		return !(CollectionUtil.isEmpty(this.getEstimateTimeCompany(companyId, targetYear)));
	}

}
