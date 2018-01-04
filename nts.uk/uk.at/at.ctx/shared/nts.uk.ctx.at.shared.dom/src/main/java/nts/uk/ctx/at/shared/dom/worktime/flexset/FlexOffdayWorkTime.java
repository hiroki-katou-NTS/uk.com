/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;

/**
 * The Class FlexOffdayWorkTime.
 */
@Getter
// フレックス勤務の休日出勤用勤務時間帯
public class FlexOffdayWorkTime extends DomainObject {

	/** The lst work timezone. */
	// 勤務時間帯
	private List<HDWorkTimeSheetSetting> lstWorkTimezone;

	/** The rest timezone. */
	// 休憩時間帯
	private FlowWorkRestTimezone restTimezone;

	/**
	 * Instantiates a new flex offday work time.
	 *
	 * @param memento the memento
	 */
	public FlexOffdayWorkTime(FlexOffdayWorkTimeGetMemento memento) {
		this.lstWorkTimezone = memento.getLstWorkTimezone();
		this.restTimezone = memento.getRestTimezone();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlexOffdayWorkTimeSetMemento memento){
		memento.setLstWorkTimezone(this.lstWorkTimezone);
		memento.setRestTimezone(this.restTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		if (this.restTimezone.isFixRestTime() && !this.isRestTzInHolidayTz()) {
			throw new BusinessException("Msg_756");
		}
	}

	/**
	 * Checks if is rest tz in hd wtz.
	 *
	 * @return true, if is rest tz in hd wtz
	 */
	private boolean isRestTzInHolidayTz() {
		if (this.restTimezone.getFixedRestTimezone().getTimezones().isEmpty()) {
			return true;
		}
		return this.restTimezone.getFixedRestTimezone().getTimezones().stream().anyMatch(
				resTz -> this.lstWorkTimezone.stream().anyMatch(hdWtz -> resTz.isBetweenOrEqual(hdWtz.getTimezone())));
	}
}
