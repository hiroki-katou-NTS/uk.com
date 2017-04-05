/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;

/**
 * The Class JpaWageLedgerDataRepository.
 */
@Stateless
public class JpaWageLedgerDataRepository implements WageLedgerDataRepository {

	@Override
	public List<WLOldLayoutReportData> findReportDatas(int targetYear, List<String> employeeIds) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
