/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFixedWorkTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexHAFWRestTZSetMemento;

/**
 * The Class JpaFlexHAWorkTimeSetMemento.
 */
public class JpaFlexHAWorkTimeSetMemento implements FlexHalfDayWorkTimeSetMemento{


	/** The entity. */
	 private KshmtWtFleBrFlWek entity;

	/**
	 * Instantiates a new jpa flex HA work time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAWorkTimeSetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone lstRestTimezone) {
		if(lstRestTimezone!=null){
			lstRestTimezone.saveToMemento(new JpaFlexHAFWRestTZSetMemento(this.entity));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FixedWorkTimezoneSet)
	 */
	@Override
	public void setWorkTimezone(FixedWorkTimezoneSet workTimezone) {
		if(workTimezone!=null){
			workTimezone.saveToMemento(new JpaFlexFixedWorkTimezoneSetSetMemento(this.entity));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setAmpmAtr(nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr)
	 */
	@Override
	public void setAmpmAtr(AmPmAtr ampmAtr) {
		this.entity.getKshmtWtFleBrFlWekPK().setAmPmAtr(ampmAtr.value);
	}

}
