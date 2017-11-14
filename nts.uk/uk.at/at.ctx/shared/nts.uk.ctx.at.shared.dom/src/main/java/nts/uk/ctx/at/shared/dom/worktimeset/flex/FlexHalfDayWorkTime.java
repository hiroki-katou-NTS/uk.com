/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktimeset.flex;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;
import nts.uk.ctx.at.shared.dom.worktimeset.common.FlowWorkRestTimezone;

/**
 * The Class FlexHalfDayWorkTime.
 */
//フレックス勤務の平日出勤用勤務時間帯

/**
 * Gets the am pm classification.
 *
 * @return the am pm classification
 */
@Getter
public class FlexHalfDayWorkTime extends DomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private FlowWorkRestTimezone restTimezone;
	
	// TODO wait QA勤務時間帯
	// private
	
	/** The Am pm classification. */
	// 午前午後区分
	private AmPmClassification AmPmClassification;
}
