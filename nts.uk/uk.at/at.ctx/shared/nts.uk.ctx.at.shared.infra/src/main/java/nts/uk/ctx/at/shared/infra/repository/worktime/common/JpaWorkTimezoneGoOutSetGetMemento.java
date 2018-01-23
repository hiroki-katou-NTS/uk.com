/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtSpecialRoundOut;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeGoOutSet;

/**
 * The Class JpaWorkTimezoneGoOutSetGetMemento.
 */
public class JpaWorkTimezoneGoOutSetGetMemento implements WorkTimezoneGoOutSetGetMemento {

	/** The kshmt worktime go out set. */
	private KshmtWorktimeGoOutSet kshmtWorktimeGoOutSet;

	/** The kshmt other late early. */
	private List<KshmtSpecialRoundOut> kshmtSpecialRoundOuts;

	/**
	 * Instantiates a new jpa work timezone go out set get memento.
	 *
	 * @param kshmtWorktimeGoOutSet
	 *            the kshmt worktime go out set
	 * @param kshmtOtherLateEarly
	 *            the kshmt other late early
	 */
	public JpaWorkTimezoneGoOutSetGetMemento(KshmtWorktimeGoOutSet kshmtWorktimeGoOutSet,
			List<KshmtSpecialRoundOut> kshmtSpecialRoundOuts) {
		super();
		this.kshmtWorktimeGoOutSet = kshmtWorktimeGoOutSet;
		this.kshmtSpecialRoundOuts = kshmtSpecialRoundOuts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSetGetMemento#
	 * getTotalRoundingSet()
	 */
	@Override
	public TotalRoundingSet getTotalRoundingSet() {
		if (this.kshmtWorktimeGoOutSet == null) {
			return null;
		}
		return new TotalRoundingSet(this.kshmtWorktimeGoOutSet.getRoundingSameFrame(),
				this.kshmtWorktimeGoOutSet.getRoundingCrossFrame());
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
