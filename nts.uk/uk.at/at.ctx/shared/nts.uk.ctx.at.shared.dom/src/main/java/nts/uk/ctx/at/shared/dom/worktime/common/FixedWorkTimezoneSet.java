/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;

/**
 * The Class FixedWorkTimezoneSet.
 */
// 固定勤務時間帯設定
@Getter
public class FixedWorkTimezoneSet extends DomainObject {

	/** The lst working timezone. */
	// 就業時間帯
	private List<EmTimeZoneSet> lstWorkingTimezone;

	/** The lst OT timezone. */
	// 残業時間帯
	private List<OverTimeOfTimeZoneSet> lstOTTimezone;

	/**
	 * Instantiates a new fixed work timezone set.
	 *
	 * @param memento the memento
	 */
	public FixedWorkTimezoneSet(FixedWorkTimezoneSetGetMemento memento) {
		this.lstWorkingTimezone = memento.getLstWorkingTimezone();
		this.lstOTTimezone = memento.getLstOTTimezone();
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		this.checkOverlap();
	}

	/**
	 * Check overlap.
	 */
	private void checkOverlap() {
		if (!CollectionUtil.isEmpty(this.lstWorkingTimezone)) {
			val size = this.lstWorkingTimezone.size();
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					if (this.lstWorkingTimezone.get(i).getTimezone()
							.isOverlap(this.lstWorkingTimezone.get(j).getTimezone())) {
						throw new BusinessException("Msg_515");
					}
				}
			}
		}

		if (!CollectionUtil.isEmpty(this.lstOTTimezone)) {
			val size = this.lstOTTimezone.size();
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					if (this.lstOTTimezone.get(i).getTimezone()
							.isOverlap(this.lstOTTimezone.get(j).getTimezone())) {
						throw new BusinessException("Msg_515");
					}
				}
			}
		}

	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FixedWorkTimezoneSetSetMemento memento){
		memento.setLstWorkingTimezone(this.lstWorkingTimezone);
		memento.setLstOTTimezone(this.lstOTTimezone);
	}
}
