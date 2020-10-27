/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;

/**
 * The Class JpaFixOffdayWorkTimezoneGetMemento.
 */
public class JpaFixOffdayWorkTimezoneGetMemento implements FixOffdayWorkTimezoneGetMemento {

	/** The entity. */
	private KshmtWtFix entity;

	/**
	 * Instantiates a new jpa fix offday work timezone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFixOffdayWorkTimezoneGetMemento(KshmtWtFix entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento#getRestTimezone()
	 */
	@Override
	public FixRestTimezoneSet getRestTimezone() {
		return new FixRestTimezoneSet(new JpaFixedOffDayRestTimeGetMemento(this.entity.getLstKshmtWtFixBrHolTs()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento#getLstWorkTimezone()
	 */
	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFixHolTs())) {
			return new ArrayList<>();
		}
		return this.entity.getLstKshmtWtFixHolTs().stream()
				.map(entity -> new HDWorkTimeSheetSetting(new JpaFixedHDWorkTimeSheetGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
