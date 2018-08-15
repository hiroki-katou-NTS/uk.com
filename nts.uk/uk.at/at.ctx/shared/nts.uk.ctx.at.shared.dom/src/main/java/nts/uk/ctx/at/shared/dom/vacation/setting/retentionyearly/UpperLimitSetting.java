/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class UpperLimitSetting. 上限設定
 */
@Getter
public class UpperLimitSetting extends DomainObject {
	
	/** The retention years amount. */
	//保持年数
	private RetentionYearsAmount retentionYearsAmount;
	
	/** The max days cumulation. */
	//上限日数
	private MaxDaysRetention maxDaysCumulation;
	
	/**
	 * Instantiates a new upper limit setting.
	 *
	 * @param memento the memento
	 */
	public UpperLimitSetting(UpperLimitSettingGetMemento memento) {
		this.maxDaysCumulation = memento.getMaxDaysCumulation();
		this.retentionYearsAmount = memento.getRetentionYearsAmount();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(UpperLimitSettingSetMemento memento) {
		memento.setMaxDaysRetention(this.maxDaysCumulation);
		memento.setRetentionYearsAmount(this.retentionYearsAmount);
	}
}
