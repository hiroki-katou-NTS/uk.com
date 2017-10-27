/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.pub.person;

import lombok.Data;

/**
 * The Class PersonDto.
 */
@Data
public class PubPersonDto {

	/** The person id. */
	private String personId;

	/** The person name. */
	private String personName;

	/**
	 * Instantiates a new person dto.
	 *
	 * @param personId
	 *            the person id
	 * @param personName
	 *            the person name
	 */
	public PubPersonDto(String personId, String personName) {
		super();
		this.personId = personId;
		this.personName = personName;
	}

}
