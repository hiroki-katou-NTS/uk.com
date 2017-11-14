/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class RoundingTime.
 */
// 時刻丸め
@Getter
public class RoundingTime extends DomainObject {

	/** The Font rear section. */
	// 前後区分
	private FontRearSection FontRearSection;

	/** The rounding time unit. */
	// 時刻丸め単位
	private RoundingTimeUnit roundingTimeUnit;
}
