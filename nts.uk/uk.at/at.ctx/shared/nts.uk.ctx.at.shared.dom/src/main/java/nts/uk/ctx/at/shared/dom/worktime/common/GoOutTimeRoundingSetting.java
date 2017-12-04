/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class GoOutTimeRoundingSetting.
 */
//外出時間の丸め設定
@Getter
public class GoOutTimeRoundingSetting extends DomainObject {

	/** The rounding method. */
	//丸め方法
	private GoOutTimeRoundingMethod roundingMethod;
	
	/** The rounding setting. */
	//丸め設定
	private TimeRoundingSetting roundingSetting;
	
	
	/**
	 * Instantiates a new go out time rounding setting.
	 *
	 * @param memento the memento
	 */
	public GoOutTimeRoundingSetting(GoOutTimeRoundingSettingGetMemento memento) {
		this.roundingMethod = memento.getRoundingMethod();
		this.roundingSetting = memento.getRoundingSetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(GoOutTimeRoundingSettingSetMemento memento){
		memento.setRoundingMethod(this.roundingMethod);
		memento.setRoundingSetting(this.roundingSetting);
	}
}
