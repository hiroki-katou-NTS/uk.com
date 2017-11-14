/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OneDayTime;

/**
 * The Class WorkTimezoneMedicalSet.
 */
//就業時間帯の医療設定
@Getter
public class WorkTimezoneMedicalSet extends DomainObject {
	
	/** The rounding set. */
	//丸め設定
	private TimeRoundingSetting roundingSet;
	
	/** The work system atr. */
	//勤務体系区分
	private WorkSystemAtr workSystemAtr;
	
	/** The application time. */
	//申送時間
	private OneDayTime applicationTime;
	
}
