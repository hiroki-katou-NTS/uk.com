/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;

/**
 * The Class JpaWageLedgerDataRepository.
 */
@Stateless
public class JpaWageLedgerDataRepository implements WageLedgerDataRepository {

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository
	 * #findReportData(nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery)
	 */
	@Override
	public WLOldLayoutReportData findReportData(WageLedgerReportQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
