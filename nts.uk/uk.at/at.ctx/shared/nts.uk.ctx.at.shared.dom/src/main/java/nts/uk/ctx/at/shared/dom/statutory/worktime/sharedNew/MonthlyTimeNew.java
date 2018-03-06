/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

/**
 * The Class MonthlyTimeNew.
 */
@Getter
// 月単位.
public class MonthlyTimeNew extends DomainObject {

	/** The monthly. */
	/** 月度. */
	private MonthlyNew monthly;

	/** The monthly time. */
	/** 月間時間. */
	private MonthlyEstimateTime monthlyTime;

	/**
	 * Instantiates a new monthly time new.
	 *
	 * @param memento
	 *            the memento
	 */
	public MonthlyTimeNew(MonthlyTimeNew memento) {
		this.monthly = memento.getMonthly();
		this.monthlyTime = memento.getMonthlyTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(MonthlyTimeNewSetMemento memento) {
		memento.setMonthlyTime(this.monthlyTime);
		memento.setMonthlyTime(this.monthlyTime);
	}
}
