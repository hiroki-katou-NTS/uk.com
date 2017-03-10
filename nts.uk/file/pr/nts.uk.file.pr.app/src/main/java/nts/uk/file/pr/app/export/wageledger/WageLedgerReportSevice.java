/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery.LayoutType;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;

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
	private WageLedgerReportGenerator generator;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<WageLedgerReportQuery> context) {
		
		// TODO : validate query.
		WageLedgerReportQuery query = context.getQuery();
		
		// Query Data.
		WLOldLayoutReportData reportData = this.repository.findReportData(query);
		
		// Generate report.
		if (query.layoutType == LayoutType.NewLayout) {
			this.generator.generateWithNewLayout(context.getGeneratorContext(), null);
		} else {
			this.generator.generateWithOldLayout(context.getGeneratorContext(), reportData);
		}
	}
}
