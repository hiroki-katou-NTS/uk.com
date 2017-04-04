/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social.pensionrate;

import java.util.HashSet;
import java.util.Set;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.FundInputApply;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.InsuranceGender;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionChargeRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionrate.QismtPensionRate;

/**
 * The Class JpaPensionRateGetMemento.
 */
public class JpaPensionRateGetMemento implements PensionRateGetMemento {

	/** The type value. */
	private QismtPensionRate typeValue;

	/**
	 * Instantiates a new jpa pension rate get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaPensionRateGetMemento(QismtPensionRate typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.typeValue.getQismtPensionRatePK().getHistId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return this.typeValue.getQismtPensionRatePK().getCcd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getOfficeCode()
	 */
	@Override
	public OfficeCode getOfficeCode() {
		return new OfficeCode(this.typeValue.getQismtPensionRatePK().getSiOfficeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getApplyRange()
	 */
	@Override
	public MonthRange getApplyRange() {
		return MonthRange.range(new YearMonth(this.typeValue.getStrYm()),
				new YearMonth(this.typeValue.getEndYm()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getMaxAmount()
	 */
	@Override
	public CommonAmount getMaxAmount() {
		return new CommonAmount(this.typeValue.getBonusPensionMaxMny());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getFundRateItems()
	 */
	@Override
	public Set<FundRateItem> getFundRateItems() {
		Set<FundRateItem> fundRateItems = new HashSet<FundRateItem>();

		PensionChargeRateItem salaryMaleBurden = new PensionChargeRateItem();
		salaryMaleBurden.setCompanyRate(new Ins2Rate(this.typeValue.getCPayFundMaleRate()));
		salaryMaleBurden.setPersonalRate(new Ins2Rate(this.typeValue.getPPayFundMaleRate()));
		PensionChargeRateItem salaryMaleExemption = new PensionChargeRateItem();
		salaryMaleExemption.setCompanyRate(new Ins2Rate(this.typeValue.getCPayFundExMaleRate()));
		salaryMaleExemption.setPersonalRate(new Ins2Rate(this.typeValue.getPPayFundExMaleRate()));
		FundRateItem salaryMale = new FundRateItem(PaymentType.Salary, InsuranceGender.Male,
				salaryMaleBurden, salaryMaleExemption);

		PensionChargeRateItem salaryFemaleBurden = new PensionChargeRateItem();
		salaryFemaleBurden.setCompanyRate(new Ins2Rate(this.typeValue.getCPayFundFemRate()));
		salaryFemaleBurden.setPersonalRate(new Ins2Rate(this.typeValue.getPPayFundFemRate()));
		PensionChargeRateItem salaryFemaleExemption = new PensionChargeRateItem();
		salaryFemaleExemption.setCompanyRate(new Ins2Rate(this.typeValue.getCPayFundExFemRate()));
		salaryFemaleExemption.setPersonalRate(new Ins2Rate(this.typeValue.getPPayFundExFemRate()));
		FundRateItem salaryFemale = new FundRateItem(PaymentType.Salary, InsuranceGender.Female,
				salaryFemaleBurden, salaryFemaleExemption);

		PensionChargeRateItem salaryUnknowBurden = new PensionChargeRateItem();
		salaryUnknowBurden.setCompanyRate(new Ins2Rate(this.typeValue.getCPayFundMinerRate()));
		salaryUnknowBurden.setPersonalRate(new Ins2Rate(this.typeValue.getPPayFundMinerRate()));
		PensionChargeRateItem salaryUnknowExemption = new PensionChargeRateItem();
		salaryUnknowExemption.setCompanyRate(new Ins2Rate(this.typeValue.getCPayFundExMinerRate()));
		salaryUnknowExemption
				.setPersonalRate(new Ins2Rate(this.typeValue.getPPayFundExMinerRate()));
		FundRateItem salaryUnknow = new FundRateItem(PaymentType.Salary, InsuranceGender.Unknow,
				salaryUnknowBurden, salaryUnknowExemption);

		PensionChargeRateItem bonusMaleBurden = new PensionChargeRateItem();
		bonusMaleBurden.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsFundManRate()));
		bonusMaleBurden.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsFundManRate()));
		PensionChargeRateItem bonusMaleExemption = new PensionChargeRateItem();
		bonusMaleExemption.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsFundExMaleRate()));
		bonusMaleExemption.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsFundExMaleRate()));
		FundRateItem bonusMale = new FundRateItem(PaymentType.Bonus, InsuranceGender.Male,
				bonusMaleBurden, bonusMaleExemption);

		PensionChargeRateItem bonusFemaleBurden = new PensionChargeRateItem();
		bonusFemaleBurden.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsFundFemRate()));
		bonusFemaleBurden.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsFundFemRate()));
		PensionChargeRateItem bonusFemaleExemption = new PensionChargeRateItem();
		bonusFemaleExemption.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsFundExFemRate()));
		bonusFemaleExemption.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsFundExFemRate()));
		FundRateItem bonusFemale = new FundRateItem(PaymentType.Bonus, InsuranceGender.Female,
				bonusFemaleBurden, bonusFemaleExemption);

		PensionChargeRateItem bonusUnknowBurden = new PensionChargeRateItem();
		bonusUnknowBurden.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsFundMinerRate()));
		bonusUnknowBurden.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsFundMinerRate()));
		PensionChargeRateItem bonusUnknowExemption = new PensionChargeRateItem();
		bonusUnknowExemption.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsFundExMinerRate()));
		bonusUnknowExemption.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsFundExMinerRate()));
		FundRateItem bonusUnknow = new FundRateItem(PaymentType.Bonus, InsuranceGender.Unknow,
				bonusUnknowBurden, bonusUnknowExemption);

		fundRateItems.add(salaryMale);
		fundRateItems.add(salaryFemale);
		fundRateItems.add(salaryUnknow);
		fundRateItems.add(bonusMale);
		fundRateItems.add(bonusFemale);
		fundRateItems.add(bonusUnknow);
		return fundRateItems;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getPremiumRateItems()
	 */
	@Override
	public Set<PensionPremiumRateItem> getPremiumRateItems() {

		Set<PensionPremiumRateItem> pensionRates = new HashSet<PensionPremiumRateItem>();

		PensionChargeRateItem salaryMaleCharge = new PensionChargeRateItem();
		salaryMaleCharge.setCompanyRate(new Ins2Rate(this.typeValue.getCPayPensionMaleRate()));
		salaryMaleCharge.setPersonalRate(new Ins2Rate(this.typeValue.getPPayPensionMaleRate()));
		PensionPremiumRateItem salaryMale = new PensionPremiumRateItem(PaymentType.Salary,
				InsuranceGender.Male, salaryMaleCharge);

		PensionChargeRateItem salaryFemaleCharge = new PensionChargeRateItem();
		salaryFemaleCharge.setCompanyRate(new Ins2Rate(this.typeValue.getCPayPensionFemRate()));
		salaryFemaleCharge.setPersonalRate(new Ins2Rate(this.typeValue.getPPayPensionFemRate()));
		PensionPremiumRateItem salaryFemale = new PensionPremiumRateItem(PaymentType.Salary,
				InsuranceGender.Female, salaryFemaleCharge);

		PensionChargeRateItem salaryUnknowCharge = new PensionChargeRateItem();
		salaryUnknowCharge.setCompanyRate(new Ins2Rate(this.typeValue.getCPayPensionMinerRate()));
		salaryUnknowCharge.setPersonalRate(new Ins2Rate(this.typeValue.getPPayPensionMinerRate()));
		PensionPremiumRateItem salaryUnknow = new PensionPremiumRateItem(PaymentType.Salary,
				InsuranceGender.Unknow, salaryUnknowCharge);

		PensionChargeRateItem bonusMaleCharge = new PensionChargeRateItem();
		bonusMaleCharge.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsPensionMaleRate()));
		bonusMaleCharge.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsPensionMaleRate()));
		PensionPremiumRateItem bonusMale = new PensionPremiumRateItem(PaymentType.Bonus,
				InsuranceGender.Male, bonusMaleCharge);

		PensionChargeRateItem bonusFemaleCharge = new PensionChargeRateItem();
		bonusFemaleCharge.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsPensionFemRate()));
		bonusFemaleCharge.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsPensionFemRate()));
		PensionPremiumRateItem bonusFemale = new PensionPremiumRateItem(PaymentType.Bonus,
				InsuranceGender.Female, bonusFemaleCharge);

		PensionChargeRateItem bonusUnknowCharge = new PensionChargeRateItem();
		bonusUnknowCharge.setCompanyRate(new Ins2Rate(this.typeValue.getCBnsPensionMinerRate()));
		bonusUnknowCharge.setPersonalRate(new Ins2Rate(this.typeValue.getPBnsPensionMinerRate()));
		PensionPremiumRateItem bonusUnknow = new PensionPremiumRateItem(PaymentType.Bonus,
				InsuranceGender.Unknow, bonusUnknowCharge);

		pensionRates.add(salaryMale);
		pensionRates.add(salaryFemale);
		pensionRates.add(salaryUnknow);
		pensionRates.add(bonusMale);
		pensionRates.add(bonusFemale);
		pensionRates.add(bonusUnknow);

		return pensionRates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getChildContributionRate()
	 */
	@Override
	public Ins2Rate getChildContributionRate() {
		return new Ins2Rate(this.typeValue.getChildContributionRate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getRoundingMethods()
	 */
	@Override
	public Set<PensionRateRounding> getRoundingMethods() {
		Set<PensionRateRounding> listRounding = new HashSet<PensionRateRounding>();
		RoundingItem salaryRoundingItem = new RoundingItem();
		RoundingItem bonusRoundingItem = new RoundingItem();
		salaryRoundingItem.setCompanyRoundAtr(
				RoundingMethod.valueOf(this.typeValue.getCPayPensionRoundAtr()));
		salaryRoundingItem.setPersonalRoundAtr(
				RoundingMethod.valueOf(this.typeValue.getPPayPensionRoundAtr()));
		bonusRoundingItem.setCompanyRoundAtr(
				RoundingMethod.valueOf(this.typeValue.getCBnsPensionRoundAtr()));
		bonusRoundingItem.setPersonalRoundAtr(
				RoundingMethod.valueOf(this.typeValue.getPBnsPensionRoundAtr()));

		PensionRateRounding roundSalary = new PensionRateRounding(PaymentType.Salary,
				salaryRoundingItem);
		PensionRateRounding roundBonus = new PensionRateRounding(PaymentType.Bonus,
				bonusRoundingItem);
		listRounding.add(roundSalary);
		listRounding.add(roundBonus);
		return listRounding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getFundInputApply()
	 */
	@Override
	public FundInputApply getFundInputApply() {
		if (this.typeValue.getPensionFundJoinAtr() == FundInputApply.Yes.value) {
			return FundInputApply.Yes;
		} else {
			return FundInputApply.No;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento
	 * #getAutoCalculate()
	 */
	@Override
	public CalculateMethod getAutoCalculate() {
		if (this.typeValue.getKeepEntryFlg() == CalculateMethod.Auto.value) {
			return CalculateMethod.Auto;
		} else {
			return CalculateMethod.Manual;
		}
	}

}
