/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.ListUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.PensionRateService;

/**
 * The Class PensionRate.
 */
@Getter
public class PensionRate extends AggregateRoot {
	/** The fund rate item count. */
	private final int FUND_RATE_ITEM_COUNT = 6;

	/** The rounding method count. */
	private final int ROUNDING_METHOD_COUNT = 4;

	/** The history id. */
	// historyId
	private String historyId;

	/** The company code. */
	private CompanyCode companyCode;

	/** The office code. */
	private OfficeCode officeCode;

	/** The apply range. */
	private MonthRange applyRange;

	/** The max amount. */
	private CommonAmount maxAmount;

	/** The fund rate items. */
	private List<FundRateItem> fundRateItems;

	/** The premium rate items. */
	private List<PensionPremiumRateItem> premiumRateItems;

	/** The child contribution rate. */
	private Ins2Rate childContributionRate;

	/** The rounding methods. */
	private List<PensionRateRounding> roundingMethods;

	/**
	 * Instantiates a new pension rate.
	 */
	public PensionRate() {
		super();
	}

	/**
	 * Validate.
	 *
	 * @param service
	 *            the service
	 */
	public void validate(PensionRateService service) {
		// Validate required item
		service.validateRequiredItem(this);
		// if (StringUtil.isNullOrEmpty(officeCode.v(), true) || applyRange ==
		// null || maxAmount == null
		// || childContributionRate == null || ListUtil.isEmpty(fundRateItems)
		// || fundRateItems.size() != FUND_RATE_ITEM_COUNT ||
		// ListUtil.isEmpty(roundingMethods)
		// || roundingMethods.size() != ROUNDING_METHOD_COUNT) {
		// throw new BusinessException("ER001");
		// }

		// Check consistency date range.
		service.validateDateRange(this);
		// History after start date and time exists
		// throw new BusinessException("ER011"); ER0123!?
	}

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new pension rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public PensionRate(PensionRateGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.companyCode = memento.getCompanyCode();
		this.officeCode = memento.getOfficeCode();
		this.applyRange = memento.getApplyRange();
		this.maxAmount = memento.getMaxAmount();
		this.fundRateItems = memento.getFundRateItems();
		this.premiumRateItems = memento.getPremiumRateItems();
		this.childContributionRate = memento.getChildContributionRate();
		this.roundingMethods = memento.getRoundingMethods();
		this.setVersion(memento.getVersion());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(PensionRateSetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setCompanyCode(this.companyCode);
		memento.setOfficeCode(this.officeCode);
		memento.setApplyRange(this.applyRange);
		memento.setMaxAmount(this.maxAmount);
		memento.setFundRateItems(this.fundRateItems);
		memento.setPremiumRateItems(this.premiumRateItems);
		memento.setChildContributionRate(this.childContributionRate);
		memento.setRoundingMethods(this.roundingMethods);
		memento.setVersion(this.getVersion());
	}

}
