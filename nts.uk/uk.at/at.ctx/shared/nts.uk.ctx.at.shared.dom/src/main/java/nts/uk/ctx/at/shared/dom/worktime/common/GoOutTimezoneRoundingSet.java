/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class GoOutTimezoneRoundingSet.
 */
//時間帯別外出丸め設定
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoOutTimezoneRoundingSet extends WorkTimeDomainObject implements Cloneable{

	/** The pub hol work timezone. */
	// 休日出勤時間帯
	private GoOutTypeRoundingSet pubHolWorkTimezone;

	/** The work timezone. */
	// 就業時間帯
	private GoOutTypeRoundingSet workTimezone;

	/** The ottimezone. */
	// 残業時間帯
	private GoOutTypeRoundingSet ottimezone;

	/**
	 * Instantiates a new go out timezone rounding set.
	 *
	 * @param memento
	 *            the memento
	 */
	public GoOutTimezoneRoundingSet(GoOutTimezoneRoundingSetGetMemento memento) {
		this.pubHolWorkTimezone = memento.getPubHolWorkTimezone();
		this.workTimezone = memento.getWorkTimezone();
		this.ottimezone = memento.getOttimezone();
	}

	/**
	 * Save to mememto.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMememto(GoOutTimezoneRoundingSetSetMemento memento) {
		memento.setPubHolWorkTimezone(this.pubHolWorkTimezone);
		memento.setWorkTimezone(this.workTimezone);
		memento.setOttimezone(this.ottimezone);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, GoOutTimezoneRoundingSet oldDomain) {
		this.pubHolWorkTimezone.correctData(screenMode, oldDomain.getPubHolWorkTimezone());
		this.workTimezone.correctData(screenMode, oldDomain.getWorkTimezone());
		this.ottimezone.correctData(screenMode, oldDomain.getOttimezone());
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		this.pubHolWorkTimezone.correctDefaultData(screenMode);
		this.workTimezone.correctDefaultData(screenMode);
		this.ottimezone.correctDefaultData(screenMode);
	}
	
	@Override
	public GoOutTimezoneRoundingSet clone() {
		GoOutTimezoneRoundingSet cloned = new GoOutTimezoneRoundingSet();
		try {
			cloned.pubHolWorkTimezone = this.pubHolWorkTimezone.clone();
			cloned.workTimezone = this.workTimezone.clone();
			cloned.ottimezone = this.ottimezone.clone();
		}
		catch (Exception e){
			throw new RuntimeException("GoOutTimezoneRoundingSet clone error.");
		}
		return cloned;
	}

	/**
	 * 丸め設定を取得する
	 * @param actualAtr 実働時間帯区分
	 * @param reason 外出理由
	 * @param dedAtr 控除区分
	 * @param reverse 逆丸め用
	 * @return 時間丸め設定
	 */
	public TimeRoundingSetting getRoundingSet(ActualWorkTimeSheetAtr actualAtr, GoingOutReason reason, DeductionAtr dedAtr, TimeRoundingSetting reverse) {
		if(actualAtr.isWithinWorkTime()) {
			return this.workTimezone.getRoundingSet(reason, dedAtr, reverse);
		}
		if(actualAtr.isHolidayWork()) {
			return this.pubHolWorkTimezone.getRoundingSet(reason, dedAtr, reverse);
		}
		return this.ottimezone.getRoundingSet(reason, dedAtr, reverse);
	}
}
