/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Class UnemployeeInsuranceRate.
 */
@Getter
public class UnemployeeInsuranceRate extends AggregateRoot {

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

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new unemployee insurance rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnemployeeInsuranceRate(UnemployeeInsuranceRateGetMemento memento) {
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
	public void saveToMemento(UnemployeeInsuranceRateSetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setHistoryId(this.historyId);
		memento.setApplyRange(this.applyRange);
		memento.setRateItems(this.rateItems);
		memento.setVersion(this.getVersion());
	}

}
