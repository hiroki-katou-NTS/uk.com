/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;

/**
 * The Class ContactItemsSetting.
 */
@Getter
public class ContactItemsSetting extends AggregateRoot {

	/** The company code. */
	private String companyCode;

	/** The processing no. */
	private int processingNo;

	/** The pay bonus atr. */
	private PayBonusAtr payBonusAtr;

	/** The processing ym. */
	private YearMonth processingYm;

	/** The spare pay attr. */
	private SparePayAtr sparePayAttr;

	/** The comment initial cp. */
	private CommentInitialCp commentInitialCp;

	/** The comment month cp. */
	private CommentMonthCp commentMonthCp;

	/** The personal comments. */
	private Set<PersonalComment> personalComments;

}
