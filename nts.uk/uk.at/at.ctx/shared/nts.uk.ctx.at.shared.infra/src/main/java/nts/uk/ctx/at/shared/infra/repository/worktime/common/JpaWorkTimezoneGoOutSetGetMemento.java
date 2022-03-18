/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGooutRound;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGoout;

/**
 * The Class JpaWorkTimezoneGoOutSetGetMemento.
 */
public class JpaWorkTimezoneGoOutSetGetMemento implements WorkTimezoneGoOutSetGetMemento {

	/** The kshmt worktime go out set. */
	private KshmtWtComGoout kshmtWorktimeGoOutSet;

	/** The kshmt other late early. */
	private List<KshmtWtComGooutRound> kshmtSpecialRoundOuts;

	/**
	 * Instantiates a new jpa work timezone go out set get memento.
	 *
	 * @param kshmtWorktimeGoOutSet
	 *            the kshmt worktime go out set
	 * @param kshmtOtherLateEarly
	 *            the kshmt other late early
	 */
	public JpaWorkTimezoneGoOutSetGetMemento(KshmtWtComGoout kshmtWorktimeGoOutSet,
			List<KshmtWtComGooutRound> kshmtSpecialRoundOuts) {
		super();
		this.kshmtWorktimeGoOutSet = kshmtWorktimeGoOutSet;
		this.kshmtSpecialRoundOuts = kshmtSpecialRoundOuts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetGetMemento#
	 * getRoundingMethod()
	 */
	@Override
	public GoOutTimeRoundingMethod getRoundingMethod() {
		if (this.kshmtWorktimeGoOutSet == null) {
			return null;
		}
		return GoOutTimeRoundingMethod.valueOf(this.kshmtWorktimeGoOutSet.getRoundingMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetGetMemento#
	 * getDiffTimezoneSetting()
	 */
	@Override
	public GoOutTimezoneRoundingSet getDiffTimezoneSetting() {
		if (CollectionUtil.isEmpty(this.kshmtSpecialRoundOuts)) {
			this.kshmtSpecialRoundOuts = new ArrayList<>();
		}
		return new GoOutTimezoneRoundingSet(
				new JpaGoOutTimezoneRoundingSetGetMemento(this.kshmtSpecialRoundOuts));
	}

}
