/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import java.util.List;

import nts.arc.error.BundledBusinessException;

/**
 * The Interface CompensatoryOccurrencePolicy.
 */
public interface CompensatoryOccurrencePolicy {

	/**
	 * Validate.
	 *
	 * @param bundledBusinessExceptions the bundled business exceptions
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flowWorkSetting the flow work setting
	 */
	void validate(BundledBusinessException bundledBusinessExceptions, List<CompensatoryOccurrenceSetting> compensatoryOccurrenceSetting);
}
