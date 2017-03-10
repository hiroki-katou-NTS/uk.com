/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRatePK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate_;

/**
 * The Class JpaHealthInsuranceRateRepository.
 */
@Stateless
public class JpaHealthInsuranceRateRepository extends JpaRepository
		implements HealthInsuranceRateRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#add(nts.uk.ctx.pr.core.dom.insurance.social
	 * .healthrate.HealthInsuranceRate)
	 */
	@Override
	public void add(HealthInsuranceRate rate) {
		EntityManager em = this.getEntityManager();

		QismtHealthInsuRate entity = new QismtHealthInsuRate();
		rate.saveToMemento(new JpaHealthInsuranceRateSetMemento(entity));

		em.persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#update(nts.uk.ctx.pr.core.dom.insurance.
	 * social.healthrate.HealthInsuranceRate)
	 */
	@Override
	public void update(HealthInsuranceRate rate) {
		EntityManager em = this.getEntityManager();

		QismtHealthInsuRate entity = new QismtHealthInsuRate();
		rate.saveToMemento(new JpaHealthInsuranceRateSetMemento(entity));

		em.merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String historyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuRate> cq = cb.createQuery(QismtHealthInsuRate.class);
		Root<QismtHealthInsuRate> root = cq.from(QismtHealthInsuRate.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK)
				.get(QismtHealthInsuRatePK_.histId), historyId));
		cq.where(predicateList.toArray(new Predicate[] {}));
		List<QismtHealthInsuRate> result = em.createQuery(cq).getResultList();
		// If have no record.
		if (!ListUtil.isEmpty(result)) {
			QismtHealthInsuRate entity = new QismtHealthInsuRate();
			entity = result.get(0);
			em.remove(entity);
		} else {
			// TODO not found delete element
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#findAll(java.lang.String, java.lang.String)
	 */
	@Override
	public List<HealthInsuranceRate> findAll(CompanyCode companyCode) {

		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuRate> cq = cb.createQuery(QismtHealthInsuRate.class);
		Root<QismtHealthInsuRate> root = cq.from(QismtHealthInsuRate.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK)
				.get(QismtHealthInsuRatePK_.ccd), companyCode.v()));

		cq.where(predicateList.toArray(new Predicate[] {}));
		cq.orderBy(cb.desc(root.get(QismtHealthInsuRate_.strYm)));
		return em.createQuery(cq).getResultList().stream()
				.map(item -> new HealthInsuranceRate(new JpaHealthInsuranceRateGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<HealthInsuranceRate> findById(String historyId) {

		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuRate> cq = cb.createQuery(QismtHealthInsuRate.class);
		Root<QismtHealthInsuRate> root = cq.from(QismtHealthInsuRate.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK)
				.get(QismtHealthInsuRatePK_.histId), historyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.of(em.createQuery(cq).getResultList().stream()
				.map(item -> new HealthInsuranceRate(new JpaHealthInsuranceRateGetMemento(item)))
				.collect(Collectors.toList()).get(0));
	}

	@Override
	public boolean isInvalidDateRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HealthInsuranceRate> findAllOffice(CompanyCode companyCode, OfficeCode officeCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Query for.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuRate> cq = cb.createQuery(QismtHealthInsuRate.class);
		Root<QismtHealthInsuRate> root = cq.from(QismtHealthInsuRate.class);

		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK).get(QismtHealthInsuRatePK_.ccd),
				companyCode.v()));
		predicateList.add(
				cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK).get(QismtHealthInsuRatePK_.siOfficeCd),
						officeCode.v()));

		cq.where(predicateList.toArray(new Predicate[] {}));
		cq.orderBy(cb.desc(root.get(QismtHealthInsuRate_.strYm)));
		return em.createQuery(cq).getResultList().stream()
				.map(item -> new HealthInsuranceRate(new JpaHealthInsuranceRateGetMemento(item)))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteHistory(String uuid) {
		this.remove(uuid);
	}

	@Override
	public Optional<HealthInsuranceRate> findLastestHistoryByMasterCode(String companyCode, String officeCode) {
		return Optional.of(this.findAllOffice(new CompanyCode(companyCode),new OfficeCode(officeCode)).get(0));
	}

	@Override
	public Optional<HealthInsuranceRate> findHistoryByUuid(String uuid) {
		return this.findById(uuid);
	}

	@Override
	public void addHistory(HealthInsuranceRate history) {
		this.add(history);
	}

	@Override
	public void updateHistory(HealthInsuranceRate history) {
		this.update(history);
	}

	@Override
	public List<HealthInsuranceRate> findAllHistoryByMasterCode(String companyCode, String masterCode) {
		// TODO Auto-generated method stub
		return null;
	}
}
