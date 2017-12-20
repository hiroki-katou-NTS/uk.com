/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowWorkRestTimezone.
 */
//流動勤務の休憩時間帯
@Getter
public class FlowWorkRestTimezone extends DomainObject{

	/** The fix rest time. */
	//休憩時間帯を固定にする
	private boolean fixRestTime;
	
	/** The fixed rest timezone. */
	//固定休憩時間帯
	private TimezoneOfFixedRestTimeSet fixedRestTimezone;
	
	/** The flow rest timezone. */
	//流動休憩時間帯
	private FlowRestTimezone flowRestTimezone;
	
	/**
	 * Instantiates a new flow work rest timezone.
	 *
	 * @param memento the memento
	 */
	public FlowWorkRestTimezone(FlowWorkRestTimezoneGetMemento memento) {
		this.fixRestTime = memento.getFixRestTime();
		this.fixedRestTimezone = memento.getFixedRestTimezone();
		this.flowRestTimezone = memento.getFlowRestTimezone();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlowWorkRestTimezoneSetMemento memento){
		memento.setFixRestTime(this.fixRestTime);
		memento.setFixedRestTimezone(this.fixedRestTimezone);
		memento.setFlowRestTimezone(this.flowRestTimezone);
	}
	
}
