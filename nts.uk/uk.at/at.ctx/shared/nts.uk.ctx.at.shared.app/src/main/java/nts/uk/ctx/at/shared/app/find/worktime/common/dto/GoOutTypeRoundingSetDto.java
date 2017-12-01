/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class GoOutTypeRoundingSetDto.
 */
@Getter
@Setter
public class GoOutTypeRoundingSetDto {
	
	/** The offical use compen go out. */
	private DeductGoOutRoundingSetDto officalUseCompenGoOut;
	
	/** The private union go out. */
	private DeductGoOutRoundingSetDto privateUnionGoOut;

	/**
	 * Instantiates a new go out type rounding set dto.
	 *
	 * @param officalUseCompenGoOut the offical use compen go out
	 * @param privateUnionGoOut the private union go out
	 */
	public GoOutTypeRoundingSetDto(DeductGoOutRoundingSetDto officalUseCompenGoOut,
			DeductGoOutRoundingSetDto privateUnionGoOut) {
		super();
		this.officalUseCompenGoOut = officalUseCompenGoOut;
		this.privateUnionGoOut = privateUnionGoOut;
	}
	
	

}
