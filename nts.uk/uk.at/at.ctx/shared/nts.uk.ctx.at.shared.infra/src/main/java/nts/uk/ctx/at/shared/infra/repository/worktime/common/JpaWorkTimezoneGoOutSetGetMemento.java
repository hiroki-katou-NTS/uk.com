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
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGooutRound;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGoout;

/**
 * The Class JpaWorkTimezoneGoOutSetGetMemento.
 */
public class JpaWorkTimezoneGoOutSetGetMemento implements WorkTimezoneGoOutSetGetMemento {

	/** The kshmt worktime go out set. */
	private KshmtWtComGoout kshmtWtComGoout;

	/** The kshmt other late early. */
	private List<KshmtWtComGooutRound> kshmtWtComGooutRounds;

	/**
	 * Instantiates a new jpa work timezone go out set get memento.
	 *
	 * @param kshmtWtComGoout
	 *            the kshmt worktime go out set
	 * @param kshmtWtComLatetime
	 *            the kshmt other late early
	 */
	public JpaWorkTimezoneGoOutSetGetMemento(KshmtWtComGoout kshmtWtComGoout,
			List<KshmtWtComGooutRound> kshmtWtComGooutRounds) {
		super();
		this.kshmtWtComGoout = kshmtWtComGoout;
		this.kshmtWtComGooutRounds = kshmtWtComGooutRounds;
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
		if (this.kshmtWtComGoout == null) {
			return null;
		}
		return new TotalRoundingSet(this.kshmtWtComGoout.getRoundingSameFrame(),
				this.kshmtWtComGoout.getRoundingCrossFrame());
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
		if (CollectionUtil.isEmpty(this.kshmtWtComGooutRounds)) {
			this.kshmtWtComGooutRounds = new ArrayList<>();
		}
		return new GoOutTimezoneRoundingSet(
				new JpaGoOutTimezoneRoundingSetGetMemento(this.kshmtWtComGooutRounds));
	}

}
