/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class FixedWorkCalcSetting.
 */
@Getter
// 固定勤務�計算設�
public class FixedWorkCalcSetting extends WorkTimeDomainObject {

	/** The exceeded pred add vacation calc. */
	// 休暇�算時間が所定を趁�した場合�計�
	private ExceededPredAddVacationCalc exceededPredAddVacationCalc;

	/** The over time calc no break. */
	// 休�未取得時の残業計�
	private OverTimeCalcNoBreak overTimeCalcNoBreak;

	/**
	 * Instantiates a new fixed work calc setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public FixedWorkCalcSetting(FixedWorkCalcSettingGetMemento memento) {
		this.exceededPredAddVacationCalc = memento.getExceededPredAddVacationCalc();
		this.overTimeCalcNoBreak = memento.getOverTimeCalcNoBreak();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FixedWorkCalcSettingSetMemento memento) {
		memento.setExceededPredAddVacationCalc(this.exceededPredAddVacationCalc);
		memento.setOverTimeCalcNoBreak(this.overTimeCalcNoBreak);
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, Optional<FixedWorkCalcSetting> oldDomain) {
		if (oldDomain.isPresent()) {
			this.exceededPredAddVacationCalc.correctData(screenMode, oldDomain.get().getExceededPredAddVacationCalc());
			this.overTimeCalcNoBreak.correctData(screenMode, oldDomain.get().getOverTimeCalcNoBreak());
		} else {
			this.correctDefaultData(screenMode);
		}
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		this.exceededPredAddVacationCalc.correctDefaultData(screenMode);
		this.overTimeCalcNoBreak.correctDefaultData(screenMode);
	}
}
