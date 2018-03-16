/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
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
	private List<HDWorkTimeSheetSetting> workTimezones;

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
		this.validateRestInWork();
	}

	private void checkOverlap() {
		// validate overlap list work time
		if (!CollectionUtil.isEmpty(this.workTimezones)) {
			for (int i = 0; i < this.workTimezones.size(); i++) {
				for (int j = i + 1; j < this.workTimezones.size(); j++) {
					if (this.workTimezones.get(i).getTimezone().isOverlap(this.workTimezones.get(j).getTimezone())) {
						this.bundledBusinessExceptions.addMessage("Msg_515","KMK003_90");
					}
				}
			}
		}

		// validate overlap list rest time
		if (!CollectionUtil.isEmpty(this.restTimezone.getRestTimezones())) {
			for (int i = 0; i < this.restTimezone.getRestTimezones().size(); i++) {
				for (int j = i + 1; j < this.restTimezone.getRestTimezones().size(); j++) {
					if (this.restTimezone.getRestTimezones().get(i)
							.isOverlap(this.restTimezone.getRestTimezones().get(j))) {
						this.bundledBusinessExceptions.addMessage("Msg_515","KMK003_21");
					}
				}
			}
		}
	}
	
	private void validateRestInWork() {
		this.restTimezone.getRestTimezones().stream().forEach(rest -> {
			List<HDWorkTimeSheetSetting> workTime = this.workTimezones.stream()
					.filter(work -> (work.getTimezone().getStart().v() <= rest.getStart().v())
							&& (work.getTimezone().getEnd().v() >= rest.getEnd().v()))
					.collect(Collectors.toList());
			if (CollectionUtil.isEmpty(workTime)) {
				this.bundledBusinessExceptions.addMessage("Msg_756");
			}
		});
	}
}
