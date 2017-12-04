/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;

/**
 * The Class DiffTimezoneSetting.
 */
// 時差勤務時間帯設定
@Getter
public class DiffTimezoneSetting {

	/** The employment timezone. */
	// 就業時間帯
	private List<EmTimeZoneSet> employmentTimezone;

	/** The OT timezone. */
	// 残業時間帯
	private List<DiffTimeOTTimezoneSet> OTTimezone;

	/**
	 * Instantiates a new diff timezone setting.
	 *
	 * @param memento the memento
	 */
	public DiffTimezoneSetting(DiffTimezoneSettingGetMemento memento) {
		this.employmentTimezone = memento.getEmploymentTimezone();
		this.OTTimezone = memento.getOTTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DiffTimezoneSettingSetMemento memento) {
		memento.setEmploymentTimezone(this.employmentTimezone);
		memento.setOTTimezone(this.OTTimezone);
	}
}
