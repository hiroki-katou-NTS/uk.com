/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWtzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowHalfDayWorkTimezoneGetMemento.
 */
public class JpaFlowHalfDayWorkTimezoneGetMemento implements FlowHalfDayWtzGetMemento {

	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow half day work timezone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowHalfDayWorkTimezoneGetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzGetMemento#getRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		return new FlowWorkRestTimezone(new JpaFlowWorkRestTimezoneGetMemento(this.entity.getFlowHalfDayWorkRtSet()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzGetMemento#getWorkTimeZone()
	 */
	@Override
	public FlowWorkTimezoneSetting getWorkTimeZone() {
		return new FlowWorkTimezoneSetting(new JpaFlowWorkTimezoneSettingGetMemento(this.entity));
	}

}
