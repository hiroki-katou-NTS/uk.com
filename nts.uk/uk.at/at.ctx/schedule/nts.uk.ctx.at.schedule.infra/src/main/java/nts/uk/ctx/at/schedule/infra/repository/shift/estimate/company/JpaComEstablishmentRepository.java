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
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysCom_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceCom_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeCom_;

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
		List<KscmtEstTimeCom> estimateTimeCompanys = this.getEstimateTimeCompany(companyId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimateTimeCompanys)){
			estimateTimeCompanys = this.getEstimateTimeCompanyDefault(companyId, targetYear);
		}
		
		
		// get by data base 
		List<KscmtEstPriceCom> estimatePriceCompanys = this.getEstimatePriceCompany(companyId,
				targetYear);
		
		// check exist data
		if(CollectionUtil.isEmpty(estimatePriceCompanys)){
			estimatePriceCompanys = this.getEstimatePriceCompanyDefault(companyId, targetYear);
		}
		
		// get by data base 
		List<KscmtEstDaysCom> estimateDaysCompanys = this.getEstimateDaysCompany(companyId,
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
	private List<KscmtEstTimeCom> getEstimateTimeCompanyDefault(String companyId,
			int targetYear) {
		List<KscmtEstTimeCom> estimateTimeCompanys = new ArrayList<>();
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
	private List<KscmtEstTimeCom> getEstimateTimeCompany(String companyId, int targetYear){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_COM (KscmtEstTimeCom SQL)
		CriteriaQuery<KscmtEstTimeCom> cq = criteriaBuilder
				.createQuery(KscmtEstTimeCom.class);

		// root data
		Root<KscmtEstTimeCom> root = cq.from(KscmtEstTimeCom.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstTimeCom_.kscmtEstTimeComPK).get(KscmtEstTimeComPK_.cid),
				companyId));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeCom_.kscmtEstTimeComPK)
						.get(KscmtEstTimeComPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeCom> query = em.createQuery(cq);

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
	private List<KscmtEstPriceCom> getEstimatePriceCompanyDefault(String companyId,
			int targetYear) {
		List<KscmtEstPriceCom> estimatePriceCompanys = new ArrayList<>();
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
	private List<KscmtEstPriceCom> getEstimatePriceCompany(String companyId, int targetYear) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_PRICE_COM (KscmtEstPriceCom SQL)
		CriteriaQuery<KscmtEstPriceCom> cq = criteriaBuilder
				.createQuery(KscmtEstPriceCom.class);

		// root data
		Root<KscmtEstPriceCom> root = cq.from(KscmtEstPriceCom.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceCom_.kscmtEstPriceComPK)
						.get(KscmtEstPriceComPK_.cid), companyId));

		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstPriceCom_.kscmtEstPriceComPK)
						.get(KscmtEstPriceComPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstPriceCom> query = em.createQuery(cq);

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
	private List<KscmtEstDaysCom> getEstimateDaysCompanyDefault(String companyId,
			int targetYear) {
		List<KscmtEstDaysCom> estimateDaysCompanys = new ArrayList<>();
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
	private List<KscmtEstDaysCom> getEstimateDaysCompany(String companyId, int targetYear) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		// call KSCMT_EST_DAYS_COM (KscmtEstDaysCom SQL)
		CriteriaQuery<KscmtEstDaysCom> cq = criteriaBuilder
				.createQuery(KscmtEstDaysCom.class);
		
		// root data
		Root<KscmtEstDaysCom> root = cq.from(KscmtEstDaysCom.class);
		
		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtEstDaysCom_.kscmtEstDaysComPK).get(KscmtEstDaysComPK_.cid),
				companyId));
		
		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstDaysCom_.kscmtEstDaysComPK)
						.get(KscmtEstDaysComPK_.targetYear), targetYear));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// create query
		TypedQuery<KscmtEstDaysCom> query = em.createQuery(cq);
		
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
	private CompanyEstablishment toDomain(List<KscmtEstTimeCom> estimateTimeCompanys,
			List<KscmtEstPriceCom> estimatePriceCompanys,
			List<KscmtEstDaysCom> estimateDaysCompanys) {
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
	public KscmtEstTimeCom toEntityTimeDefault(String companyId, int targetYear, int targetCls) {
		KscmtEstTimeCom entity = new KscmtEstTimeCom();
		entity.setKscmtEstTimeComPK(new KscmtEstTimeComPK(companyId, targetYear, targetCls));
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
	public KscmtEstPriceCom toEntityPriceDefault(String companyId, int targetYear,
			int targetCls) {
		KscmtEstPriceCom entity = new KscmtEstPriceCom();
		entity.setKscmtEstPriceComPK(
				new KscmtEstPriceComPK(companyId, targetYear, targetCls));
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
	public KscmtEstDaysCom toEntityDaysDefault(String companyId, int targetYear,
			int targetCls) {
		KscmtEstDaysCom entity = new KscmtEstDaysCom();
		entity.setKscmtEstDaysComPK(new KscmtEstDaysComPK(companyId, targetYear, targetCls));
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
		List<KscmtEstTimeCom> estimateTimeCompanys = this.getEstimateTimeCompany(
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
		List<KscmtEstPriceCom> estimatePriceCompanys = this.getEstimatePriceCompany(
				companyEstablishment.getCompanyId().v(), companyEstablishment.getTargetYear().v());

		// check exist data
		if (CollectionUtil.isEmpty(estimatePriceCompanys)) {
			estimatePriceCompanys = this.getEstimatePriceCompanyDefault(
					companyEstablishment.getCompanyId().v(),
					companyEstablishment.getTargetYear().v());
			isAddPrice = true;
		}
		// find by id => optional data
		List<KscmtEstDaysCom> estimateDaysCompanys = this.getEstimateDaysCompany(
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
		List<KscmtEstTimeCom> estimateTimeCompanys = this.getEstimateTimeCompany(companyId,
				targetYear);
		
		// find by price
		List<KscmtEstPriceCom> estimatePriceCompanys = this.getEstimatePriceCompany(companyId,
				targetYear);
		
		// find by number of day
		List<KscmtEstDaysCom> estimateDaysCompanys = this.getEstimateDaysCompany(companyId,
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
