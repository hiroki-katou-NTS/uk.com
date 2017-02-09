/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.AccidentInsuranceRateService;

/**
 * The Class AccidentInsuranceRate.
 */
@Getter
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
	 * Validate.
	 *
	 * @param service
	 *            the service
	 */
	public void validate(AccidentInsuranceRateService service) {
		// Validate required item
		service.validateRequiredItem(this);
		// if (StringUtil.isNullOrEmpty(unitPriceCode.v(), true) ||
		// StringUtil.isNullOrEmpty(unitPriceName.v(), true)
		// || applyRange == null || budget == null) {
		// throw new BusinessException("ER001");
		// }

		// Check consistency date range.
		service.validateDateRange(this);
		// History after start date and time exists
		// throw new BusinessException("ER010");
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
		this.setVersion(memento.getVersion());
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
		memento.setVersion(this.getVersion());
	}

}
