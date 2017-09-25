/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AutoCalFlexOvertimeSetting.
 */
// フレックス超過時間の自動計算設定

/**
 * Gets the flex ot night time.
 *
 * @return the flex ot night time
 */
@Getter
public class AutoCalFlexOvertimeSetting extends DomainObject {

	/** The flex ot time. */
	// フレックス超過時間
	private AutoCalSetting flexOtTime;

	/** The flex ot night time. */
	// フレックス超過深夜時間
	private AutoCalSetting flexOtNightTime;

	/**
	 * Instantiates a new auto cal flex overtime setting.
	 *
	 * @param flexOtTimeSet the flex ot time set
	 * @param flexOtNightTimeSet the flex ot night time set
	 */
	public AutoCalFlexOvertimeSetting(AutoCalFlexOvertimeSettingGetMemento memento) {
		super();
		this.flexOtTime = memento.getFlexOtTime();
		this.flexOtNightTime = memento.getFlexOtNightTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AutoCalFlexOvertimeSettingSetMemento memento) {
		memento.setFlexOtNightTime(this.flexOtNightTime);
		memento.setFlexOtTime(this.flexOtTime);
	}

}
