/******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOtTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOtTimeSetPK;

/**
 * The Class JpaFlexOverTimeOfTimeZoneSetGetMemento.
 */
public class JpaFlexOverTimeOfTimeZoneSetSetMemento implements OverTimeOfTimeZoneSetSetMemento{
	
	/** The entity. */
	private KshmtFlexOtTimeSet entity;

	/**
	 * Instantiates a new jpa flex over time of time zone set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOverTimeOfTimeZoneSetSetMemento(KshmtFlexOtTimeSet entity) {
		super();
		if(entity.getKshmtFlexOtTimeSetPK() == null){
			entity.setKshmtFlexOtTimeSetPK(new KshmtFlexOtTimeSetPK());
		}
		this.entity = entity;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento#
	 * setWorkTimezoneNo(nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo)
	 */
	@Override
	public void setWorkTimezoneNo(EmTimezoneNo workTimezoneNo) {
		this.entity.getKshmtFlexOtTimeSetPK().setWorktimeNo(workTimezoneNo.v());
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento#
	 * setRestraintTimeUse(boolean)
	 */
	@Override
	public void setRestraintTimeUse(boolean restraintTimeUse) {
		this.entity.setTreatTimeWork(BooleanGetAtr.getAtrByBoolean(restraintTimeUse));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento#
	 * setEarlyOTUse(boolean)
	 */
	@Override
	public void setEarlyOTUse(boolean earlyOTUse) {
		this.entity.setTreatEarlyOtWork(BooleanGetAtr.getAtrByBoolean(earlyOTUse));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento#
	 * setTimezone(nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding)
	 */
	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		if (timezone != null) {
			timezone.saveToMemento(new JpaFlexOTTimeZoneRoundingSetMemento(this.entity));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento#
	 * setOTFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo)
	 */
	@Override
	public void setOTFrameNo(OTFrameNo OTFrameNo) {
		this.entity.setOtFrameNo(OTFrameNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento#
	 * setLegalOTframeNo(nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo)
	 */
	@Override
	public void setLegalOTframeNo(OTFrameNo legalOTframeNo) {
		this.entity.setLegalOtFrameNo(legalOTframeNo == null ? null : legalOTframeNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetSetMemento#
	 * setSettlementOrder(nts.uk.ctx.at.shared.dom.worktime.common.
	 * SettlementOrder)
	 */
	@Override
	public void setSettlementOrder(SettlementOrder settlementOrder) {
		this.entity.setPayoffOrder(settlementOrder == null ? null : settlementOrder.v());
	}

}
