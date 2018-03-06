/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;

/**
 * The Class WeeklyTimeNew.
 */
@Getter
// 週間時間.
public class WeeklyTimeNew extends DomainObject {

	/** The Constant DEFAULT_VALUE. */
	public static final int DEFAULT_VALUE = 0;

	/** The weekly time. */
	/** 週間時間. */
	private WeeklyTime weeklyTime;

	/** The week start. */
	/** 週開始. */
	private WeekStart weekStart;

	/**
	 * Instantiates a new weekly time new.
	 *
	 * @param memento
	 *            the memento
	 */
	public WeeklyTimeNew(WeeklyTimeNew memento) {
		this.weeklyTime = memento.getWeeklyTime();
		this.weekStart = memento.getWeekStart();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WeeklyTimeNewSetMemento memento) {
		memento.setWeeklyTime(this.weeklyTime);
		memento.setWeekStart(this.weekStart);
	}
}
