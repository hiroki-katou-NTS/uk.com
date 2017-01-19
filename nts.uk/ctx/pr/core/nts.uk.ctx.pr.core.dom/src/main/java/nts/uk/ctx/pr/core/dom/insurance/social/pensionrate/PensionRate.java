/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;

/**
 * The Class PensionRate.
 */
@Getter
public class PensionRate extends AggregateRoot {

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
	private Long maxAmount;

	/** The fund rate items. */
	private List<FundRateItem> fundRateItems;

	/** The premium rate items. */
	private List<PensionPremiumRateItem> premiumRateItems;

	/** The child contribution rate. */
	private Double childContributionRate;

	/** The rounding methods. */
	private List<PensionRateRounding> roundingMethods;

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new pension rate.
	 *
	 * @param memento
	 *            the memento
	 */
	public PensionRate(PensionRateMemento memento) {
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
	public void saveToMemento(PensionRateMemento memento) {
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
