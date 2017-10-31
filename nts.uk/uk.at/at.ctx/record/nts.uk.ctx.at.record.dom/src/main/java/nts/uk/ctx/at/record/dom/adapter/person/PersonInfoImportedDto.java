/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.person;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class PersonInfoImported.
 */
@Getter
@Builder
public class PersonInfoImportedDto {

	/** The employee id. */
	private String employeeId;

	/** The employee name. */
	private String employeeName;

}
