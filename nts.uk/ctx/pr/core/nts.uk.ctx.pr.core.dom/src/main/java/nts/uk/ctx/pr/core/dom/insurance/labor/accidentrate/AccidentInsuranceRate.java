/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;

/**
 * The Class AccidentInsuranceRate.
 */
@Getter
public class AccidentInsuranceRate extends DomainObject {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The short name. */
	private Set<InsuBizRateItem> rateItems;

	private AccidentInsuranceRate() {
		this.historyId = IdentifierUtil.randomUniqueId();
	}

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public AccidentInsuranceRate(AccidentInsuranceRateGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.companyCode = memento.getCompanyCode();
		this.applyRange = memento.getApplyRange();
		this.rateItems = memento.getRateItems();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AccidentInsuranceRateSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setCompanyCode(this.companyCode);
		memento.setApplyRange(this.applyRange);
		memento.setRateItems(this.rateItems);
	}

	public AccidentInsuranceRate copyWithDate(YearMonth start) {
		AccidentInsuranceRate newDomain = new AccidentInsuranceRate();
		newDomain.companyCode = this.companyCode;
		newDomain.applyRange = MonthRange.range(start, this.applyRange.getEndMonth());
		newDomain.rateItems = this.rateItems;
		return newDomain;
	}

	/**
	 * Creates the with intial.
	 *
	 * @param companyCode
	 *            the company code
	 * @param startYearMonth
	 *            the start year month
	 * @return the unemployee insurance rate
	 */
	public static final AccidentInsuranceRate createWithIntial(String companyCode, YearMonth startYearMonth) {
		AccidentInsuranceRate domain = new AccidentInsuranceRate();
		domain.companyCode = new CompanyCode(companyCode);
		domain.applyRange = MonthRange.toMaxDate(startYearMonth);
		Set<InsuBizRateItem> setItem = new HashSet<>();
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz1St));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz2Nd));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz3Rd));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz4Th));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz5Th));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz6Th));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz7Th));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz8Th));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz9Th));
		setItem.add(AccidentInsuranceRate.valueIntial(BusinessTypeEnum.Biz10Th));
		domain.rateItems = setItem;
		return domain;
	}

	public static final InsuBizRateItem valueIntial(BusinessTypeEnum businessTypeEnum) {
		return new InsuBizRateItem(businessTypeEnum, Double.valueOf(0d), RoundingMethod.RoundUp);
	}

	public void setMaxDate() {
		this.applyRange = MonthRange.toMaxDate(this.getApplyRange().getStartMonth());
	}
}
