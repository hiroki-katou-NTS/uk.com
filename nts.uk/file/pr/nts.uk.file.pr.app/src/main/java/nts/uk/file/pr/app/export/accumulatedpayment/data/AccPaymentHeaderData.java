/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.accumulatedpayment.data;

import java.time.YearMonth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class AccPaymentHeaderData.
 */
@Builder
@Getter
@Setter
public class AccPaymentHeaderData {

	/** The start. */
	private YearMonth start;

	/** The end. */
	private YearMonth end;
}
