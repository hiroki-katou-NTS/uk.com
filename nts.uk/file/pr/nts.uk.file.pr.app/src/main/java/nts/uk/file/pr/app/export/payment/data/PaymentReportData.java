/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;

/**
 * The Class PaymentReportData.
 */
@Setter
@Getter
public class PaymentReportData {

	List<PaymentReportDto> reportData;
}