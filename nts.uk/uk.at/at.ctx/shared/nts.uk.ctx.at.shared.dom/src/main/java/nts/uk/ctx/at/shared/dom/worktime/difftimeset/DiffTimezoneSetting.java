/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;

/**
 * The Class DiffTimezoneSetting.
 */
// 時差勤務時間帯設定
@Getter
public class DiffTimezoneSetting extends DomainObject{

	private static final Integer MIN_WORK_NO = 2;

	private static final Integer ZERO = 1;
	
	/** The employment timezone. */
	// 就業時間帯
	private List<EmTimeZoneSet> employmentTimezones;

	/** The OT timezone. */
	// 残業時間帯
	private List<DiffTimeOTTimezoneSet> oTTimezones;

	/**
	 * Instantiates a new diff timezone setting.
	 *
	 * @param memento the memento
	 */
	public DiffTimezoneSetting(DiffTimezoneSettingGetMemento memento) {
		this.employmentTimezones = memento.getEmploymentTimezones();
		this.oTTimezones = memento.getOTTimezones();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DiffTimezoneSettingSetMemento memento) {
		memento.setEmploymentTimezones(this.employmentTimezones);
		memento.setOTTimezones(this.oTTimezones);
	}
	
	@Override
	public void validate() {
		super.validate();
		
		// Validate employmentTimezones
		EmTimeZoneSet firstEmTimeZoneSet = this.employmentTimezones.get(ZERO);
		int countEmTimeZoneSet = ZERO;
		for (EmTimeZoneSet item : this.employmentTimezones) {
			if (item.getTimezone().getStart().equals(firstEmTimeZoneSet.getTimezone().getStart())
					&& item.getTimezone().getEnd().equals(firstEmTimeZoneSet.getTimezone().getEnd())) {
				countEmTimeZoneSet++;
			}
		}
		if (countEmTimeZoneSet >= MIN_WORK_NO) {
			throw new BusinessException("Msg_515");
		}

		// validate oTTimezones

		DiffTimeOTTimezoneSet firstDiffTimeOTTimezoneSet = this.oTTimezones.get(ZERO);
		int countOTTimezones = ZERO;
		for (DiffTimeOTTimezoneSet item : this.oTTimezones) {
			if (item.getTimezone().getStart().equals(firstDiffTimeOTTimezoneSet.getTimezone().getStart())
					&& item.getTimezone().getEnd().equals(firstDiffTimeOTTimezoneSet.getTimezone().getEnd())) {
				countOTTimezones++;
			}
		}
		if (countOTTimezones >= MIN_WORK_NO) {
			throw new BusinessException("Msg_515");
		}
	}
}
