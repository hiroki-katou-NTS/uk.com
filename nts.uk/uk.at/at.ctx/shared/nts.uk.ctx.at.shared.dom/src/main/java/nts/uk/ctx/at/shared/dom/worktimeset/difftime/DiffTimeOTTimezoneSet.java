/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class DiffTimeOTTimezoneSet.
 */
@Getter
public class DiffTimeOTTimezoneSet extends DomainObject {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;
}
