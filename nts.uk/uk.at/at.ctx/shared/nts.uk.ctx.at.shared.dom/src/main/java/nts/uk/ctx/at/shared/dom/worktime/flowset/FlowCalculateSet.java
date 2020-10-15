/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowCalculateSet.
 */
// 流動計算設定
@Getter
@NoArgsConstructor
public class FlowCalculateSet extends WorkTimeDomainObject implements Cloneable{
	
	/** The calc start time set. */
	// 計算開始時刻を決める設定
	private PrePlanWorkTimeCalcMethod calcStartTimeSet;

	/**
	 * Instantiates a new flow calculate set.
	 *
	 * @param memento the memento
	 */
	public FlowCalculateSet(FlowCalculateGetMemento memento) {
		this.calcStartTimeSet = memento.getCalcStartTimeSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowCalculateSetMemento memento) {
		memento.setCalcStartTimeSet(this.calcStartTimeSet);
	}
	
	@Override
	public FlowCalculateSet clone() {
		FlowCalculateSet cloned = new FlowCalculateSet();
		try {
			cloned.calcStartTimeSet = PrePlanWorkTimeCalcMethod.valueOf(this.calcStartTimeSet.value);
		}
		catch (Exception e){
			throw new RuntimeException("FlowCalculateSet clone error.");
		}
		return cloned;
	}
}
