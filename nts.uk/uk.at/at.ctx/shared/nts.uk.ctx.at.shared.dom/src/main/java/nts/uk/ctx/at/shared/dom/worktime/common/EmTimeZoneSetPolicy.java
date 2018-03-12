/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;

/**
 * The Interface EmTimeZoneSetPolicy.
 */
public interface EmTimeZoneSetPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param etzSet the etz set
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, EmTimeZoneSet etzSet);

	/**
	 * Validate timezone.
	 *
	 * @param be the be
	 * @param presTz the pres tz
	 * @param timezone the timezone
	 */
	void validateTimezone(BundledBusinessException be, PrescribedTimezoneSetting presTz, TimeZoneRounding timezone);
}
