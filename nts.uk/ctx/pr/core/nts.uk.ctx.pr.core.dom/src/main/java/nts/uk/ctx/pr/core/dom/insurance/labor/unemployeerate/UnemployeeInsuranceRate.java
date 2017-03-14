/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Class UnemployeeInsuranceRate.
 */
@Getter
public class UnemployeeInsuranceRate extends DomainObject {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The apply range. */
	@Setter
	private MonthRange applyRange;

	/** The rate items. */
	@Setter
	private Set<UnemployeeInsuranceRateItem> rateItems;

	/**
	 * Instantiates a new unemployee insurance rate.
	 */
	private UnemployeeInsuranceRate() {
		this.historyId = IdentifierUtil.randomUniqueId();
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new unemployee insurance rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnemployeeInsuranceRate(UnemployeeInsuranceRateGetMemento memento) {
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
	public void saveToMemento(UnemployeeInsuranceRateSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setCompanyCode(this.companyCode);
		memento.setApplyRange(this.applyRange);
		memento.setRateItems(this.rateItems);
	}

	public YearMonth getStart() {
		return this.applyRange.getStartMonth();
	}

	public YearMonth getEnd() {
		return this.applyRange.getEndMonth();
	}

	public void setStart(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(yearMonth, this.applyRange.getEndMonth());
	}

	public void setEnd(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(this.applyRange.getStartMonth(), yearMonth);
	}

	public UnemployeeInsuranceRate copyWithDate(YearMonth start) {
		UnemployeeInsuranceRate newDomain = new UnemployeeInsuranceRate();
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
	public static final UnemployeeInsuranceRate createWithIntial(String companyCode, YearMonth startYearMonth) {
		UnemployeeInsuranceRate domain = new UnemployeeInsuranceRate();
		domain.companyCode = new CompanyCode(companyCode);
		domain.applyRange = MonthRange.toMaxDate(startYearMonth);
		Set<UnemployeeInsuranceRateItem> setItem = new HashSet<>();
		setItem.add(UnemployeeInsuranceRateItem.valueIntial(CareerGroup.Agroforestry));
		setItem.add(UnemployeeInsuranceRateItem.valueIntial(CareerGroup.Contruction));
		setItem.add(UnemployeeInsuranceRateItem.valueIntial(CareerGroup.Other));
		domain.rateItems = setItem;
		return domain;
	}
}
