/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.primitive.sample.ProcessingNo;

/**
 * The Class PersonalComment.
 */
@Getter
public class ContactPersonalSetting extends AggregateRoot {

	/** The company code. */
	private String companyCode;

	/** The processing no. */ // ??????
	private ProcessingNo processingNo;

	// PAY_BONUS_ATR = 0
	// SPARE_PAY_ATR = 0

	/** The processing ym. */
	private YearMonth processingYm;

	/** The employee code. */
	private String employeeCode;

	/** The initial comment. */
	private ReportComment comment;

}
