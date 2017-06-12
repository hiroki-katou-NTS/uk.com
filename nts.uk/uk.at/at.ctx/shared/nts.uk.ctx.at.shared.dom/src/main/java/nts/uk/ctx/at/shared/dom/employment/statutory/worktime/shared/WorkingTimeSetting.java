/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 労働時間設定.
 */
@Getter
public class WorkingTimeSetting extends DomainObject {

	/** 日単位. */
	private int daily;

	/** 月単位. */
	private List<Monthly> monthly;

	/** 週単位. */
	private int weekly;
}
