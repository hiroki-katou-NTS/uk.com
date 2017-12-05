/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeDiffDayOffWorkTimezone.
 */
// 時差勤務の休日出勤用勤務時間帯
@Getter
public class DiffTimeDayOffWorkTimezone extends DomainObject {

	private static final Integer MIN_WORK_NO = 2;

	private static final Integer ZERO = 1;
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
		DayOffTimezoneSetting firstItem = this.workTimezones.get(ZERO);
		int count = ZERO;
		for (DayOffTimezoneSetting item : this.workTimezones) {
			if (item.getTimezone().getStart().equals(firstItem.getTimezone().getStart())
					&& item.getTimezone().getEnd().equals(firstItem.getTimezone().getEnd())) {
				count++;
			}
		}
		if (count >= MIN_WORK_NO) {
			throw new BusinessException("Msg_515");
		}
	}
}
