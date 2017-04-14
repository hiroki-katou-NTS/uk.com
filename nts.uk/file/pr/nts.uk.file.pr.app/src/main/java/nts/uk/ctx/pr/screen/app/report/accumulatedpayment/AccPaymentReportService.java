/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.screen.app.report.accumulatedpayment;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.screen.app.report.accumulatedpayment.data.AccPaymentDataSource;
import nts.uk.ctx.pr.screen.app.report.accumulatedpayment.data.AccPaymentItemData;
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

		// Get Query
		AccPaymentReportQuery query = context.getQuery();
		
		// Query data.
		List<AccPaymentItemData> items = this.repository.getItems(AppContexts.user().companyCode(), query);
		
		// Fake List of AccumulatedItemData
		List<AccPaymentItemData> accumulatedPaymentList = new ArrayList<>();
		for (int i = 0; i < 255; i++) {
			AccPaymentItemData accumulatedPayment = AccPaymentItemData
					.builder()
					.empDesignation("Designation" + (i + 1))
					.empCode("code000000")
					.empName("氏名 " + (i + 1)).taxAmount(13456.0 + 100*i)
					.socialInsuranceAmount(10156.0 + 100*i)
					.widthHoldingTaxAmount(3956.0 + 100*i)
					.amountAfterTaxDeduction(5567.0 + 100*i)
					.enrollmentStatus("退職")
					.directionalStatus("xxxxxxxxxxxxx   から　  出向")
					.build();
			accumulatedPaymentList.add(accumulatedPayment);
		}
		
		if(items == null){
			items = accumulatedPaymentList;
		}
		// Create header object.
		


		// Create data source.
		val dataSource = AccPaymentDataSource
				.builder()
				.accPaymentItemData(items)
				.headerData(null)
				.build();

		// Call generator.
		this.generator.generate(context.getGeneratorContext(),dataSource, query);		
	}
}
