/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.dom.adapter.bs.dto;

import lombok.Getter;

/**
 * The Class AffWorkplaceHistoryImport.
 */
// 所属職場履歴
@Getter
public class AffWorkplaceHistoryImport {

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	/** The period. */
	// 期間
	private Period period;
	
	/** The workplace id. */
	// 職場ID
	private String workplaceId;
}
