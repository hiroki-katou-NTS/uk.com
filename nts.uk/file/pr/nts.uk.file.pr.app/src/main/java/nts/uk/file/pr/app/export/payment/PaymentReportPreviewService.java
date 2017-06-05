/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.payment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

/**
 * The Class PaymentReportService.
 */

@Stateless
public class PaymentReportPreviewService extends ExportService<PaymentReportPreviewQuery> {

	/** The generator. */
	@Inject
	private PaymentReportPreviewGenerator generator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<PaymentReportPreviewQuery> context) {
		this.generator.generate(context.getGeneratorContext(), context.getQuery());
	}
}
