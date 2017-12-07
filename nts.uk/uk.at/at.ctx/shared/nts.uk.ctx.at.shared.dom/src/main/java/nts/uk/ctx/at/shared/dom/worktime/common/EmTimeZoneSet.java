/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmTimeZoneSet.
 */
// 就業時間の時間帯設定
@Getter
public class EmTimeZoneSet extends DomainObject {

	/** The Employment time frame no. */
	//就業時間枠NO
	private EmTimeFrameNo employmentTimeFrameNo;
	
	/** The timezone. */
	//時間帯
	private TimeZoneRounding timezone;

	/**
	 * Instantiates a new em time zone set.
	 *
	 * @param memento the memento
	 */
	public EmTimeZoneSet(EmTimeZoneSetGetMemento memento) {
		this.employmentTimeFrameNo = memento.getEmploymentTimeFrameNo();
		this.timezone = memento.getTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmTimeZoneSetSetMemento memento) {
		memento.setEmploymentTimeFrameNo(this.employmentTimeFrameNo);
		memento.setTimezone(this.timezone);
	}
	
	@Override
	public void validate()
	{
		super.validate();
		
		if (this.timezone.getStart().v() >= this.timezone.getEnd().v()) {
			throw new BusinessException("Msg_770");
		}
		//TODO
		
	}
}
