/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;


import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class FlowHalfDayWorkTimezone.
 */
// 流動勤務の平日出勤用勤務時間帯
@Getter
@NoArgsConstructor
public class FlowHalfDayWorkTimezone extends WorkTimeDomainObject implements Cloneable{

	/** The work time zone. */
	// 勤務時間帯
	private FlowWorkTimezoneSetting workTimeZone;

	/** The rest timezone. */
	// 休憩時間帯
	private FlowWorkRestTimezone restTimezone;

	/**
	 * Instantiates a new flow half day work timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowHalfDayWorkTimezone(FlowHalfDayWtzGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimeZone = memento.getWorkTimeZone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlowHalfDayWtzSetMemento memento) {
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimeZone(this.workTimeZone);
	}

	/**
	 * Restore data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, FlowHalfDayWorkTimezone oldDomain) {
		this.restTimezone.correctData(screenMode, oldDomain.getRestTimezone());
	}

	/**
	 * Restore default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		this.restTimezone.correctDefaultData(screenMode);
	}

	/**
	 * Correct default data.
	 */
	public void correctDefaultData() {
		this.workTimeZone.correctDefaultData();
	}
	
	@Override
	public FlowHalfDayWorkTimezone clone() {
		FlowHalfDayWorkTimezone cloned = new FlowHalfDayWorkTimezone();
		try {
			cloned.workTimeZone = this.workTimeZone.clone();
			cloned.restTimezone = this.restTimezone.clone();
		}
		catch (Exception e){
			throw new RuntimeException("FlowHalfDayWorkTimezone clone error.");
		}
		return cloned;
	}
}
