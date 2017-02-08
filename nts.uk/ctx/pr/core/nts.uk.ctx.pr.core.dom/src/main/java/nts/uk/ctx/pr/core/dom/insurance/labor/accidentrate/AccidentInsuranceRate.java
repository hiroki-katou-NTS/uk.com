/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate;

import java.util.Set;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;

/**
 * The Class AccidentInsuranceRate.
 */
@Data
public class AccidentInsuranceRate extends AggregateRoot {

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The short name. */
	private Set<InsuBizRateItem> rateItems;

	/**
	 * Instantiates a new accident insurance rate.
	 */
	public AccidentInsuranceRate() {
		super();
	}

	/**
	 * Instantiates a new accident insurance rate.
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
	public AccidentInsuranceRate(String historyId, CompanyCode companyCode, MonthRange applyRange,
			Set<InsuBizRateItem> rateItems) {
		super();

		// Validate required item
		if (applyRange == null) {
			throw new BusinessException("ER001");
		}

		// TODO: Check consistency date range.
		// History after start date and time exists
		// throw new BusinessException("ER010");

		this.historyId = historyId;
		this.companyCode = companyCode;
		this.applyRange = applyRange;
		this.rateItems = rateItems;
	}

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public AccidentInsuranceRate(AccidentInsuranceRateMemento memento) {
		this.historyId = memento.getHistoryId();
		this.companyCode = memento.getCompanyCode();
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
	public void saveToMemento(AccidentInsuranceRateMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setCompanyCode(this.companyCode);
		memento.setApplyRange(this.applyRange);
		memento.setRateItems(this.rateItems);
		memento.setVersion(this.getVersion());
	}

}
