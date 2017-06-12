/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * The Class Monthly.
 */
@Getter
public class Monthly extends DomainObject {

	/** The time. */
	private int time;

	/** The month. */
	private Month month;
}