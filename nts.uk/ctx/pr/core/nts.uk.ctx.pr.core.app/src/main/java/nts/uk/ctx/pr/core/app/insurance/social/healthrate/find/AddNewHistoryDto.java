/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class HealthInsuranceBaseCommand.
 */
@Getter
@Setter
public class AddNewHistoryDto {

	/** The office code. */
	private String officeCode;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The is clone data. */
	private Boolean isCloneData;
}
