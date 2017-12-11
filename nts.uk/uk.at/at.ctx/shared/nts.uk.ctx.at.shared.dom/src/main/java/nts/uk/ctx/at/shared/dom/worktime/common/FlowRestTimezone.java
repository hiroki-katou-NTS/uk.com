/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowRestTimezone.
 */
// 流動休憩時間帯
@Getter
public class FlowRestTimezone extends DomainObject {

	/** The flow rest set. */
	// 流動休憩設定
	private List<FlowRestSetting> flowRestSets;

	/** The use here after rest set. */
	// 設定以降の休憩を使用する
	private boolean useHereAfterRestSet;

	/** The here after rest set. */
	// 設定以降の休憩設定
	private FlowRestSetting hereAfterRestSet;

	/**
	 * Instantiates a new flow rest timezone.
	 *
	 * @param memento the memento
	 */
	public FlowRestTimezone(FlowRestTimezoneGetMemento memento) {
		this.flowRestSets = memento.getFlowRestSet();
		this.useHereAfterRestSet = memento.getUseHereAfterRestSet();
		this.hereAfterRestSet = memento.getHereAfterRestSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowRestTimezoneSetMemento memento) {
		memento.setFlowRestSet(this.flowRestSets);
		memento.setUseHereAfterRestSet(this.useHereAfterRestSet);
		memento.setHereAfterRestSet(this.hereAfterRestSet);
	}
}
