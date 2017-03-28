/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.address.find;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class AddressSelection.
 */
@Getter
@Setter
public class AddressSelection {

	/** The id. */
	private String id;

	/** The city. */
	private String localGovCode;

	/** The zip code. */
	private String postcode;

	/** The prefecture. */
	private String prefectureNameKn;

	/** The prefecture. */
	private String municipalityNameKn;

	/** The town. */
	private String townNameKn;

	/** The prefecture. */
	private String prefectureName;

	/** The prefecture. */
	private String municipalityName;

	/** The town. */
	private String townName;
}
