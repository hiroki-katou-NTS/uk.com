/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthrate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import nts.uk.ctx.pr.core.dom.insurance.Ins3Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceType;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.QismtSocialInsuOffice;
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

		// Set<InsuranceRateItem> list1 = new HashSet<InsuranceRateItem>();
		// HealthChargeRateItem rateItem = new HealthChargeRateItem();
		// rateItem.setCompanyRate(new Ins3Rate(new BigDecimal(40.900)));
		// rateItem.setPersonalRate(new Ins3Rate(new BigDecimal(40.900)));
		// list1.add(new InsuranceRateItem(PaymentType.Bonus,
		// HealthInsuranceType.Basic, rateItem));
		// list1.add(new InsuranceRateItem(PaymentType.Salary,
		// HealthInsuranceType.General, rateItem));
		// list1.add(new InsuranceRateItem(PaymentType.Bonus,
		// HealthInsuranceType.Basic, rateItem));
		// list1.add(new InsuranceRateItem(PaymentType.Salary,
		// HealthInsuranceType.General, rateItem));
		// list1.add(new InsuranceRateItem(PaymentType.Bonus,
		// HealthInsuranceType.Basic, rateItem));
		// list1.add(new InsuranceRateItem(PaymentType.Salary,
		// HealthInsuranceType.General, rateItem));
		// list1.add(new InsuranceRateItem(PaymentType.Bonus,
		// HealthInsuranceType.Basic, rateItem));
		// list1.add(new InsuranceRateItem(PaymentType.Salary,
		// HealthInsuranceType.General, rateItem));
		//
		// Set<HealthInsuranceRounding> list2 = new
		// HashSet<HealthInsuranceRounding>();
		// RoundingItem ri = new RoundingItem();
		// ri.setCompanyRoundAtr(RoundingMethod.Down4_Up5);
		// ri.setPersonalRoundAtr(RoundingMethod.RoundDown);
		// list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		// list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		// list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		// list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		// MonthRange mr = MonthRange.range(new YearMonth(201601), new
		// YearMonth(201605));
		//
		// HealthInsuranceRate mock = new HealthInsuranceRate(new
		// HealthInsuranceRateGetMemento() {
		//
		// @Override
		// public Set<HealthInsuranceRounding> getRoundingMethods() {
		// // TODO Auto-generated method stub
		// return list2;
		// }
		//
		// @Override
		// public Set<InsuranceRateItem> getRateItems() {
		// // TODO Auto-generated method stub
		// return list1;
		// }
		//
		// @Override
		// public OfficeCode getOfficeCode() {
		// return new OfficeCode("000000");
		// }
		//
		// @Override
		// public CommonAmount getMaxAmount() {
		// return new CommonAmount(new BigDecimal(5));
		// }
		//
		// @Override
		// public String getHistoryId() {
		// return "a";
		// }
		//
		// @Override
		// public CompanyCode getCompanyCode() {
		// return new CompanyCode("Ｃ 事業所");
		// }
		//
		// @Override
		// public Boolean getAutoCalculate() {
		// return true;
		// }
		//
		// @Override
		// public MonthRange getApplyRange() {
		// return mr;
		// }
		// });
		// List<HealthInsuranceRate> mockList = new
		// ArrayList<HealthInsuranceRate>();
		// mockList.add(mock);
		// mockList.add(mock);
		// mockList.add(mock);
		// return mockList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.
	 * HealthInsuranceRateRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<HealthInsuranceRate> findById(String id) {
		Set<InsuranceRateItem> list1 = new HashSet<InsuranceRateItem>();
		HealthChargeRateItem rateItem = new HealthChargeRateItem();
		rateItem.setCompanyRate(new Ins3Rate(new BigDecimal(40.900)));
		rateItem.setPersonalRate(new Ins3Rate(new BigDecimal(40.900)));
		list1.add(new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Basic, rateItem));
		list1.add(new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.General, rateItem));
		list1.add(new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Basic, rateItem));
		list1.add(new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.General, rateItem));
		list1.add(new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Basic, rateItem));
		list1.add(new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.General, rateItem));
		list1.add(new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Basic, rateItem));
		list1.add(new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.General, rateItem));

		Set<HealthInsuranceRounding> list2 = new HashSet<HealthInsuranceRounding>();
		RoundingItem ri = new RoundingItem();
		ri.setCompanyRoundAtr(RoundingMethod.Down4_Up5);
		ri.setPersonalRoundAtr(RoundingMethod.RoundDown);
		list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		list2.add(new HealthInsuranceRounding(PaymentType.Bonus, ri));
		MonthRange mr = MonthRange.range(new YearMonth(201601), new YearMonth(201605));

		HealthInsuranceRate mock = new HealthInsuranceRate(new HealthInsuranceRateGetMemento() {

			@Override
			public Set<HealthInsuranceRounding> getRoundingMethods() {
				// TODO Auto-generated method stub
				return list2;
			}

			@Override
			public Set<InsuranceRateItem> getRateItems() {
				// TODO Auto-generated method stub
				return list1;
			}

			@Override
			public OfficeCode getOfficeCode() {
				return new OfficeCode("000000");
			}

			@Override
			public CommonAmount getMaxAmount() {
				return new CommonAmount(new BigDecimal(5));
			}

			@Override
			public String getHistoryId() {
				return "a";
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode("Ｃ 事業所");
			}

			@Override
			public CalculateMethod getAutoCalculate() {
				// TODO
				return CalculateMethod.Manual;
			}

			@Override
			public MonthRange getApplyRange() {
				return mr;
			}
		});
		return Optional.of(mock);
	}

	@Override
	public boolean isInvalidDateRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		return false;
	}
}
