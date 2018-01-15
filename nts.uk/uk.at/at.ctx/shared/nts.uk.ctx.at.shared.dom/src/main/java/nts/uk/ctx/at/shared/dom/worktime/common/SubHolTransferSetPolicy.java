/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface SubHolTransferSetPolicy.
 */
public interface SubHolTransferSetPolicy {

	/**
	 * Validate.
	 *
	 * @param predSet the pred set
	 * @param subHdSet the sub hd set
	 */
	void validate(PredetemineTimeSetting predSet, SubHolTransferSet subHdSet);
}
