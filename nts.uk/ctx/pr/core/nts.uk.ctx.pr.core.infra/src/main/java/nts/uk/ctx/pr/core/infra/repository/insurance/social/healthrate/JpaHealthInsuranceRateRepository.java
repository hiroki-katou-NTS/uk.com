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

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

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


	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository#findAll(java.lang.String)
	 */
	@Override
	public List<HealthInsuranceRate> findAll(String officeCode) {
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
		MonthRange mr = MonthRange.range(new YearMonth(55), new YearMonth(33));

		HealthInsuranceRate mock = new HealthInsuranceRate(new HealthInsuranceRateGetMemento() {

			@Override
			public Long getVersion() {
				return 1l;
			}

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
			public Boolean getAutoCalculate() {
				return true;
			}

			@Override
			public MonthRange getApplyRange() {
				return mr;
			}
		});
		List<HealthInsuranceRate> mockList = new ArrayList<HealthInsuranceRate>();
		mockList.add(mock);
		mockList.add(mock);
		mockList.add(mock);
		return mockList;
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
		MonthRange mr = MonthRange.range(new YearMonth(55), new YearMonth(33));

		HealthInsuranceRate mock = new HealthInsuranceRate(new HealthInsuranceRateGetMemento() {

			@Override
			public Long getVersion() {
				return 1l;
			}

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
			public Boolean getAutoCalculate() {
				return false;
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
