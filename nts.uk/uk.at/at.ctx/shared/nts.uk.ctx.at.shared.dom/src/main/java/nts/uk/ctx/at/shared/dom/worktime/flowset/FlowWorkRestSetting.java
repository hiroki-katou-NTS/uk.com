/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;

/**
 * The Class FlowWorkRestSetting.
 */
// 流動勤務の休憩設定
@Getter
public class FlowWorkRestSetting extends DomainObject {

	/** The common rest setting. */
	// 共通の休憩設定
	private CommonRestSetting commonRestSetting;

	/** The flow rest setting. */
	// 流動休憩設定
	private FlowWorkRestSettingDetail flowRestSetting;

	/**
	 * Instantiates a new flow work rest setting.
	 *
	 * @param memento the memento
	 */
	public FlowWorkRestSetting(FlowWorkRestSettingGetMemento memento) {
		this.commonRestSetting = memento.getCommonRestSetting();
		this.flowRestSetting = memento.getFlowRestSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowWorkRestSettingSetMemento memento) {
		memento.setCommonRestSetting(this.commonRestSetting);
		memento.setFlowRestSetting(this.flowRestSetting);
	}
}
