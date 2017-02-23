/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.InsuranceRateItemDto;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;

/**
 * The Class HealthInsuranceBaseCommand.
 */
@Getter
@Setter
public abstract class HealthInsuranceBaseCommand {

	/** The history id. */
	private String historyId;

	/** The company code. */
	private String companyCode;

	/** The office code. */
	private String officeCode;

	/** The start month. */
	private Integer startMonth;
	
	/** The end month. */
	private Integer endMonth;
	
	/** The auto calculate. */
	private Boolean autoCalculate;

	/** The max amount. */
	private BigDecimal maxAmount;

	/** The rate items. */
	private List<InsuranceRateItemDto> rateItems;

	/** The rounding methods. */
	private List<HealthInsuranceRounding> roundingMethods;
}
