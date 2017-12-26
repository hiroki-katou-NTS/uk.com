/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;

/**
 * The Class ExtraordWorkOTFrameSetDto.
 */
@Getter
@Setter
public class ExtraordWorkOTFrameSetDto implements ExtraordWorkOTFrameSetSetMemento {

	/** The OT frame no. */
	private Integer oTFrameNo;

	/** The in legal work frame no. */
	private Integer inLegalWorkFrameNo;

	/** The settlement order. */
	private Integer settlementOrder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetSetMemento
	 * #setOTFrameNo(nts.uk.ctx.at.shared.dom.worktime.fixedset.OTFrameNo)
	 */
	@Override
	public void setOTFrameNo(OTFrameNo no) {
		this.oTFrameNo = no.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetSetMemento
	 * #setInLegalWorkFrameNo(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * OTFrameNo)
	 */
	@Override
	public void setInLegalWorkFrameNo(OTFrameNo no) {
		this.inLegalWorkFrameNo = no.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.ExtraordWorkOTFrameSetSetMemento
	 * #setSettlementOrder(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * SettlementOrder)
	 */
	@Override
	public void setSettlementOrder(SettlementOrder order) {
		this.settlementOrder = order.v();
	}

}
