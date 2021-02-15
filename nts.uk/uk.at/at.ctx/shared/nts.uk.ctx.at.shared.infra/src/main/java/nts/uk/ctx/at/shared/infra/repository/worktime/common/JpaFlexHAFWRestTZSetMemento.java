/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;

/**
 * The Class JpaFlexHAFWRestTZSetMemento.
 */
public class JpaFlexHAFWRestTZSetMemento implements FlowWorkRestTimezoneSetMemento{
	
	/** The entity group. */
	private KshmtWtFleBrFlWek entity;
	

	/**
	 * Instantiates a new jpa flex HAFW rest TZ get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHAFWRestTZSetMemento(KshmtWtFleBrFlWek entity) {
		super();
		this.entity = entity;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento#
	 * setFixRestTime(boolean)
	 */
	@Override
	public void setFixRestTime(boolean fixRestTime) {
		this.entity.setFixRestTime(BooleanGetAtr.getAtrByBoolean(fixRestTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento#
	 * setFixedRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSet)
	 */
	@Override
	public void setFixedRestTimezone(TimezoneOfFixedRestTimeSet fixedRestTimezone) {
		if (fixedRestTimezone != null) {
			fixedRestTimezone.saveToMemento(new JpaFlexHATzOFRTimeSetSetMemento(this.entity));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneSetMemento#
	 * setFlowRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestTimezone)
	 */
	@Override
	public void setFlowRestTimezone(FlowRestTimezone flowRestTimezone) {
		if(flowRestTimezone!=null){
			flowRestTimezone.saveToMemento(new JpaFlexHAFlowRestTzSetMemento(this.entity));
		}
		
	}

}
