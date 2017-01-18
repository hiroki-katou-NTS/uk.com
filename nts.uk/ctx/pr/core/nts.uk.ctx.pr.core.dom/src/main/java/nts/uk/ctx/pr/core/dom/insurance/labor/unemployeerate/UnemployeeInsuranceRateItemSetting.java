/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;

/**
 * The Class LaborInsuranceOffice.
 */
@Getter
public class UnemployeeInsuranceRateItemSetting {

	/** The company code. */
	private RoundingMethod roundAtr;

	/** The code. */
	private Double rate;

}
