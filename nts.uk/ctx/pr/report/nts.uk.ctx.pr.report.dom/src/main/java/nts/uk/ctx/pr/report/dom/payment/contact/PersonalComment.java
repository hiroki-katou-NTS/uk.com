/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class PersonalComment.
 */
@Getter
public class PersonalComment extends AggregateRoot {

	/** The company code. */
	private String companyCode;

	/** The employee code. */
	private String employeeCode;

	/** The comment initial. */
	private CommentInitial commentInitial;

	/** The comment month. */
	private CommentMonth commentMonth;

}
