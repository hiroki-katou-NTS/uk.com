/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.accumulatedpayment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.screen.app.report.accumulatedpayment.data.AccPaymentDataSource;
import nts.uk.ctx.pr.screen.app.report.accumulatedpayment.query.AccPaymentReportQuery;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AccPaymentReportService.
 */
@Stateless
public class AccPaymentReportService extends ExportService<AccPaymentReportQuery>{

	/** The generator. */
	@Inject
	private AccPaymentReportGenerator generator;

	/** The repository. */
	@Inject
	private AccPaymentRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<AccPaymentReportQuery> context) {

		// Query data.
		val items = this.repository.getItems(AppContexts.user().companyCode(), context.getQuery());

		// Create header object.
		


		// Create data source.
		val dataSource = AccPaymentDataSource
				.builder()
				.accPaymentItemData(items)
				.headerData(null)
				.build();

		// Call generator.
		this.generator.generate(context.getGeneratorContext(),dataSource);		
	}
}
