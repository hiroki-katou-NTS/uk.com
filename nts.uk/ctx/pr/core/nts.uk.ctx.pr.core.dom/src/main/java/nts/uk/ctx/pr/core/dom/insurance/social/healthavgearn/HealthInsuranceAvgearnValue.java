/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class HealthInsuranceAvgearnValue.
 */
@Data
@AllArgsConstructor
public class HealthInsuranceAvgearnValue {

	/** The health basic mny. */
	private Long healthBasicMny;

	/** The health general mny. */
	private Long healthGeneralMny;

	/** The health nursing mny. */
	private Long healthNursingMny;

	/** The health specific mny. */
	private Long healthSpecificMny;

	/**
	 * Instantiates a new health insurance avgearn value.
	 */
	public HealthInsuranceAvgearnValue() {
		super();
	}
	
}
