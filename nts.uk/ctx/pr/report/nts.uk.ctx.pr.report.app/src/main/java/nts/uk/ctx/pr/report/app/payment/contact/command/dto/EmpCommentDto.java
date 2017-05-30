/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.payment.contact.command.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class EmpCommentDto.
 */

@Getter
@Setter
public class EmpCommentDto {
	
	/** The emp cd. */
	private String empCd;
	
	/** The employee name. */
	private String employeeName;

	/** The initial comment. */
	private String initialComment;

	/** The monthly comment. */
	private String monthlyComment;
}
