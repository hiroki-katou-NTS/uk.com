/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class FlowWorkRestTimezone.
 */
//流動勤務の休憩時間帯
@Getter
public class FlowWorkRestTimezone extends WorkTimeDomainObject {

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
	 * Constructor
	 * @param fixRestTime  The fix rest time
	 * @param fixedRestTimezone The fixed rest timezone
	 * @param flowRestTimezone The flow rest timezone
	 */
	public FlowWorkRestTimezone(boolean fixRestTime, TimezoneOfFixedRestTimeSet fixedRestTimezone,
			FlowRestTimezone flowRestTimezone) {
		super();
		this.fixRestTime = fixRestTime;
		this.fixedRestTimezone = fixedRestTimezone;
		this.flowRestTimezone = flowRestTimezone;
	}
	
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

	@Override
	public void validate() {
		this.fixedRestTimezone.checkOverlap("#KMK003_20");
		super.validate();
	}
	
}
