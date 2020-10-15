/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class FlowWorkDedicateSetting.
 */
//流動勤務専用設定
@Getter
@NoArgsConstructor
public class FlowWorkDedicateSetting extends WorkTimeDomainObject implements Cloneable{

	/** The overtime setting. */
	//残業設定
	private FlowOTSet overtimeSetting;
	
	/** The calculate setting. */
	//計算設定
	private FlowCalculateSet calculateSetting;

	/**
	 * Instantiates a new flow work dedicate setting.
	 *
	 * @param memento the memento
	 */
	public FlowWorkDedicateSetting(FlWorkDedGetMemento memento) {
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
	
	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param other the other
	 */
	public void correctData(ScreenMode screenMode, FlowWorkDedicateSetting other) {
		switch (screenMode) {
		case SIMPLE:
			this.overtimeSetting.correctData(other.getOvertimeSetting());
			break;
		case DETAIL:
			break;
		default:
			throw new RuntimeException("Screen Mode not found.");
		}
	}
	
	@Override
	public FlowWorkDedicateSetting clone() {
		FlowWorkDedicateSetting cloned = new FlowWorkDedicateSetting();
		try {
			cloned.overtimeSetting = this.overtimeSetting.clone();
			cloned.calculateSetting = this.calculateSetting.clone();
		}
		catch (Exception e){
			throw new RuntimeException("FlowWorkDedicateSetting clone error.");
		}
		return cloned;
	}
}
