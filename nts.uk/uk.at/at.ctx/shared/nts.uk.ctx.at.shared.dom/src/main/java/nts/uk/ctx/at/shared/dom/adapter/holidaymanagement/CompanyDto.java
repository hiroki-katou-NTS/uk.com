/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.holidaymanagement;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CompanyDto.
 */
// Imported class for request list 316
@Getter
@Setter
public class CompanyDto {

	/** The start month. */
	// 期首月
	private int startMonth;

	/**
	 * Instantiates a new company dto.
	 *
	 * @param startMonth
	 *            the start month
	 */
	public CompanyDto(int startMonth) {
		super();
		this.startMonth = startMonth;
	}

	/**
	 * Instantiates a new company dto.
	 */
	public CompanyDto() {
		super();
	}

}
