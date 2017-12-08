/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface CommonWorkTimePolicy.
 */
public interface CommonWorkTimePolicy {

	/**
	 * Validate work timezone late early set.
	 *
	 * @param pred the pred
	 * @param workTimezoneLateEarlySet the work timezone late early set
	 */
	public void validateWorkTimezoneLateEarlySet(PredetemineTimeSetting pred,WorkTimezoneLateEarlySet workTimezoneLateEarlySet);
	
	/**
	 * Validate sub hol transfer set.
	 *
	 * @param pred the pred
	 * @param subHolTransferSet the sub hol transfer set
	 */
	public void validateSubHolTransferSet(PredetemineTimeSetting pred,SubHolTransferSet subHolTransferSet);
}
