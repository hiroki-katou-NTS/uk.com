/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.base.simplehistory.History;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins3Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.PaymentType;
import nts.uk.ctx.pr.core.dom.insurance.RoundingItem;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;

/**
 * The Class HealthInsuranceRate.
 */
@Getter
public class HealthInsuranceRate extends DomainObject implements History<HealthInsuranceRate> {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The office code. */
	private OfficeCode officeCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The auto calculate. */
	private CalculateMethod autoCalculate;

	/** The max amount. */
	private CommonAmount maxAmount;

	/** The rate items. */
	private Set<InsuranceRateItem> rateItems;

	/** The rounding methods. */
	private Set<HealthInsuranceRounding> roundingMethods;

	private HealthInsuranceRate() {
		this.historyId = IdentifierUtil.randomUniqueId();
	};
	
	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new health insurance rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public HealthInsuranceRate(HealthInsuranceRateGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.companyCode = memento.getCompanyCode();
		this.officeCode = memento.getOfficeCode();
		this.applyRange = memento.getApplyRange();
		this.autoCalculate = memento.getAutoCalculate();
		this.maxAmount = memento.getMaxAmount();
		this.rateItems = memento.getRateItems();
		this.roundingMethods = memento.getRoundingMethods();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(HealthInsuranceRateSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setCompanyCode(this.companyCode);
		memento.setOfficeCode(this.officeCode);
		memento.setApplyRange(this.applyRange);
		memento.setAutoCalculate(this.autoCalculate);
		memento.setMaxAmount(this.maxAmount);
		memento.setRateItems(this.rateItems);
		memento.setRoundingMethods(this.roundingMethods);
	}

	@Override
	public String getUuid() {
		return this.historyId;
	}

	@Override
	public PrimitiveValue<String> getMasterCode() {
		return this.officeCode;
	}

	@Override
	public YearMonth getStart() {
		return this.getApplyRange().getStartMonth();
	}

	@Override
	public YearMonth getEnd() {
		return this.getApplyRange().getEndMonth();
	}

	@Override
	public void setStart(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(yearMonth, this.applyRange.getEndMonth());
	}

	@Override
	public void setEnd(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(this.applyRange.getStartMonth(), yearMonth);
	}

	@Override
	public HealthInsuranceRate copyWithDate(YearMonth start) {
		//check if duplicate start date 
		if(this.applyRange.getStartMonth().equals(start))
		{
			throw new BusinessException("ER011");
		}
		HealthInsuranceRate HealthInsuranceRate = new HealthInsuranceRate();
		HealthInsuranceRate.applyRange = MonthRange.toMaxDate(start);;
		HealthInsuranceRate.autoCalculate = this.autoCalculate;
		HealthInsuranceRate.companyCode = this.companyCode;
		HealthInsuranceRate.maxAmount = this.maxAmount;
		HealthInsuranceRate.officeCode = this.officeCode;
		HealthInsuranceRate.rateItems = this.rateItems;
		HealthInsuranceRate.roundingMethods = this.roundingMethods;
		return HealthInsuranceRate;
	}
	
	public static final HealthInsuranceRate createWithIntial(CompanyCode companyCode, OfficeCode officeCode, YearMonth startYearMonth) {
		HealthInsuranceRate HealthInsuranceRate = new HealthInsuranceRate();
		HealthInsuranceRate.companyCode = companyCode;
		HealthInsuranceRate.officeCode = officeCode;
		HealthInsuranceRate.applyRange = MonthRange.toMaxDate(startYearMonth);
		HealthInsuranceRate.autoCalculate = CalculateMethod.Auto;
		HealthInsuranceRate.maxAmount = new CommonAmount(BigDecimal.ZERO);
		HealthInsuranceRate.rateItems = setDefaultRateItems();
		HealthInsuranceRate.roundingMethods = setDefaultRounding();
		return HealthInsuranceRate;
	}
	
	/**
	 * Sets the default rate items.
	 *
	 * @return the sets the
	 */
	private static Set<InsuranceRateItem> setDefaultRateItems() {
		Set<InsuranceRateItem> setItem = new HashSet<InsuranceRateItem>();
		InsuranceRateItem item1 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.Basic,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item1);
		InsuranceRateItem item2 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.General,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item2);
		InsuranceRateItem item3 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.Nursing,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item3);
		InsuranceRateItem item4 = new InsuranceRateItem(PaymentType.Salary, HealthInsuranceType.Special,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item4);
		InsuranceRateItem item5 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Basic,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item5);
		InsuranceRateItem item6 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.General,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item6);
		InsuranceRateItem item7 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Nursing,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item7);
		InsuranceRateItem item8 = new InsuranceRateItem(PaymentType.Bonus, HealthInsuranceType.Special,
				new HealthChargeRateItem(new Ins3Rate(BigDecimal.ZERO), new Ins3Rate(BigDecimal.ZERO)));
		setItem.add(item8);
		return setItem;
	}
	
	/**
	 * Sets the default rounding.
	 *
	 * @return the sets the
	 */
	//init default rounding values
	private static Set<HealthInsuranceRounding> setDefaultRounding() {
		Set<HealthInsuranceRounding> setItem = new HashSet<HealthInsuranceRounding>();
		RoundingItem salRounding = new RoundingItem();
		salRounding.setCompanyRoundAtr(RoundingMethod.RoundUp);
		salRounding.setPersonalRoundAtr(RoundingMethod.RoundUp);
		HealthInsuranceRounding item1 = new HealthInsuranceRounding(PaymentType.Salary, salRounding);
		setItem.add(item1);
		RoundingItem bnsRounding = new RoundingItem();
		bnsRounding.setCompanyRoundAtr(RoundingMethod.RoundUp);
		bnsRounding.setPersonalRoundAtr(RoundingMethod.RoundUp);
		HealthInsuranceRounding item2 = new HealthInsuranceRounding(PaymentType.Bonus, bnsRounding);
		setItem.add(item2);

		return setItem;
	}
			
}
