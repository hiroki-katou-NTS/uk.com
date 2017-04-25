/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.accumulatedpayment;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentDataSource;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentHeaderData;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentItemData;
import nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery;
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
		String personIds[] = { "99900000-0000-0000-0000-000000000001", "99900000-0000-0000-0000-000000000002",
				"99900000-0000-0000-0000-000000000003", "99900000-0000-0000-0000-000000000004",
				"99900000-0000-0000-0000-000000000005", "99900000-0000-0000-0000-000000000006",
				"99900000-0000-0000-0000-000000000007", "99900000-0000-0000-0000-000000000008",
				"99900000-0000-0000-0000-000000000009", "99900000-0000-0000-0000-000000000010" };
			query.setPIdList(Arrays.asList(personIds));
		
		
		// Query data.
		List<AccPaymentItemData> items = this.repository.getItems(AppContexts.user().companyCode(), query);
		
//		// Fake List of AccumulatedItemData
//		List<AccPaymentItemData> accumulatedPaymentList = new ArrayList<>();
//		for (int i = 0; i < 255; i++) {
//			AccPaymentItemData accumulatedPayment = AccPaymentItemData
//					.builder()
//					.empDesignation("Designation" + (i + 1))
//					.empCode("code000000")
//					.empName("氏名 " + (i + 1))
//					.taxAmount(13456.0 + 100*i)
//					.socialInsuranceAmount(1346.0 + 10*i)
//					.widthHoldingTaxAmount(1016.0 + 10*i)
//					.amountAfterTaxDeduction(5567.0 + 100*i)
//					.enrollmentStatus("退職")
//					.directionalStatus("から　  出向")
//					.build();
//			accumulatedPaymentList.add(accumulatedPayment);
//		}
		
		
//		if(items == null){
//			items = accumulatedPaymentList;
//		}
		// Create header object.
		
		AccPaymentHeaderData headerData = AccPaymentHeaderData.builder()
				.departmentInfo("【部門：役員　販売促進1課　役員～製造部　製造課　製造　（31部門）】")
				.empTypeInfo("【分類：正社員～アルバイト（5分類）】")
				.positionInfo("【職位：参事～主任（10職位）】")
				.yearMonthInfo("【処理年月：平成12年1月~12月迄】")
				.build();

		// Create data source.
		val dataSource = AccPaymentDataSource
				.builder()
				.accPaymentItemData(items)
				.headerData(headerData)
				.build();

		// Call generator.
		this.generator.generate(context.getGeneratorContext(),dataSource, query);		
	}

}
