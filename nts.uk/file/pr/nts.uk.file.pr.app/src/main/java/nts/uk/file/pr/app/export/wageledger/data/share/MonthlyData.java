/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger.data.share;

import java.util.Date;

import lombok.Builder;

/**
 * The Class MonthlyData.
 */
@Builder
public class MonthlyData {
	
	/** The month. */
	public Integer month;
	
	/** The amount. */
	public long amount;
	
	/** The payment date. */
	public Date paymentDate;

}
