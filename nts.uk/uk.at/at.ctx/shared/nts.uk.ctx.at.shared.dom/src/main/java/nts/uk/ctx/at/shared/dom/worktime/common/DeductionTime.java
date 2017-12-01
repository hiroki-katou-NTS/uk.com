/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DeductionTime.
 */
@Getter
// 控除時間帯(丸め付き)
public class DeductionTime extends DomainObject {

	/** The start. */
	// 開始
	private TimeWithDayAttr start;

	/** The end. */
	// 終了
	private TimeWithDayAttr end;

	/**
	 * Instantiates a new deduction time.
	 *
	 * @param memento the memento
	 */
	public DeductionTime(DeductionTimeGetMemento memento){
		this.start = memento.getStart();
		this.end = memento.getEnd();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DeductionTimeSetMemento memento){
		memento.setStart(this.start);
		memento.setEnd(this.end);
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		
		if (this.start.greaterThanOrEqualTo(this.end)) {
			throw new BusinessException("Msg_770");
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return start + "," + end;
	}
	
}
