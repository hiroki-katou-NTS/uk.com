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
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRatePK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate_;

/**
 * The Class JpaHealthInsuranceRateRepository.
 */
@Stateless
public class JpaHealthInsuranceRateRepository extends JpaRepository implements HealthInsuranceRateRepository {

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
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#findAll(java.lang.String, java.lang.String)
	 */
	@Override
	public List<HealthInsuranceRate> findAll(String companyCode, String officeCode) {

		// Get entity manager
		EntityManager em = this.getEntityManager();
		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuRate> cq = cb.createQuery(QismtHealthInsuRate.class);
		Root<QismtHealthInsuRate> root = cq.from(QismtHealthInsuRate.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK).get(QismtHealthInsuRatePK_.ccd),
				companyCode));
		predicateList.add(
				cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK).get(QismtHealthInsuRatePK_.siOfficeCd),
						officeCode));

		cq.where(predicateList.toArray(new Predicate[] {}));
		
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
		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtHealthInsuRate> cq = cb.createQuery(QismtHealthInsuRate.class);
		Root<QismtHealthInsuRate> root = cq.from(QismtHealthInsuRate.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(cb.equal(root.get(QismtHealthInsuRate_.qismtHealthInsuRatePK).get(QismtHealthInsuRatePK_.histId),
				historyId));

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
}
