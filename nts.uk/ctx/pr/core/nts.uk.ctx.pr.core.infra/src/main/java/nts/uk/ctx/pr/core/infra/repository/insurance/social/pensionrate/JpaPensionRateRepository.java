/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionrate;

import java.math.BigDecimal;
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
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceGender;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRate;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRatePK_;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRate_;

/**
 * The Class JpaPensionRateRepository.
 */
@Stateless
public class JpaPensionRateRepository extends JpaRepository implements PensionRateRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #add(nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate)
	 */
	@Override
	public void add(PensionRate rate) {
		EntityManager em = this.getEntityManager();

		QismtPensionRate entity = new QismtPensionRate();
		rate.saveToMemento(new JpaPensionRateSetMemento(entity));

		em.persist(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #update(nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate)
	 */
	@Override
	public void update(PensionRate rate) {

		EntityManager em = this.getEntityManager();

		QismtPensionRate entity = new QismtPensionRate();
		rate.saveToMemento(new JpaPensionRateSetMemento(entity));

		em.merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #findAll(int)
	 */
	@Override
	public List<PensionRate> findAll(String companyCode, String officeCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtPensionRate> cq = cb.createQuery(QismtPensionRate.class);
		Root<QismtPensionRate> root = cq.from(QismtPensionRate.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(
				cb.equal(root.get(QismtPensionRate_.qismtPensionRatePK).get(QismtPensionRatePK_.ccd), companyCode));
		predicateList.add(cb.equal(root.get(QismtPensionRate_.qismtPensionRatePK).get(QismtPensionRatePK_.siOfficeCd),
				officeCode));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(item -> new PensionRate(new JpaPensionRateGetMemento(item))).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository
	 * #findById(java.lang.String)
	 */
	@Override
	public Optional<PensionRate> findById(String id) {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		// Query for indicated stress check.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QismtPensionRate> cq = cb.createQuery(QismtPensionRate.class);
		Root<QismtPensionRate> root = cq.from(QismtPensionRate.class);
		// Constructing list of parameters
		List<Predicate> predicateList = new ArrayList<Predicate>();

		// Construct condition.
		predicateList.add(
				cb.equal(root.get(QismtPensionRate_.qismtPensionRatePK).get(QismtPensionRatePK_.histId), id));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.of(em.createQuery(cq).getResultList().stream()
				.map(item -> new PensionRate(new JpaPensionRateGetMemento(item))).collect(Collectors.toList()).get(0));
	}

	@Override
	public boolean isInvalidDateRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		return false;
	}

}
