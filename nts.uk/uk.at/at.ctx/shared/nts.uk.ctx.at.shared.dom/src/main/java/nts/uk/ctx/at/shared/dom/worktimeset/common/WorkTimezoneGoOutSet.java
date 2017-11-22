/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimezoneGoOutSet.
 */
//就業時間帯の外出設定
@Getter
public class WorkTimezoneGoOutSet extends DomainObject{
	
	/** The total rounding set. */
	//合計丸め設定
	private TotalRoundingSet totalRoundingSet;
	
	/** The diff timezone setting. */
	//時間帯別設定
	private GoOutTimezoneRoundingSet diffTimezoneSetting;
}
