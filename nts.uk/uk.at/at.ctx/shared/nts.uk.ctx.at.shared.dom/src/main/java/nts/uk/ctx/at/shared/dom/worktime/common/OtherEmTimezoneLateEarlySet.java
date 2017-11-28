/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class OtherEmTimezoneLateEarlySet.
 */
//就業時間帯の遅刻・早退別設定

/**
 * Gets the late early atr.
 *
 * @return the late early atr
 */
@Getter
public class OtherEmTimezoneLateEarlySet extends DomainObject {

	/** The del time rounding set. */
	//控除時間丸め設定
	private TimeRoundingSetting delTimeRoundingSet; 
	
	/** The stamp exactly time is late early. */
	//時間丁度の打刻は遅刻・早退とする
	private boolean stampExactlyTimeIsLateEarly;
	
	/** The grace time set. */
	//猶予時間設定
	private GraceTimeSet graceTimeSet;
	
	/** The record time rounding set. */
	//計上時間丸め設定
	private TimeRoundingSetting recordTimeRoundingSet;
	
	/** The late early atr. */
	//遅刻早退区分
	private LateEarlyAtr lateEarlyAtr;
}
