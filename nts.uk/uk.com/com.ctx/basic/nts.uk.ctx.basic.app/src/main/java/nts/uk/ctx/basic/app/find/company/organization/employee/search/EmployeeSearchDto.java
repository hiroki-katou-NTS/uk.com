/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employee.search;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.app.find.company.organization.classification.history.dto.ClassificationHistoryInDto;

/**
 * The Class EmployeeSearchDto.
 */
@Getter
@Setter
public class EmployeeSearchDto implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private ClassificationHistoryInDto classificationHistory;
}
