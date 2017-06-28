/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employee.search;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class EmployeeSearchDto.
 */
@Getter
@Setter
public class EmployeeSearchDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The base date. */
	private GeneralDate baseDate;

	/** The employment codes. */
	private List<String> employmentCodes;

	/** The classification codes. */
	private List<String> classificationCodes;

	/** The job title codes. */
	private List<String> jobTitleCodes;
	
	/** The workplace codes. */
	private List<String> workplaceCodes;
}
