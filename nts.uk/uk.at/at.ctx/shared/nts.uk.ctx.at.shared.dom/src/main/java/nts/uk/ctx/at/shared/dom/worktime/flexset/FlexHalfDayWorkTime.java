/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;

/**
 * The Class FlexHalfDayWorkTime.
 */
//フレックス勤務の平日出勤用勤務時間帯
@Getter
public class FlexHalfDayWorkTime extends DomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private List<FlowWorkRestTimezone> lstRestTimezone;
	
	/** The work timezone. */
	// 勤務時間帯
	private FixedWorkTimezoneSet workTimezone;
	
	/** The ampm atr. */
	// 午前午後区分
	private AmPmAtr ampmAtr;

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		if (this.isNotFixRestTime() && this.hasNoNo1()) {
			throw new BusinessException("Msg_847");
		}
	}

	/**
	 * Checks if is not fix rest time.
	 *
	 * @return true, if is not fix rest time
	 */
	private boolean isNotFixRestTime() {
		return this.lstRestTimezone.stream().anyMatch(item -> !item.isFixRestTime());
	}

	/**
	 * Checks for no no 1.
	 *
	 * @return true, if successful
	 */
	private boolean hasNoNo1() {
		val EM_TIME_NO_1 = 1;
		return this.workTimezone.getLstWorkingTimezone().stream()
				.anyMatch(item -> item.getEmploymentTimeFrameNo().v() != EM_TIME_NO_1);
	}

	/**
	 * Instantiates a new flex half day work time.
	 *
	 * @param memento the memento
	 */
	public FlexHalfDayWorkTime(FlexHalfDayWorkTimeGetMemento memento) {
		this.lstRestTimezone = memento.getLstRestTimezone();
		this.workTimezone = memento.getWorkTimezone();
		this.ampmAtr = memento.getAmpmAtr();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlexHalfDayWorkTimeSetMemento memento){
		memento.setLstRestTimezone(this.lstRestTimezone);
		memento.setWorkTimezone(this.workTimezone);
		memento.setAmpmAtr(this.ampmAtr);
	}
}
