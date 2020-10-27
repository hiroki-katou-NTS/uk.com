/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloHolTs;

/**
 * The Class JpaFlowWorkHolidayTimeZoneSetMemento.
 */
public class JpaFlowWorkHolidayTimeZoneSetMemento implements FlWorkHdTzSetMemento {

	/** The entity. */
	KshmtWtFloHolTs entity;

	/**
	 * Instantiates a new jpa flow work holiday time zone set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowWorkHolidayTimeZoneSetMemento(KshmtWtFloHolTs entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setWorktimeNo(java.lang.Integer)
	 */
	@Override
	public void setWorktimeNo(Integer wtNo) {
		this.entity.getKshmtWtFloHolTsPK().setWorktimeNo(wtNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setUseInLegalBreakRestrictTime(boolean)
	 */
	@Override
	public void setUseInLegalBreakRestrictTime(boolean brTime) {
		this.entity.setUseInlegalBreakRestTime(BooleanGetAtr.getAtrByBoolean(brTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setInLegalBreakFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakFrameNo)
	 */
	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo brNo) {
		this.entity.setInlegalBreakRestTime(brNo.v().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setUseOutLegalBreakRestrictTime(boolean)
	 */
	@Override
	public void setUseOutLegalBreakRestrictTime(boolean brTime) {
		this.entity.setUseOutLegalbReakRestTime(BooleanGetAtr.getAtrByBoolean(brTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setOutLegalBreakFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakFrameNo)
	 */
	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo brNo) {
		this.entity.setOutLegalbReakRestTime(brNo.v().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setUseOutLegalPubHolRestrictTime(boolean)
	 */
	@Override
	public void setUseOutLegalPubHolRestrictTime(boolean uolphrTime) {
		this.entity.setUseOutLegalPubholRestTime(BooleanGetAtr.getAtrByBoolean(uolphrTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setOutLegalPubHolFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakFrameNo)
	 */
	@Override
	public void setOutLegalPubHolFrameNo(BreakFrameNo no) {
		this.entity.setOutLegalPubholRestTime(no.v().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzSetMemento#
	 * setFlowTimeSetting(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowTimeSetting)
	 */
	@Override
	public void setFlowTimeSetting(FlowTimeSetting ftSet) {
		ftSet.saveToMemento(new JpaFlowTimeSettingSetMemento(this.entity));
	}

}
