/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.denominationtable.data;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;




/**
 * The Class DenominationTableData.
 */

@Builder

@Getter

@Setter
public class DenominationTableData {
	
	/** The salary chart header. */
	private DenoTableHeaderData salaryChartHeader;
	
	/** The employee list. */
	private List<EmployeeData> employeeList;
}
