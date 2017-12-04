/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkTimezoneSet;

/**
 * The Class FlexHalfDayWorkTime.
 */
//フレックス勤務の平日出勤用勤務時間帯
@Getter
public class FlexHalfDayWorkTime extends DomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private List<FlowWorkRestTimezone> restTimezone;
	
	/** The work timezone. */
	// 勤務時間帯
	private FixedWorkTimezoneSet workTimezone;
	
	/** The am pm atr. */
	// 午前午後区分
	private AmPmAtr amPmAtr;

	/**
	 * Instantiates a new flex half day work time.
	 *
	 * @param memento the memento
	 */
	public FlexHalfDayWorkTime(FlexHalfDayWorkTimeGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimezone = memento.getWorkTimezone();
		this.amPmAtr = memento.getAmPmAtr();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlexHalfDayWorkTimeSetMemento memento){
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimezone(this.workTimezone);
		memento.setAmPmAtr(this.amPmAtr);
	}
}
