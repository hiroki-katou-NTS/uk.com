/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.base.simplehistory.History;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;

/**
 * The Class PensionRate.
 */
@Getter
public class PensionRate extends DomainObject implements History<PensionRate> {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The office code. */
	private OfficeCode officeCode;

	/** The apply range. */
	@Setter
	private MonthRange applyRange;

	/** The fund input apply. */
	@Setter
	private Boolean fundInputApply;

	/** The auto calculate. */
	@Setter
	private CalculateMethod autoCalculate;

	/** The premium rate items. */
	@Setter
	private Set<PensionPremiumRateItem> premiumRateItems;

	/** The fund rate items. */
	@Setter
	private Set<FundRateItem> fundRateItems;

	/** The rounding methods. */
	@Setter
	private Set<PensionRateRounding> roundingMethods;

	/** The max amount. */
	@Setter
	private CommonAmount maxAmount;

	/** The child contribution rate. */
	@Setter
	private Ins2Rate childContributionRate;

	/**
	 * Instantiates a new pension rate.
	 */
	private PensionRate() {
		this.historyId = IdentifierUtil.randomUniqueId();
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new pension rate.
	 *
	 * @param memento the memento
	 */
	public PensionRate(PensionRateGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.companyCode = memento.getCompanyCode();
		this.officeCode = memento.getOfficeCode();
		this.applyRange = memento.getApplyRange();
		this.fundInputApply = memento.getFundInputApply();
		this.autoCalculate = memento.getAutoCalculate();
		this.maxAmount = memento.getMaxAmount();
		this.fundRateItems = memento.getFundRateItems();
		this.premiumRateItems = memento.getPremiumRateItems();
		this.childContributionRate = memento.getChildContributionRate();
		this.roundingMethods = memento.getRoundingMethods();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PensionRateSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setCompanyCode(this.companyCode);
		memento.setOfficeCode(this.officeCode);
		memento.setApplyRange(this.applyRange);
		memento.setFundInputApply(this.fundInputApply);
		memento.setAutoCalculate(this.autoCalculate);
		memento.setMaxAmount(this.maxAmount);
		memento.setFundRateItems(this.fundRateItems);
		memento.setPremiumRateItems(this.premiumRateItems);
		memento.setChildContributionRate(this.childContributionRate);
		memento.setRoundingMethods(this.roundingMethods);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getUuid()
	 */
	@Override
	public String getUuid() {
		return this.historyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getMasterCode()
	 */
	@Override
	public PrimitiveValue<String> getMasterCode() {
		return this.getOfficeCode();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getStart()
	 */
	@Override
	public YearMonth getStart() {
		return this.getApplyRange().getStartMonth();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getEnd()
	 */
	@Override
	public YearMonth getEnd() {
		return this.getApplyRange().getEndMonth();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#setStart(nts.arc.time.YearMonth)
	 */
	@Override
	public void setStart(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(yearMonth, this.applyRange.getEndMonth());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#setEnd(nts.arc.time.YearMonth)
	 */
	@Override
	public void setEnd(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(this.applyRange.getStartMonth(), yearMonth);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#copyWithDate(nts.arc.time.YearMonth)
	 */
	@Override
	public PensionRate copyWithDate(YearMonth start) {
		if(this.applyRange.getStartMonth().equals(start))
		{
			throw new BusinessException("ER011");
		}
		PensionRate newPensionRate = new PensionRate();
		newPensionRate.companyCode = this.companyCode;
		newPensionRate.officeCode = this.officeCode;
		newPensionRate.applyRange = MonthRange.toMaxDate(start);
		newPensionRate.autoCalculate = this.autoCalculate;
		newPensionRate.fundInputApply = this.fundInputApply;
		newPensionRate.maxAmount = this.maxAmount;
		newPensionRate.childContributionRate = this.childContributionRate;
		newPensionRate.fundRateItems = this.fundRateItems;
		newPensionRate.premiumRateItems = this.premiumRateItems;
		newPensionRate.roundingMethods = this.roundingMethods;
		return newPensionRate;
	}

	/**
	 * Creates the with intial.
	 *
	 * @param companyCode the company code
	 * @param officeCode the office code
	 * @param startYearMonth the start year month
	 * @return the pension rate
	 */
	public static final PensionRate createWithIntial(CompanyCode companyCode, OfficeCode officeCode,
			YearMonth startYearMonth) {
		PensionRate pensionRate = new PensionRate();
		pensionRate.companyCode = companyCode;
		pensionRate.officeCode = officeCode;
		pensionRate.applyRange = MonthRange.toMaxDate(startYearMonth);
		pensionRate.autoCalculate = CalculateMethod.Auto;
		pensionRate.fundInputApply = true;
		pensionRate.maxAmount = new CommonAmount(BigDecimal.ZERO);
		pensionRate.childContributionRate = new Ins2Rate(BigDecimal.ZERO);
		pensionRate.fundRateItems = setDafaultFunRateItems();
		pensionRate.premiumRateItems = setDefaultPremiumRateItems();
		pensionRate.roundingMethods = setDefaultRounding();
		return pensionRate;
	}
	
	/**
	 * Sets the dafault fun rate items.
	 *
	 * @return the sets the
	 */
	private static Set<FundRateItem> setDafaultFunRateItems() {
		Set<FundRateItem> setItems = new HashSet<FundRateItem>();
		
		PensionChargeRateItem charge = new PensionChargeRateItem();
		charge.setCompanyRate(new Ins2Rate(BigDecimal.ZERO));
		charge.setPersonalRate(new Ins2Rate(BigDecimal.ZERO));
		
		FundRateItem item1 = new FundRateItem(PaymentType.Salary,InsuranceGender.Male,charge,charge);
		setItems.add(item1);
		FundRateItem item2 = new FundRateItem(PaymentType.Salary,InsuranceGender.Female,charge,charge);
		setItems.add(item2);
		FundRateItem item3 = new FundRateItem(PaymentType.Salary,InsuranceGender.Unknow,charge,charge);
		setItems.add(item3);
		FundRateItem item4 = new FundRateItem(PaymentType.Bonus,InsuranceGender.Male,charge,charge);
		setItems.add(item4);
		FundRateItem item5 = new FundRateItem(PaymentType.Bonus,InsuranceGender.Female,charge,charge);
		setItems.add(item5);
		FundRateItem item6 = new FundRateItem(PaymentType.Bonus,InsuranceGender.Unknow,charge,charge);
		setItems.add(item6);
		return setItems;
	}

	/**
	 * Sets the default premium rate items.
	 *
	 * @return the sets the
	 */
	private static Set<PensionPremiumRateItem> setDefaultPremiumRateItems(){
		Set<PensionPremiumRateItem> setItems = new HashSet<PensionPremiumRateItem>();
		PensionChargeRateItem charge = new PensionChargeRateItem();
		charge.setCompanyRate(new Ins2Rate(BigDecimal.ZERO));
		charge.setPersonalRate(new Ins2Rate(BigDecimal.ZERO));
		PensionPremiumRateItem item1 = new PensionPremiumRateItem(PaymentType.Salary,InsuranceGender.Male,charge);
		setItems.add(item1);
		PensionPremiumRateItem item2 = new PensionPremiumRateItem(PaymentType.Salary,InsuranceGender.Female,charge);
		setItems.add(item2);
		PensionPremiumRateItem item3 = new PensionPremiumRateItem(PaymentType.Salary,InsuranceGender.Unknow,charge);
		setItems.add(item3);
		PensionPremiumRateItem item4 = new PensionPremiumRateItem(PaymentType.Bonus,InsuranceGender.Male,charge);
		setItems.add(item4);
		PensionPremiumRateItem item5 = new PensionPremiumRateItem(PaymentType.Bonus,InsuranceGender.Female,charge);
		setItems.add(item5);
		PensionPremiumRateItem item6 = new PensionPremiumRateItem(PaymentType.Bonus,InsuranceGender.Unknow,charge);
		setItems.add(item6);
		return setItems;
	}

	/**
	 * Sets the default rounding.
	 *
	 * @return the sets the
	 */
	// init default rounding values
	private static Set<PensionRateRounding> setDefaultRounding() {
		Set<PensionRateRounding> setItems = new HashSet<PensionRateRounding>();
		RoundingItem salRounding = new RoundingItem();
		salRounding.setCompanyRoundAtr(RoundingMethod.RoundUp);
		salRounding.setPersonalRoundAtr(RoundingMethod.RoundUp);
		PensionRateRounding item1 = new PensionRateRounding(PaymentType.Salary, salRounding);
		setItems.add(item1);
		RoundingItem bnsRounding = new RoundingItem();
		bnsRounding.setCompanyRoundAtr(RoundingMethod.RoundUp);
		bnsRounding.setPersonalRoundAtr(RoundingMethod.RoundUp);
		PensionRateRounding item2 = new PensionRateRounding(PaymentType.Bonus, bnsRounding);
		setItems.add(item2);

		return setItems;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PensionRate))
			return false;
		PensionRate other = (PensionRate) obj;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		return true;
	}
	
}
