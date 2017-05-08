/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.denominationtable;

import java.util.List;

import nts.uk.file.pr.app.export.denominationtable.data.EmployeeData;
import nts.uk.file.pr.app.export.denominationtable.query.DenominationTableReportQuery;


/**
 * The Interface SalarychartRepository.
 */
public interface DenominationTableRepository {

	/**
	 * Gets the items.
	 *
	 * @param code the code
	 * @return the items
	 */
	List<EmployeeData> getItems(String companyCode, DenominationTableReportQuery query);
	
	
}
