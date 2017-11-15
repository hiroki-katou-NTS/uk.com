/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.difftime;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class DiffTimeDeductTimezone.
 */
//時差勤務の控除時間帯

/**
 * Checks if is update start time.
 *
 * @return true, if is update start time
 */
@Getter
public class DiffTimeDeductTimezone extends DomainObject {

	/** The is update start time. */
	// 開始時刻に合わせて時刻を変動させる
	private boolean isUpdateStartTime;
}
