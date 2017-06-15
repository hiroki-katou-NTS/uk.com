/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

import java.time.Month;

import lombok.Value;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.AttendanceTime;

/**
 * The Class Monthly.
 */
@Value
public class Monthly extends DomainObject {

	/** The time. */
	private AttendanceTime time;

	/** The month. */
	private Month month;
}