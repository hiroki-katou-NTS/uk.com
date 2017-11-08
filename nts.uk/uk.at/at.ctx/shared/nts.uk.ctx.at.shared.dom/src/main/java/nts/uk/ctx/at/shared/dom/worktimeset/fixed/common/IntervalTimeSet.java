/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

//インターバル時間設定
@Getter
public class IntervalTimeSet extends DomainObject {

	// インターバル免除時間を使用する
	private boolean useIntervalExemptionTime;

	// インターバル免除時間丸め
	private TimeRounding intervalExemptionTimeRound;

	// インターバル時間
	private IntervalTime intervalTime;

	// インターバル時間を使用する
	private boolean useIntervalTime;
}
