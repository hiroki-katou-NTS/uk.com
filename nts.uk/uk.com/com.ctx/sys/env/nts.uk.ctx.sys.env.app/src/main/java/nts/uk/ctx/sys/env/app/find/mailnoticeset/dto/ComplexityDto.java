/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.dto;

import lombok.Getter;

/**
 * The Class ComplexityDto.
 */
@Getter
public class ComplexityDto {

	/** The alphabet digit. */
	private Integer alphabetDigit;

	/** The number of digits. */
	private Integer numberOfDigits;

	/** The number of char. */
	private Integer numberOfChar;

	/**
	 * Instantiates a new complexity dto.
	 *
	 * @param alphabetDigit
	 *            the alphabet digit
	 * @param numberOfDigits
	 *            the number of digits
	 * @param numberOfChar
	 *            the number of char
	 */
	public ComplexityDto(Integer alphabetDigit, Integer numberOfDigits, Integer numberOfChar) {
		this.alphabetDigit = alphabetDigit;
		this.numberOfDigits = numberOfDigits;
		this.numberOfChar = numberOfChar;
	}

}
