/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.payment;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PaymentReportQuery.
 */

@Setter
@Getter
public class PaymentReportQuery {
	
	/** The processing no. */
	private int processingNo;
	
	/** The processing YM. */
	private int processingYM;
	
	/** The select print types. */ 
	private int selectPrintTypes;
	
	/** The specification codes. */
	private List<String> specificationCodes;
	
	/** The layout items. */
	private int layoutItems;
		
}
