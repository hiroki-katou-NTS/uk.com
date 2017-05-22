/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.file.pr.app.export.payment.data.dto.RefundPaddingDto;

/**
 * The Class PaymentReportData.
 */
@Setter
@Getter
public class PaymentReportData {

	/** The report data. */
	private List<PaymentReportDto> reportData;
	
	/** The config. */
	private RefundPaddingDto config;
	
	/** The layout item. */ 
	private int layoutItem;
}