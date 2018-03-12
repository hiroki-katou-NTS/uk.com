/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * The Class RoundingSet.
 */
// 丸め設定
@Getter
public class RoundingSet extends WorkTimeDomainObject {

	/** The rounding set. */
	// 時刻丸め
	private InstantRounding roundingSet;

	/** The section. */
	// 打優丸め区分
	private Superiority section;

	/**
	 * Instantiates a new rounding set.
	 *
	 * @param memento
	 *            the memento
	 */
	public RoundingSet(RoundingSetGetMemento memento) {
		this.roundingSet = memento.getRoundingSet();
		this.section = memento.getSection();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(RoundingSetSetMemento memento) {
		memento.setRoundingSet(this.roundingSet);
		memento.setSection(this.section);
	}

}
