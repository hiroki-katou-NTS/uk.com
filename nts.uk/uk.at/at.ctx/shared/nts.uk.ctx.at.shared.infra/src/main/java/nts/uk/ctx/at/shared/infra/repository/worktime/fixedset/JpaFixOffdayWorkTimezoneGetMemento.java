/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;

/**
 * The Class JpaFixOffdayWorkTimezoneGetMemento.
 */
public class JpaFixOffdayWorkTimezoneGetMemento implements FixOffdayWorkTimezoneGetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa fix offday work timezone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFixOffdayWorkTimezoneGetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento#getRestTimezone()
	 */
	@Override
	public FixRestTimezoneSet getRestTimezone() {
		return new FixRestTimezoneSet(new JpaFixedOffDayRestTimeGetMemento(this.entity.getKshmtFixedHolRestSets()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento#getLstWorkTimezone()
	 */
	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		return this.entity.getLstKshmtFixedHolTimeSet().stream()
				.map(entity -> new HDWorkTimeSheetSetting(new JpaFixedHDWorkTimeSheetGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
