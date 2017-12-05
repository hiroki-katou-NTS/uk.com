/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;

/**
 * The Class ExtraordWorkOTFrameSetDto.
 */
@Getter
@Setter
public class ExtraordWorkOTFrameSetDto implements ExtraordWorkOTFrameSetGetMemento {

	/** The OT frame no. */
	private BigDecimal oTFrameNo;

	/** The in legal work frame no. */
	private BigDecimal inLegalWorkFrameNo;

	/** The settlement order. */
	private Integer settlementOrder;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetGetMemento#getOTFrameNo()
	 */
	@Override
	public OTFrameNo getOTFrameNo() {
		return new OTFrameNo(this.oTFrameNo);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetGetMemento#getInLegalWorkFrameNo()
	 */
	@Override
	public OTFrameNo getInLegalWorkFrameNo() {
		return new OTFrameNo(this.inLegalWorkFrameNo);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetGetMemento#getSettlementOrder()
	 */
	@Override
	public SettlementOrder getSettlementOrder() {
		return new SettlementOrder(this.settlementOrder);
	}
	


}
