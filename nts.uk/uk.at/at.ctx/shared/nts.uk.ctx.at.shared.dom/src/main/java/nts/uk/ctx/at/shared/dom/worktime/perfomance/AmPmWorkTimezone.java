/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.perfomance;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

/**
 * The Class AmPmWorkTimezone.
 */
//
@Getter
public class AmPmWorkTimezone extends DeductionTime {

	/** The am pm atr. */
	// 午前午後区分
	private AmPmAtr amPmAtr;

	/**
	 * Instantiates a new am pm work timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public AmPmWorkTimezone(AmPmWorkTimezoneGetMemento memento) {
		super(memento.getStart(), memento.getEnd());
		this.amPmAtr = memento.getAmPmAtr();
	}

}
