/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.employee;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The Class EmployeeDto.
 */
@Data
public class EmployeeDto {

	/** The company id. */
	private String companyId;

	/** The p id. */
	private String pId;

	/** The s id. */
	private String sId;

	/** The s cd. */
	private String sCd;

	/** The s mail. */
	private String sMail;

	/** The retirement date. */
	private GeneralDate retirementDate;

	/** The join date. */
	private GeneralDate joinDate;

}
