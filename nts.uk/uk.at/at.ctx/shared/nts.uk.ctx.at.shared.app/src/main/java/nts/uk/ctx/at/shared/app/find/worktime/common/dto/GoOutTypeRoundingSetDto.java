/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSetSetMemento;

/**
 * The Class GoOutTypeRoundingSetDto.
 */
@Getter
@Setter
public class GoOutTypeRoundingSetDto implements GoOutTypeRoundingSetSetMemento{
	
	/** The offical use compen go out. */
	private DeductGoOutRoundingSetDto officalUseCompenGoOut;
	
	/** The private union go out. */
	private DeductGoOutRoundingSetDto privateUnionGoOut;

	public GoOutTypeRoundingSetDto() {
		this.officalUseCompenGoOut = new DeductGoOutRoundingSetDto();
		this.privateUnionGoOut = new DeductGoOutRoundingSetDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSetSetMemento#
	 * setOfficalUseCompenGoOut(nts.uk.ctx.at.shared.dom.worktime.common.
	 * DeductGoOutRoundingSet)
	 */
	@Override
	public void setOfficalUseCompenGoOut(DeductGoOutRoundingSet officalUseCompenGoOut) {
		officalUseCompenGoOut.saveToMemento(this.officalUseCompenGoOut);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSetSetMemento#
	 * setPrivateUnionGoOut(nts.uk.ctx.at.shared.dom.worktime.common.
	 * DeductGoOutRoundingSet)
	 */
	@Override
	public void setPrivateUnionGoOut(DeductGoOutRoundingSet privateUnionGoOut) {
		privateUnionGoOut.saveToMemento(this.privateUnionGoOut);
	}
	
}
