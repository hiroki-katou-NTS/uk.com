/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowWorkDedicateSetting.
 */
//流動勤務専用設定
@Getter
public class FlWorkDedSetting extends DomainObject {

	/** The overtime setting. */
	//残業設定
	private FlOTSet overtimeSetting;
	
	/** The calculate setting. */
	//計算設定
	private FlCalcSet calculateSetting;

	/**
	 * Instantiates a new flow work dedicate setting.
	 *
	 * @param memento the memento
	 */
	public FlWorkDedSetting(FlWorkDedGetMemento memento) {
		this.overtimeSetting = memento.getOvertimeSetting();
		this.calculateSetting = memento.getCalculateSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlWorkDedSetMemento memento) {
		memento.setOvertimeSetting(this.overtimeSetting);
		memento.setCalculateSetting(this.calculateSetting);
	}

}
