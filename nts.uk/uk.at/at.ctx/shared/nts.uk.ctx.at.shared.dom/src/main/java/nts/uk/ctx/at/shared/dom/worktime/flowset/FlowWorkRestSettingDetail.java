/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowWorkRestSettingDetail.
 */
// 流動勤務の休憩設定詳細
@Getter
public class FlowWorkRestSettingDetail extends DomainObject {

	/** The flow rest setting. */
	// 流動休憩設定
	private FlowRestSet flowRestSetting;

	/** The flow fixed rest setting. */
	// 流動固定休憩設定
	private FlowFixedRestSet flowFixedRestSetting;

	/** The use plural work rest time. */
	// 複数回勤務の間を休憩時間として扱う
	private boolean usePluralWorkRestTime;

	/**
	 * Instantiates a new flow work rest setting detail.
	 *
	 * @param memento the memento
	 */
	public FlowWorkRestSettingDetail(FlowWorkRestSettingDetailGetMemento memento) {
		this.flowRestSetting = memento.getFlowRestSetting();
		this.flowFixedRestSetting = memento.getFlowFixedRestSetting();
		this.usePluralWorkRestTime = memento.getUsePluralWorkRestTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowWorkRestSettingDetailSetMemento memento) {
		memento.setFlowRestSetting(this.flowRestSetting);
		memento.setFlowFixedRestSetting(this.flowFixedRestSetting);
		memento.setUsePluralWorkRestTime(this.usePluralWorkRestTime);
	}
}
