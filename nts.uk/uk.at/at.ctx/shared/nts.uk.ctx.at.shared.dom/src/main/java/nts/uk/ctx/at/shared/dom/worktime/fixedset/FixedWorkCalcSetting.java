/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FixedWorkCalcSetting.
 */
@Getter
// 固定勤務の計算設定
public class FixedWorkCalcSetting extends WorkTimeDomainObject {

	/** The exceeded pred add vacation calc. */
	// 休暇加算時間が所定を超過した場合の計算
	private ExceededPredAddVacationCalc exceededPredAddVacationCalc;

	/** The over time calc no break. */
	// 休憩未取得時の残業計算
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
	 * Constructor 
	 */
	public FixedWorkCalcSetting(ExceededPredAddVacationCalc exceededPredAddVacationCalc,
			OverTimeCalcNoBreak overTimeCalcNoBreak) {
		super();
		this.exceededPredAddVacationCalc = exceededPredAddVacationCalc;
		this.overTimeCalcNoBreak = overTimeCalcNoBreak;
	}
	
	
}
