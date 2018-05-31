/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class DiffTimeHalfDayWorkTimezone.
 */
// 時差勤務の平日出勤用勤務時間帯
@Getter
public class DiffTimeHalfDayWorkTimezone extends WorkTimeDomainObject {

	/** The rest timezone. */
	// 休憩時間帯
	private DiffTimeRestTimezone restTimezone;

	/** The work timezone. */
	// 勤務時間帯
	private DiffTimezoneSetting workTimezone;

	/** The am pm atr. */
	// 午前午後区分
	private AmPmAtr amPmAtr;

	/**
	 * Instantiates a new diff time half day work timezone.
	 *
	 * @param memento the memento
	 */
	public DiffTimeHalfDayWorkTimezone(DiffTimeHalfDayGetMemento memento) {
		this.restTimezone = memento.getRestTimezone();
		this.workTimezone = memento.getWorkTimezone();
		this.amPmAtr = memento.getAmPmAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DiffTimeHalfDaySetMemento memento) {
		memento.setRestTimezone(this.restTimezone);
		memento.setWorkTimezone(this.workTimezone);
		memento.setAmPmAtr(this.amPmAtr);
	}
	
	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param diffTimeWorkSet the diff time work set
	 * @param other the other
	 */
	public void correctData(ScreenMode screenMode, DiffTimeWorkSetting diffTimeWorkSet,
			DiffTimeHalfDayWorkTimezone other) {
		switch (screenMode) {
		case SIMPLE:
			this.restoreSimpleMode(other);
			break;
		case DETAIL:
			this.restoreDetailMode(diffTimeWorkSet, other);;
			break;
		default:
			throw new RuntimeException("ScreenMode not found.");
		}
	}
	
	public void correctDefaultData(ScreenMode screenMode) {
		if (screenMode.equals(ScreenMode.SIMPLE) && this.getAmPmAtr() != AmPmAtr.ONE_DAY) {
			this.restTimezone.correctDefaultData();
			this.workTimezone.correctDefaultData();
		}
	}

	/**
	 * Correct default data.
	 */
	public void correctDefaultData() {
		this.workTimezone.correctDefaultData();
	}
	
	/**
	 * Restore simple mode.
	 *
	 * @param other the other
	 */
	private void restoreSimpleMode(DiffTimeHalfDayWorkTimezone other) {
		if (other.getAmPmAtr() != AmPmAtr.ONE_DAY) {
			this.workTimezone.correctData(other.getWorkTimezone());
			this.restTimezone.correctData(other.getRestTimezone());
		}
	}
	
	/**
	 * Restore detail mode.
	 *
	 * @param diffTimeWorkSet the diff time work set
	 * @param other the other
	 */
	private void restoreDetailMode(DiffTimeWorkSetting diffTimeWorkSet, DiffTimeHalfDayWorkTimezone other) {
		// restore data of dayAtr = AM, PM
		if (!diffTimeWorkSet.isUseHalfDayShift() && other.getAmPmAtr() != AmPmAtr.ONE_DAY) {
			this.workTimezone.correctData(other.getWorkTimezone());
			this.restTimezone.correctData(other.getRestTimezone());
		}
	}
	
	@Override
	public void validate() {
		
		//validate Msg_770 for list work
		this.workTimezone.getEmploymentTimezones().stream().forEach(item->{
			item.getTimezone().validateRange("KMK003_86");
		});
		
		// validate Msg_770 for list ot
		this.workTimezone.getOTTimezones().stream().forEach(item -> {
			item.getTimezone().validateRange("KMK003_89");
		});
		
		// validate Msg_770 for rest time
		this.restTimezone.getRestTimezones().stream().forEach(item -> {
			item.validateRange("KMK003_20");
		});

		// validate Msg_515 for rest time
		this.restTimezone.validOverlap("KMK003_20");
		super.validate();
	}

	public boolean restInWork() {
		for (DiffTimeDeductTimezone item : this.getRestTimezone().getRestTimezones()) {
			List<EmTimeZoneSet> lstWork = this.getWorkTimezone().getEmploymentTimezones().stream()
					.filter(work -> work.checkRestTime(item)).collect(Collectors.toList());
			List<DiffTimeOTTimezoneSet> lstOt = this.getWorkTimezone().getOTTimezones().stream()
					.filter(ot -> ot.checkRestTime(item)).collect(Collectors.toList());
			if (CollectionUtil.isEmpty(lstWork) && CollectionUtil.isEmpty(lstOt)) {
				return true;
			}
		}
		return false;
	}
}
