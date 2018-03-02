/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class WorkTimezoneLateEarlySet.
 */
//就業時間帯の遅刻・早退設定

/**
 * Gets the other class sets.
 *
 * @return the other class sets
 */
@Getter
public class WorkTimezoneLateEarlySet extends WorkTimeDomainObject {

	/** The common set. */
	// 共通設定
	private EmTimezoneLateEarlyCommonSet commonSet;

	/** The other class sets. */
	// 区分別設定
	private List<OtherEmTimezoneLateEarlySet> otherClassSets;

	/**
	 * Instantiates a new work timezone late early set.
	 *
	 * @param memento
	 *            the memento
	 */
	public WorkTimezoneLateEarlySet(WorkTimezoneLateEarlySetGetMemento memento) {
		this.commonSet = memento.getCommonSet();
		this.otherClassSets = memento.getOtherClassSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkTimezoneLateEarlySetSetMemento memento) {
		memento.setCommonSet(this.commonSet);
		memento.setOtherClassSet(this.otherClassSets);
	}
	
}
