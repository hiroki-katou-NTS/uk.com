/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class FlowOffdayWorkTimezone.
 */
// 流動勤務の休日出勤用勤務時間帯
@Getter
public class FlowOffdayWorkTimezone extends WorkTimeDomainObject {

	/** The rest time zone. */
	// 休憩時間帯
	private FlowWorkRestTimezone restTimeZone;

	/** The lst work timezone. */
	// 勤務時間帯
	private List<FlowWorkHolidayTimeZone> lstWorkTimezone;

	/**
	 * Instantiates a new flow offday work timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowOffdayWorkTimezone(FlowOffdayWtzGetMemento memento) {
		this.restTimeZone = memento.getRestTimeZone();
		this.lstWorkTimezone = memento.getLstWorkTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlowOffdayWtzSetMemento memento) {
		memento.setRestTimeZone(this.restTimeZone);
		memento.setLstWorkTimezone(this.lstWorkTimezone);
	}

	/**
	 * Restore data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void restoreData(ScreenMode screenMode, FlowOffdayWorkTimezone oldDomain) {
		this.restTimeZone.restoreData(screenMode, oldDomain.getRestTimeZone());
	}

	/**
	 * Restore default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void restoreDefaultData(ScreenMode screenMode) {
		this.restTimeZone.restoreDefaultData(screenMode);
	}
}
