/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;

/**
 * The Class ContactPersonalSetting.
 */
@Getter
public class ContactPersonalSetting {
	/** The company code. */
	private String companyCode;

	/** The employee code. */
	private String employeeCode;

	/** The comment. */
	private CommentPersonalSetting comment;

	/** The pay bonus atr. */
	private PayBonusAtr payBonusAtr;

	/** The person id. */
	private String personId;

	/** The processing no. */
	private ProcessingNo processingNo;

	/** The spare pay atr. */
	private SparePayAttr sparePayAtr;
}
