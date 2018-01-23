/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtPioritySet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtRoundingSet;

/**
 * The Class JpaWorkTimezoneStampSetGetMemento.
 */
public class JpaWorkTimezoneStampSetGetMemento implements WorkTimezoneStampSetGetMemento {

	/** The kshmt rounding sets. */
	private List<KshmtRoundingSet> kshmtRoundingSets;

	/** The kshmt piority sets. */
	private List<KshmtPioritySet> kshmtPioritySets;

	/**
	 * Instantiates a new jpa work timezone stamp set get memento.
	 *
	 * @param kshmtRoundingSets
	 *            the kshmt rounding sets
	 * @param kshmtPioritySets
	 *            the kshmt piority sets
	 */
	public JpaWorkTimezoneStampSetGetMemento(List<KshmtRoundingSet> kshmtRoundingSets,
			List<KshmtPioritySet> kshmtPioritySets) {
		super();
		this.kshmtRoundingSets = kshmtRoundingSets;
		this.kshmtPioritySets = kshmtPioritySets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetGetMemento#
	 * getRoundingSet()
	 */
	@Override
	public List<RoundingSet> getRoundingSet() {
		if (CollectionUtil.isEmpty(this.kshmtRoundingSets)) {
			return new ArrayList<>();
		}
		return this.kshmtRoundingSets.stream()
				.map(entity -> new RoundingSet(new JpaRoundingSetGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetGetMemento#
	 * getPrioritySet()
	 */
	@Override
	public List<PrioritySetting> getPrioritySet() {
		if (CollectionUtil.isEmpty(this.kshmtPioritySets)) {
			return new ArrayList<>();
		}
		return this.kshmtPioritySets.stream()
				.map(entity -> new PrioritySetting(new JpaPrioritySettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
