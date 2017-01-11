/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

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

}
