/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionrate;

import lombok.Data;

/**
 * The Class PensionAvgearnValue.
 */
@Data
public class PensionAvgearnValue {

	/** The male amount. */
	private Long maleAmount;

	/** The female amount. */
	private Long femaleAmount;

	/** The unknown amount. */
	private Long unknownAmount;

	/**
	 * Instantiates a new pension avgearn value.
	 */
	public PensionAvgearnValue() {
		super();
	}

}
