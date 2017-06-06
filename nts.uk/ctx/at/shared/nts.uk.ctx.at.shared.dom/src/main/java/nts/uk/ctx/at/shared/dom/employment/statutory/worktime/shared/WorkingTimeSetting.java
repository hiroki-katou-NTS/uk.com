/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

import nts.arc.layer.dom.DomainObject;

/**
 * 労働時間設定.
 */
public class WorkingTimeSetting extends DomainObject {

	/** 日単位. */
	private Daily daily;

	/** 月単位. */
	private Monthly monthly;

	/** 週単位. */
	private Weekly weekly;
}
