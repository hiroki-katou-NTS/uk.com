/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class TimeDiffDayOffWorkTimezone.
 */
// 時差勤務の休日出勤用勤務時間帯
@Getter
public class DiffTimeDayOffWorkTimezone extends WorkTimeDomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private DiffTimeRestTimezone restTimezone;

	/** The work timezones. */
	// 勤務時間帯
	private List<DayOffTimezoneSetting> workTimezones;

	/**
	 * Instantiates a new diff time day off work timezone.
	 *
	 * @param memento
	 *            the memento
	 */
	public DiffTimeDayOffWorkTimezone(DiffTimeDayOffWorkTimezoneGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimezones = memento.getWorkTimezones();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DiffTimeDayOffWorkTimezoneSetMemento memento) {
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimezones(this.workTimezones);
	}

	@Override
	public void validate() {
		super.validate();
		this.checkOverlap();
	}

	private void checkOverlap() {
		if (!CollectionUtil.isEmpty(this.workTimezones)) {
			for (int i = 0; i < this.workTimezones.size(); i++) {
				for (int j = i + 1; j < this.workTimezones.size(); j++) {
					if (this.workTimezones.get(i).getTimezone().isOverlap(this.workTimezones.get(j).getTimezone())) {
						throw new BusinessException("Msg_515");
					}
				}
			}
		}
	}
}
