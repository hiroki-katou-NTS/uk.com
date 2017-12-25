/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.predset.JpaTimezoneGetMemento;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixRestTimezoneSetGetMemento implements FixRestTimezoneSetGetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa core time setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixRestTimezoneSetGetMemento(KshmtFixedWorkSet entity) {
		super();
		if (entity.getKshmtFixedWorkSetPK() == null) {
			entity.setKshmtFixedWorkSetPK(new KshmtFixedWorkSetPK());
		}
		this.entity = entity;
	}

	@Override
	public List<DeductionTime> getLstTimezone() {
		return this.entity.get.stream()
				.map(entity -> new TimezoneUse(new JpaTimezoneGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
