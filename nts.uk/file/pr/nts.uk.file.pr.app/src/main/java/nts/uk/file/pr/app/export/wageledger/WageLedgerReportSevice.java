/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery.LayoutType;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.newlayout.BeforeEndYearData;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageLedgerReportSevice.
 */
@Stateless
public class WageLedgerReportSevice extends ExportService<WageLedgerReportQuery>{
	
	/** The repository. */
	@Inject
	private WageLedgerDataRepository repository;
	
	/** The generator. */
	@Inject
	private WLOldLayoutReportGenerator oldGenerator;
	
	/** The new generator. */
	@Inject
	private WLNewLayoutReportGenerator newGenerator;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<WageLedgerReportQuery> context) {
		WageLedgerReportQuery query = context.getQuery();
		String companyCode = AppContexts.user().companyCode();
		
		// TODO: Validate query employee ids and base date.
		query.baseDate = GeneralDate.today();
		query.employeeIds = Arrays.asList("999000000000000000000000000000000001");
		if (!this.repository.hasReportData(companyCode, query)) {
			throw new BusinessException("ER010");
		}
		
		// Generate report.
		if (query.layoutType == LayoutType.NewLayout) {
			// Query Data.
			List<WLNewLayoutReportData> reportData = this.repository.findReportDatas(companyCode, query,
					WLNewLayoutReportData.class);
			// Fake data for new layout report.
			reportData.get(0).beforeEndYearData = BeforeEndYearData.builder()
					.acquisitionTaxOtherMoney((long)(Math.random() * 2000000))
					.acquisitionTaxPreviousPosition((long)(Math.random() * 2000000))
					.totalSocialInsuranceOtherMoney((long)(Math.random() * 2000000))
					.totalSocialInsurancePreviousPosition((long)(Math.random() * 2000000))
					.totalTaxOtherMoney((long)(Math.random() * 2000000))
					.totalTaxPreviousPosition((long)(Math.random() * 2000000))
					.build();
			reportData.get(0).bonusPaymentDateMap = new HashMap<>();
			reportData.get(0).salaryPaymentDateMap = this.fakeDateMap(10);
			this.newGenerator.generate(context.getGeneratorContext(), reportData.get(0), query);
			return;
		}
		// Query Data.
		List<WLOldLayoutReportData> reportData = this.repository.findReportDatas(companyCode, query,
				WLOldLayoutReportData.class);
		this.oldGenerator.generate(context.getGeneratorContext(), reportData.get(0), query);
	}
	
	private Map<Integer, Date> fakeDateMap(int month) {
		Map<Integer, Date> dateMap = new HashMap<>();
		dateMap.put(2, new Date());
		dateMap.put(3, new Date());
		return dateMap;
	}
}
