/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import java.util.Set;

import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Class UnemployeeInsuranceRate.
 */
@Data
public class UnemployeeInsuranceRate extends AggregateRoot {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The rate items. */
	private Set<UnemployeeInsuranceRateItem> rateItems;

	/**
	 * Instantiates a new unemployee insurance rate.
	 */
	public UnemployeeInsuranceRate() {
		super();
	}

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new unemployee insurance rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnemployeeInsuranceRate(UnemployeeInsuranceRateMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.historyId = memento.getHistoryId();
		this.applyRange = memento.getApplyRange();
		this.rateItems = memento.getRateItems();
		this.setVersion(memento.getVersion());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(UnemployeeInsuranceRateMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setHistoryId(this.historyId);
		memento.setApplyRange(this.applyRange);
		memento.setRateItems(this.rateItems);
		memento.setVersion(this.getVersion());
	}

}
