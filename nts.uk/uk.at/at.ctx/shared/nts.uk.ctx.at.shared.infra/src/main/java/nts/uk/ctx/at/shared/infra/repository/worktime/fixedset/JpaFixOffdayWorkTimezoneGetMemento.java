/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixOffdayWorkTimezoneGetMemento implements FixOffdayWorkTimezoneGetMemento {

	/** The entity. */
	private KshmtFlexWorkSet entity;

	/**
	 * Instantiates a new jpa core time setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixOffdayWorkTimezoneGetMemento(KshmtFlexWorkSet entity) {
		super();
		this.entity = entity;
	}

	@Override
	public FixRestTimezoneSet getRestTimezone() {
		return new FixRestTimezoneSet(new JpaFixRestTimezoneSetGetMemento(entity));
	}

	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		return null;
	}

}
