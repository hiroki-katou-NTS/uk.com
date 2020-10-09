/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowOTSet.
 */
//流動残業設定
@Getter
@NoArgsConstructor
public class FlowOTSet extends WorkTimeDomainObject implements Cloneable{

	/** The fixed change atr. */
	//所定変動区分
	private FixedChangeAtr fixedChangeAtr;

	/**
	 * Instantiates a new flow OT set.
	 *
	 * @param memento the memento
	 */
	public FlowOTSet(FlowOTGetMemento memento) {
		this.fixedChangeAtr = memento.getFixedChangeAtr();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowOTSetMemento memento) {
		memento.setFixedChangeAtr(this.fixedChangeAtr);
	}
	
	/**
	 * Restore data.
	 *
	 * @param other the other
	 */
	public void correctData(FlowOTSet other) {
		this.fixedChangeAtr = other.getFixedChangeAtr();
	}
	
	@Override
	public FlowOTSet clone() {
		FlowOTSet cloned = new FlowOTSet();
		try {
			cloned.fixedChangeAtr = FixedChangeAtr.valueOf(this.fixedChangeAtr.value);
		}
		catch (Exception e){
			throw new RuntimeException("FlowOTSet clone error.");
		}
		return cloned;
	}
}
