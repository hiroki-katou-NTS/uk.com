/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.applicable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class EmploymentConditionDto.
 */
@Getter
@Setter
public class EmploymentConditionDto {

	/** The emp cd. */
	private String empCd;

	/** The emp name. */
	private String empName;

	/** The emp applicable atr. */
	private int empApplicableAtr;

	/**
	 * Instantiates a new employment condition dto.
	 *
	 * @param empCd the emp cd
	 * @param empApplicableAtr the emp applicable atr
	 */
	public EmploymentConditionDto(String empCd, int empApplicableAtr) {
		this.empCd = empCd;
		this.empName = ""; // Get name from imported class later.
		this.empApplicableAtr = empApplicableAtr;
	}

}
