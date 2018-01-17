/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSet;

/**
 * The Class JpaFlowHalfDayWorkTimezoneSetMemento.
 */
public class JpaFlowHalfDayWorkTimezoneSetMemento implements FlHalfDayWtzSetMemento {

	/** The entity. */
	private KshmtFlowWorkSet entity;
	
	/**
	 * Instantiates a new jpa flow half day work timezone set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowHalfDayWorkTimezoneSetMemento(KshmtFlowWorkSet entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzSetMemento#setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone tzone) {
		//TODOtzone.saveToMemento(new JpaFlowWorkRestTimezoneSetMemento(this.entity.getFlowHalfDayWorkRtSet()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzSetMemento#setWorkTimeZone(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkTimezoneSetting)
	 */
	@Override
	public void setWorkTimeZone(FlowWorkTimezoneSetting tzone) {
		//TODOtzone.saveToMemento(new JpaFlowWorkTimezoneSettingSetMemento(this.entity));
	}

}
