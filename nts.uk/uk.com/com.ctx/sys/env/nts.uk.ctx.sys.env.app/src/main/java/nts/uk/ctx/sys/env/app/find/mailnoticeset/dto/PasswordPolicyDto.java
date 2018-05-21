/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.dto;

import lombok.Getter;

/**
 * The Class PasswordPolicyDto.
 */
@Getter
public class PasswordPolicyDto {

	/** The is use. */
	private Boolean isUse;

	/** The complexity. */
	private ComplexityDto complexity;

	/** The lowest digits. */
	private Integer lowestDigits;

	/** The history count. */
	private Integer historyCount;

	/** The validity period. */
	private Integer validityPeriod;

	/**
	 * Instantiates a new password policy dto.
	 *
	 * @param isUse
	 *            the is use
	 * @param complexity
	 *            the complexity
	 * @param lowestDigits
	 *            the lowest digits
	 * @param historyCount
	 *            the history count
	 * @param validityPeriod
	 *            the validity period
	 */
	public PasswordPolicyDto(Boolean isUse, ComplexityDto complexity, Integer lowestDigits, Integer historyCount,
			Integer validityPeriod) {
		this.isUse = isUse;
		this.complexity = complexity;
		this.lowestDigits = lowestDigits;
		this.historyCount = historyCount;
		this.validityPeriod = validityPeriod;
	}

}
