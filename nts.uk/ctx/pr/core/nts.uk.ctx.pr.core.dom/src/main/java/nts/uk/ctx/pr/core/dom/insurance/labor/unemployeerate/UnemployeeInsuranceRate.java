/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
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

	/**
	 * Instantiates a new unemployee insurance rate.
	 */
	public UnemployeeInsuranceRate() {
		super();
	}

	/**
	 * Instantiates a new unemployee insurance rate.
	 *
	 * @param historyId
	 *            the history id
	 * @param companyCode
	 *            the company code
	 * @param applyRange
	 *            the apply range
	 * @param rateItems
	 *            the rate items
	 */
	public UnemployeeInsuranceRate(String historyId, CompanyCode companyCode, MonthRange applyRange,
			Set<UnemployeeInsuranceRateItem> rateItems) {
		super();

		// Validate required item
		if (applyRange == null) {
			throw new BusinessException("ER001");
		}

		// TODO: Check consistency date range.
		// History after start date and time exists
		// throw new BusinessException("ER010");

		// TODO: Check duplicate start date. !? in EAP file.
		// throw new BusinessException("ER005");

		this.historyId = historyId;
		this.companyCode = companyCode;
		this.applyRange = applyRange;
		this.rateItems = rateItems;
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
