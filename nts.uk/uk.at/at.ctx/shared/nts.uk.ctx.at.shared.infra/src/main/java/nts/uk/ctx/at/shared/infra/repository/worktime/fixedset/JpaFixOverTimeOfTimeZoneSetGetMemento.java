/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixOverTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFixOverTimeOfTimeZoneSetGetMemento.
 */
public class JpaFixOverTimeOfTimeZoneSetGetMemento implements OverTimeOfTimeZoneSetGetMemento {

	/** The entity. */
	private KshmtWtFixOverTs entity;

	/**
	 * Instantiates a new jpa fix over time of time zone set get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixOverTimeOfTimeZoneSetGetMemento(KshmtWtFixOverTs entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento#
	 * getWorkTimezoneNo()
	 */
	@Override
	public EmTimezoneNo getWorkTimezoneNo() {
		return new EmTimezoneNo(this.entity.getKshmtWtFixOverTsPK().getWorktimeNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento#
	 * getRestraintTimeUse()
	 */
	@Override
	public boolean getRestraintTimeUse() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getTreatTimeWork());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento#
	 * getEarlyOTUse()
	 */
	@Override
	public boolean getEarlyOTUse() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getTreatEarlyOtWork());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento#
	 * getTimezone()
	 */
	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(new TimeWithDayAttr(this.entity.getTimeStr()),
				new TimeWithDayAttr(this.entity.getTimeEnd()),
				new TimeRoundingSetting(this.entity.getUnit(), this.entity.getRounding()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento#
	 * getOTFrameNo()
	 */
	@Override
	public OTFrameNo getOTFrameNo() {
		return new OTFrameNo(this.entity.getOtFrameNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento#
	 * getLegalOTframeNo()
	 */
	@Override
	public OTFrameNo getLegalOTframeNo() {
		return new OTFrameNo(this.entity.getLegalOtFrameNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetGetMemento#
	 * getSettlementOrder()
	 */
	@Override
	public SettlementOrder getSettlementOrder() {
		return new SettlementOrder(this.entity.getPayoffOrder());
	}

}
