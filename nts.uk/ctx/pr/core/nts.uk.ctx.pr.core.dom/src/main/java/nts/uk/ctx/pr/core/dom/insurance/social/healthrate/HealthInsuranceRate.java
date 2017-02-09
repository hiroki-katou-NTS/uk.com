/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.healthrate;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.service.HealthInsuranceRateService;

/**
 * The Class HealthInsuranceRate.
 */
@Getter
public class HealthInsuranceRate extends AggregateRoot {

	/** The Constant INSURANCE_RATE_ITEM_COUNT. */
	private static final int INSURANCE_RATE_ITEM_COUNT = 8;

	/** The Constant HEALTH_INSURANCE_ROUNDING_COUNT. */
	private static final int HEALTH_INSURANCE_ROUNDING_COUNT = 4;

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
	private Boolean autoCalculate;

	/** The max amount. */
	private CommonAmount maxAmount;

	/** The rate items. */
	private List<InsuranceRateItem> rateItems;

	/** The rounding methods. */
	private List<HealthInsuranceRounding> roundingMethods;

	/**
	 * Instantiates a new health insurance rate.
	 */
	public HealthInsuranceRate() {
		super();
	}

	/**
	 * Validate.
	 *
	 * @param service
	 *            the service
	 */
	public void validate(HealthInsuranceRateService service) {
		// Validate required item
		service.validateRequiredItem(this);
		// if (StringUtil.isNullOrEmpty(officeCode.v(), true) || applyRange ==
		// null || maxAmount == null
		// || ListUtil.isEmpty(rateItems) || rateItems.size() !=
		// INSURANCE_RATE_ITEM_COUNT
		// || ListUtil.isEmpty(roundingMethods) || roundingMethods.size() !=
		// HEALTH_INSURANCE_ROUNDING_COUNT) {
		// throw new BusinessException("ER001");
		// }

		// Check consistency date range.
		service.validateDateRange(this);
		// History after start date and time exists
		// throw new BusinessException("ER011"); ER0123!?

	}

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
		this.setVersion(memento.getVersion());
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
		memento.setVersion(this.getVersion());
	}

}
