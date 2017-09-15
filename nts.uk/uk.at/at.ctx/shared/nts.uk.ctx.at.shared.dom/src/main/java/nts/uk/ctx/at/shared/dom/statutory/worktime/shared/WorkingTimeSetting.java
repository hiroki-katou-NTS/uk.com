/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.shared;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;

/**
 * 労働時間設定.
 */
@Getter
@Setter
public class WorkingTimeSetting extends DomainObject {

	/** 日単位. */
	private DailyTime daily;

	/** 月単位. */
	private List<Monthly> monthly;

	/** 週単位. */
	private WeeklyTime weekly;
}
