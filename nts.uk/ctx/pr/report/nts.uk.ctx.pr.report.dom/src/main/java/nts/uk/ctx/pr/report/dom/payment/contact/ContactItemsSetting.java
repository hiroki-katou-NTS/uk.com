/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ContactItemsSetting.
 */
@Getter
public class ContactItemsSetting extends AggregateRoot {
	
	/** The company code. */
	private String companyCode;
	
	/** The employee code. */
	private String employeeCode;
	
	/** The comment initial cp. */
	private CommentInitialCp commentInitialCp;
	
	/** The comment initial em. */
	private CommentInitialEm commentInitialEm;
	
	/** The comment month cp. */
	private CommentMonthCp commentMonthCp;
	
	/** The comment month em. */
	private CommentMonthEm commentMonthEm;
	
	/** The pay bonus atr. */
	private PayBonusAtr payBonusAtr;
	
	/** The processing no. */
	private CommentInitialCp processingNo;
	
	/** The processing ym. */
	private ProcessingYm  processingYm; 
	
	/** The spare pay attr. */
	private SparePayAttr sparePayAttr; 
}
