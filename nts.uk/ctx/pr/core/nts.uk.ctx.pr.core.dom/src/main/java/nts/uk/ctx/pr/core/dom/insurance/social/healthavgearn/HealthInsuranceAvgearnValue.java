/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The Class HealthInsuranceAvgearnValue.
 */
@Getter
@NoArgsConstructor
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
	 *
	 * @param healthBasicMny
	 *            the health basic mny
	 * @param healthGeneralMny
	 *            the health general mny
	 * @param healthNursingMny
	 *            the health nursing mny
	 * @param healthSpecificMny
	 *            the health specific mny
	 */
	public HealthInsuranceAvgearnValue(Long healthBasicMny, Long healthGeneralMny, Long healthNursingMny,
			Long healthSpecificMny) {
		super();
		this.healthBasicMny = healthBasicMny;
		this.healthGeneralMny = healthGeneralMny;
		this.healthNursingMny = healthNursingMny;
		this.healthSpecificMny = healthSpecificMny;
	}

}
