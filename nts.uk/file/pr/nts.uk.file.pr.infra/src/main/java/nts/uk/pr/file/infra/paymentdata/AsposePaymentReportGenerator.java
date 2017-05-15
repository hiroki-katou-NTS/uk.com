/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.payment.PaymentReportGenerator;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposePaymentReportGenerator.
 */
@Stateless
public class AsposePaymentReportGenerator extends AsposeCellsReportGenerator implements PaymentReportGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.pr.app.export.payment.PaymentReportGenerator#generate(nts.arc
	 * .layer.infra.file.export.FileGeneratorContext,
	 * nts.uk.file.pr.app.export.payment.data.PaymentReportData)
	 */
	@Override
	public void generate(FileGeneratorContext fileContext, PaymentReportData reportData) {
	}

}