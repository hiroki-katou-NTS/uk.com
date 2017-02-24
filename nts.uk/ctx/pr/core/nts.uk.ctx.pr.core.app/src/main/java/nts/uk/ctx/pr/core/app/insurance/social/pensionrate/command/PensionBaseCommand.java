/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.FundRateItemDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionPremiumRateItemDto;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

/**
 * The Class PensionBaseCommand.
 */
@Getter
@Setter
public abstract class PensionBaseCommand {

	/** The history id. */
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The office code. */
	private String officeCode;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;
	
	/** The fund input apply. */
	private Boolean fundInputApply;
	
	/** The auto calculate. */
	private Integer autoCalculate;

	/** The premium rate items. */
	private List<PensionPremiumRateItemDto> premiumRateItems;

	/** The fund rate items. */
	private List<FundRateItemDto> fundRateItems;

	/** The rounding methods. */
	private List<PensionRateRounding> roundingMethods;

	/** The max amount. */
	private BigDecimal maxAmount;
	
	/** The child contribution rate. */
	private BigDecimal childContributionRate;
}
