/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSetGetMemento;

/**
 * The Class GoOutTypeRoundingSetDto.
 */
@Getter
@Setter
public class GoOutTypeRoundingSetDto implements GoOutTypeRoundingSetGetMemento{
	
	/** The offical use compen go out. */
	private DeductGoOutRoundingSetDto officalUseCompenGoOut;
	
	/** The private union go out. */
	private DeductGoOutRoundingSetDto privateUnionGoOut;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSetGetMemento#getOfficalUseCompenGoOut()
	 */
	@Override
	public DeductGoOutRoundingSet getOfficalUseCompenGoOut() {
		return new DeductGoOutRoundingSet(this.officalUseCompenGoOut);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSetGetMemento#getPrivateUnionGoOut()
	 */
	@Override
	public DeductGoOutRoundingSet getPrivateUnionGoOut() {
		return new DeductGoOutRoundingSet(this.privateUnionGoOut);
	}
	

	
}
