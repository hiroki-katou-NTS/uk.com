/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloOverTs;

/**
 * The Class JpaFlowOTTimezoneSetMemento.
 */
public class JpaFlowOTTimezoneSetMemento implements FlowOTTimezoneSetMemento {

	/** The entity. */
	private KshmtWtFloOverTs entity;

	/**
	 * Instantiates a new jpa flow OT timezone set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowOTTimezoneSetMemento(KshmtWtFloOverTs entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneSetMemento#
	 * setWorktimeNo(java.lang.Integer)
	 */
	@Override
	public void setWorktimeNo(Integer no) {
		this.entity.getKshmtWtFloOverTsPK().setWorktimeNo(no);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneSetMemento#
	 * setRestrictTime(boolean)
	 */
	@Override
	public void setRestrictTime(boolean val) {
		this.entity.setRestrictTime(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneSetMemento#
	 * setOTFrameNo(nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo)
	 */
	@Override
	public void setOTFrameNo(OvertimeWorkFrameNo no) {
		this.entity.setOtFrameNo(no.v().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneSetMemento#
	 * setFlowTimeSetting(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowTimeSetting)
	 */
	@Override
	public void setFlowTimeSetting(FlowTimeSetting ftSet) {
		ftSet.saveToMemento(new JpaFlowOTTimeSettingSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneSetMemento#
	 * setInLegalOTFrameNo(nts.uk.ctx.at.shared.dom.ot.frame.
	 * OvertimeWorkFrameNo)
	 */
	@Override
	public void setInLegalOTFrameNo(OvertimeWorkFrameNo no) {
		this.entity.setInLegalOtFrameNo(no == null ? null : no.v().intValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneSetMemento#
	 * setSettlementOrder(nts.uk.ctx.at.shared.dom.worktime.common.
	 * SettlementOrder)
	 */
	@Override
	public void setSettlementOrder(SettlementOrder od) {
		this.entity.setSettlementOrder(od == null ? null : od.v());
	}

}
