/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger;

import nts.uk.file.pr.app.export.wageledger.data.WageLedgerReportData;

/**
 * The Interface WageLedgerDataRepository.
 */
public interface WageLedgerDataRepository {
	
	/**
	 * Find report data.
	 *
	 * @param query the query
	 * @return the wage ledger report data
	 */
	WageLedgerReportData findReportData(WageLedgerReportQuery query);
}
