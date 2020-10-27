/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloOverTs;

/**
 * The Class JpaFlowOTTimezoneGetMemento.
 */
public class JpaFlowOTTimezoneGetMemento implements FlowOTTimezoneGetMemento {

	/** The entity. */
	private KshmtWtFloOverTs entity; 
	
	/**
	 * Instantiates a new jpa flow OT timezone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowOTTimezoneGetMemento(KshmtWtFloOverTs entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#getWorktimeNo()
	 */
	@Override
	public Integer getWorktimeNo() {
		return this.entity.getKshmtWtFloOverTsPK().getWorktimeNo();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#getRestrictTime()
	 */
	@Override
	public boolean getRestrictTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getRestrictTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#getOTFrameNo()
	 */
	@Override
	public OvertimeWorkFrameNo getOTFrameNo() {
		return new OvertimeWorkFrameNo(BigDecimal.valueOf(this.entity.getOtFrameNo()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#getFlowTimeSetting()
	 */
	@Override
	public FlowTimeSetting getFlowTimeSetting() {
		return new FlowTimeSetting(new JpaFlowOTTimeSettingGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#getInLegalOTFrameNo()
	 */
	@Override
	public OvertimeWorkFrameNo getInLegalOTFrameNo() {
		return new OvertimeWorkFrameNo(this.entity.getInLegalOtFrameNo() == null ? null : BigDecimal.valueOf(this.entity.getInLegalOtFrameNo()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlOTTimezoneGetMemento#getSettlementOrder()
	 */
	@Override
	public SettlementOrder getSettlementOrder() {
		return new SettlementOrder(this.entity.getSettlementOrder());
	}

}
