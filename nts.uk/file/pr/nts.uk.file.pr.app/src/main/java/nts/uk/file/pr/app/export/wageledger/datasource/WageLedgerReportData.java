/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger.datasource;

import java.util.List;

import lombok.Builder;

/**
 * The Class WageLedgerReportData.
 */
@Builder
public class WageLedgerReportData {
	
	/** The header data. */
	public HeaderReportData headerData;
	
	/** The report items. */
	public List<ReportItemDto> reportItems;
}
