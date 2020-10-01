/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class FlowOffdayWorkTimezone.
 */
// 流動勤務の休日出勤用勤務時間帯
@Getter
@NoArgsConstructor
public class FlowOffdayWorkTimezone extends WorkTimeDomainObject implements Cloneable{

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
	public void correctData(ScreenMode screenMode, FlowOffdayWorkTimezone oldDomain) {
		this.restTimeZone.correctData(screenMode, oldDomain.getRestTimeZone());
	}

	/**
	 * Restore default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		this.restTimeZone.correctDefaultData(screenMode);
	}
	
	@Override
	public FlowOffdayWorkTimezone clone() {
		FlowOffdayWorkTimezone cloned = new FlowOffdayWorkTimezone();
		try {
			cloned.restTimeZone = this.restTimeZone.clone();
			cloned.lstWorkTimezone = this.lstWorkTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
		}
		catch (Exception e){
			throw new RuntimeException("FlowOffdayWorkTimezone clone error.");
		}
		return cloned;
	}
}
