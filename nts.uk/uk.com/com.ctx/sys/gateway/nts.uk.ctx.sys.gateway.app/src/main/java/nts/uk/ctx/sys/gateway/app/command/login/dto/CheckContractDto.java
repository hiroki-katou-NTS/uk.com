/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.login.dto;

/**
 * The Class CheckContractDto.
 */
public class CheckContractDto {

	/** The show contract. */
	public boolean showContract;

	/** The onpre. */
	public boolean onpre;
	
	/**
	 * Instantiates a new check contract dto.
	 *
	 * @param showContract the show contract
	 */
	public CheckContractDto(boolean showContract,boolean onpre) {
		this.showContract = showContract;
		this.onpre = onpre;
	}
}
