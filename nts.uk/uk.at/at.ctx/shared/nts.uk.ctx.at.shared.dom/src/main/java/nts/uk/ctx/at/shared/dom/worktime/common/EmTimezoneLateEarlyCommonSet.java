/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;

/**
 * The Class EmTimezoneLateEarlyCommonSet.
 */
// 就業時間帯の遅刻・早退共通設定
@Getter
public class EmTimezoneLateEarlyCommonSet {

	/** The del from em time. */
	// 就業時間から控除する
	private boolean delFromEmTime;

	/**
	 * Instantiates a new em timezone late early common set.
	 *
	 * @param delFromEmTime
	 *            the del from em time
	 */
	public EmTimezoneLateEarlyCommonSet(boolean delFromEmTime) {
		super();
		this.delFromEmTime = delFromEmTime;
	}

	/**
	 * Instantiates a new em timezone late early common set.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmTimezoneLateEarlyCommonSet(EmTimezoneLateEarlyCommonSetGetMemento memento) {
		this.delFromEmTime = memento.getDelFromEmTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmTimezoneLateEarlyCommonSetSetMemento memento) {
		memento.setDelFromEmTime(this.delFromEmTime);
	}

}
