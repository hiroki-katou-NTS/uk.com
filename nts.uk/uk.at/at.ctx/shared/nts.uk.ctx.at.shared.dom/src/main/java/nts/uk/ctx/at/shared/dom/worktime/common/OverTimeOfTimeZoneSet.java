/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class OverTimeOfTimeZoneSet.
 */
// 残業時間の時間帯設定
@Getter
public class OverTimeOfTimeZoneSet extends WorkTimeDomainObject {

	/** The work timezone no. */
	// 就業時間帯NO
	private EmTimezoneNo workTimezoneNo;

	/** The restraint time use. */
	// 拘束時間として扱う
	private boolean restraintTimeUse;

	/** The early OT use. */
	// 早出残業として扱う
	private boolean earlyOTUse;

	// 時間帯
	private TimeZoneRounding timezone;

	/** The ot frame no. */
	// 残業枠NO
	private OTFrameNo otFrameNo;

	/** The legal O tframe no. */
	// 法定内残業枠NO
	private OTFrameNo legalOTframeNo;

	/** The settlement order. */
	// 精算順序
	private SettlementOrder settlementOrder;
	
	
	/**
	 * Instantiates a new over time of time zone set.
	 *
	 * @param memento the memento
	 */
	public OverTimeOfTimeZoneSet(OverTimeOfTimeZoneSetGetMemento memento) {
		this.workTimezoneNo = memento.getWorkTimezoneNo();
		this.restraintTimeUse = memento.getRestraintTimeUse();
		this.earlyOTUse = memento.getEarlyOTUse();
		this.timezone = memento.getTimezone();
		this.otFrameNo = memento.getOTFrameNo();
		this.legalOTframeNo = memento.getLegalOTframeNo();
		this.settlementOrder = memento.getSettlementOrder();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OverTimeOfTimeZoneSetSetMemento memento){
		memento.setWorkTimezoneNo(this.workTimezoneNo);
		memento.setRestraintTimeUse(this.restraintTimeUse);
		memento.setEarlyOTUse(this.earlyOTUse);
		memento.setTimezone(this.timezone);
		memento.setOTFrameNo(this.otFrameNo);
		memento.setLegalOTframeNo(this.legalOTframeNo);
		memento.setSettlementOrder(this.settlementOrder);
	}
}
