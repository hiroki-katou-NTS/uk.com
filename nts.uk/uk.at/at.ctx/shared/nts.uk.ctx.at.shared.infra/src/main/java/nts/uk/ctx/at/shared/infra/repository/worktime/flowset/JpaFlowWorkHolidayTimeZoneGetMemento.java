/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloHolTs;

/**
 * The Class JpaFlowWorkHolidayTimeZoneGetMemento.
 */
public class JpaFlowWorkHolidayTimeZoneGetMemento implements FlWorkHdTzGetMemento {

	/** The entity. */
	KshmtWtFloHolTs entity;
	
	/**
	 * Instantiates a new jpa flow work holiday time zone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkHolidayTimeZoneGetMemento(KshmtWtFloHolTs entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getWorktimeNo()
	 */
	@Override
	public Integer getWorktimeNo() {
		return this.entity.getKshmtWtFloHolTsPK().getWorktimeNo();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getUseInLegalBreakRestrictTime()
	 */
	@Override
	public boolean getUseInLegalBreakRestrictTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseInlegalBreakRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getInLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getInLegalBreakFrameNo() {
		return new BreakFrameNo(BigDecimal.valueOf(this.entity.getInlegalBreakRestTime()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getUseOutLegalBreakRestrictTime()
	 */
	@Override
	public boolean getUseOutLegalBreakRestrictTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseOutLegalbReakRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getOutLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalBreakFrameNo() {
		return new BreakFrameNo(BigDecimal.valueOf(this.entity.getOutLegalbReakRestTime()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getUseOutLegalPubHolRestrictTime()
	 */
	@Override
	public boolean getUseOutLegalPubHolRestrictTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseOutLegalPubholRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getOutLegalPubHolFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalPubHolFrameNo() {
		return new BreakFrameNo(BigDecimal.valueOf(this.entity.getOutLegalPubholRestTime()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkHdTzGetMemento#getFlowTimeSetting()
	 */
	@Override
	public FlowTimeSetting getFlowTimeSetting() {
		return new FlowTimeSetting(new JpaFlowTimeSettingGetMemento(this.entity));
	}

}
