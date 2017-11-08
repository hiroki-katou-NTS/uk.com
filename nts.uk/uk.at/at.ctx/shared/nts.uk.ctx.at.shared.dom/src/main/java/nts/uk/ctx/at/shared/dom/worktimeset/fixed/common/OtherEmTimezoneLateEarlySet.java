/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.fixed.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

//就業時間帯の遅刻・早退別設定
@Getter
public class OtherEmTimezoneLateEarlySet extends DomainObject {

	//控除時間丸め設定
	private TimeRoundingSetting delTimeRoundingSet; 
	
	//時間丁度の打刻は遅刻・早退とする
	private boolean stampExactlyTimeIsLateEarly;
	
	//猶予時間設定
	private GraceTimeSet graceTimeSet;
	
	//計上時間丸め設定
	private TimeRoundingSetting recordTimeRoundingSet;
	
	//遅刻早退区分
	private LateEarlyAtr lateEarlyAtr;
}
